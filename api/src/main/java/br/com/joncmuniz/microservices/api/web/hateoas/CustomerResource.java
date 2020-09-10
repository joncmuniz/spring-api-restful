package br.com.joncmuniz.microservices.api.web.hateoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import javax.servlet.http.HttpServletRequest;

import org.springframework.hateoas.RepresentationModel;

import br.com.joncmuniz.microservices.api.client.persitence.model.Customer;

public class CustomerResource extends RepresentationModel {

    private final Customer customer;

    public CustomerResource(final Customer customer, final HttpServletRequest request) {
        this.customer = customer;
        this.add(linkTo(methodOn(AddressHateoasController.class).getAdressesForCustomer(request,customer.getId())).withRel("adresses"));
    }
    
    public CustomerResource(final Customer customer) {
    	this.customer = customer;
        this.add(linkTo(methodOn(AddressHateoasController.class).getAdressesForCustomer(null,customer.getId())).withRel("adresses"));
	}

    public Customer getCustomer() {
        return customer;
    }

}
