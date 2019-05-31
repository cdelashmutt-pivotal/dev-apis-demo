package io.pivotal.pa.servfinepayservice.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

/**
 * Created by cdelashmutt on 10/2/17.
 */
@Entity
public class Bill {

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	private String id;
	private String billerName;
	private String payorId;
	private String amount;
	private LocalDate dueDate;

	public Bill() {
	}

	Bill(String billerName, String payorId, String amount, LocalDate dueDate) {
		this.billerName = billerName;
		this.payorId = payorId;
		this.amount = amount;
		this.dueDate = dueDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBillerName() {
		return billerName;
	}

	public void setBillerName(String billerName) {
		this.billerName = billerName;
	}

	public String getPayorId() {
		return payorId;
	}

	public void setPayorId(String payorId) {
		this.payorId = payorId;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public static BillBuilder builder() {
		return new BillBuilder();
	}

	public static class BillBuilder {
		private String billerName;
		private String payorId;
		private String amount;
		private LocalDate dueDate;

		public BillBuilder withBillerName(final String billerName) {
			this.billerName = billerName;
			return this;
		}

		public BillBuilder withPayorId(final String payorId) {
			this.payorId = payorId;
			return this;
		}

		public BillBuilder withAmount(final String amount) {
			this.amount = amount;
			return this;
		}

		public BillBuilder withDueDate(final LocalDate dueDate) {
			this.dueDate = dueDate;
			return this;
		}

		public Bill build() {
			return new Bill(billerName, payorId, amount, dueDate);
		}
	}
}
