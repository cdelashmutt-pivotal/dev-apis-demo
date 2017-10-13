package io.pivotal.pa.bankclientapp.connector;

import java.time.LocalDate;

/**
 * Created by cdelashmutt on 10/12/17.
 */
public class Bill {

	private String billerName;
	private String payorId;
	private String amount;
	private LocalDate dueDate;

	public Bill() {
	}

	public Bill(String billerName, String payorId, String amount, LocalDate dueDate) {
		this.billerName = billerName;
		this.payorId = payorId;
		this.amount = amount;
		this.dueDate = dueDate;
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
}
