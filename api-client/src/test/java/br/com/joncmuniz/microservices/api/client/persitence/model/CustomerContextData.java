package br.com.joncmuniz.microservices.api.client.persitence.model;

public class CustomerContextData {
	private Customer customer;
	private Integer ageExpected;
	
	public CustomerContextData(Customer customer, Integer expectedAge) {
		this.customer=customer;
		this.ageExpected=expectedAge;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public Integer getAgeExpected() {
		return ageExpected;
	}
	public void setAgeExpected(Integer ageExpected) {
		this.ageExpected = ageExpected;
	}
}