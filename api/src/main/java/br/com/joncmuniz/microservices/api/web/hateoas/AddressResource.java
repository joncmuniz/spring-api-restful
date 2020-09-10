package br.com.joncmuniz.microservices.api.web.hateoas;

import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import br.com.joncmuniz.microservices.api.client.persitence.model.Address;

public class AddressResource extends RepresentationModel {

    private Address address;

    public AddressResource(final Long customerId, final Address address) {
        this.address = address;
        List<Address> addresses = new ArrayList<Address>();
        addresses.add(address);
    }
    
    public AddressResource(Address address) {
		this.address=address;
	}
    
	public Address getAddress() {
        return this.address;
    }

}