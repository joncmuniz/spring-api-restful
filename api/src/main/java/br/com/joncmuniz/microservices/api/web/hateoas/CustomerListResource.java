package br.com.joncmuniz.microservices.api.web.hateoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import br.com.joncmuniz.microservices.api.client.persitence.model.Customer;

public class CustomerListResource extends RepresentationModel {

    private List<CustomerResource> customers;
    
    public CustomerListResource() {
	}

    public CustomerListResource(final List<Customer> customersEntity) {
        customers=new ArrayList<CustomerResource>();
    	for (Customer customerEntity : customersEntity) {
    		customers.add(new CustomerResource(customerEntity));
    	}
    	this.add(linkTo(CustomerHateoasController.class).withRel("customers"));
    }

	public List<CustomerResource> getCustomers() {
		return customers;
	}

	public void setCustomers(List<CustomerResource> customers) {
		this.customers = customers;
	}
	
}
