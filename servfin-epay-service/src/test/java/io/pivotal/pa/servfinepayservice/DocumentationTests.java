package io.pivotal.pa.servfinepayservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.pivotal.pa.servfinepayservice.model.Bill;
import io.pivotal.pa.servfinepayservice.repository.BillRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;

import static org.hamcrest.Matchers.*;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DocumentationTests {

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private BillRepository repository;

	@Autowired
	private ObjectMapper objectMapper;

	private MockMvc mockMvc;

	@Rule
	public final JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();

	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
				.apply(documentationConfiguration(this.restDocumentation)).build();
	}

	@Test
	public void billsListExample() throws Exception {
		this.repository.deleteAll();

		createBill("Turpis Non Enim PC", "45678", "63.77", "2018-09-18");
		createBill("Donec Sollicitudin Foundation", "12345", "3.62", "2018-01-04");

		this.mockMvc.perform(get("/bills"))
				.andExpect(status().isOk())
				.andDo(document("billsListExample",
						links(
								linkWithRel("self").description("Canonical link for this resource"),
								linkWithRel("profile").description("The ALPS profile for this resource"),
								linkWithRel("search").description("Search for bills")),
						responseFields(
								subsectionWithPath("_embedded.bills").description("An array of <<resources-bills, Bill resources>>"),
								subsectionWithPath("_links").description("<<resources-bills-list_links, Links>> to other resources"))
				));
	}

	@Test
	public void billGetExample() throws Exception {
		this.repository.deleteAll();

		Bill bill = Bill.builder().withBillerName("Turpis Non Enim PC")
				.withPayorId("45678")
				.withAmount("63.77")
				.withDueDate(LocalDate.parse("2018-09-18"))
				.build();

		String billLocation = this.mockMvc.perform(post("/bills")
				.contentType(MediaTypes.HAL_JSON)
				.content(this.objectMapper.writeValueAsString(bill)))
				.andExpect(status().isCreated())
				.andReturn().getResponse().getHeader("Location");

		this.mockMvc.perform(get(billLocation))
				.andExpect(status().isOk())
				.andExpect(jsonPath("billerName", is(bill.getBillerName())))
				.andExpect(jsonPath("payorId", is(bill.getPayorId())))
				.andExpect(jsonPath("amount", is(bill.getAmount())))
				.andExpect(jsonPath("dueDate", is(bill.getDueDate().toString())))
				.andExpect(jsonPath("_links.self.href", is(billLocation)))
				.andDo(document("billGetExample",
						links(
								linkWithRel("bill").description("Canonical link for the bill"),
								linkWithRel("self").description("Canonical link for this resource")),
						responseFields(
								fieldWithPath("billerName").description("The name of the biller for this bill"),
								fieldWithPath("payorId").description("The ID of the payor for this bill"),
								fieldWithPath("amount").description("The amount of this bill"),
								fieldWithPath("dueDate").description("The due date for this bill"),
								subsectionWithPath("_links").description("<<resources-bill-get_links, Links>> to other resources"))
				));
	}

	@Test
	public void billPostExample() throws Exception {
		this.repository.deleteAll();

		Bill bill = Bill.builder().withBillerName("Turpis Non Enim PC")
				.withPayorId("45678")
				.withAmount("63.77")
				.withDueDate(LocalDate.parse("2018-09-18"))
				.build();

		this.mockMvc.perform(post("/bills")
				.contentType(MediaTypes.HAL_JSON)
				.content(this.objectMapper.writeValueAsString(bill)))
				.andExpect(status().isCreated())
				.andDo(document("billPostExample"));
	}

	private void createBill(String billerName, String payor, String amount, String dueDate) {
		Bill bill = new Bill();
		bill.setBillerName(billerName);
		bill.setPayorId(payor);
		bill.setAmount(amount);
		bill.setDueDate(LocalDate.parse(dueDate));

		this.repository.save(bill);
	}
}