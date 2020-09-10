package br.com.joncmuniz.microservices.api.web.hateoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;

import br.com.joncmuniz.microservices.api.client.persitence.model.Address;

public class AddressListResource extends RepresentationModel {

    private List<AddressResource> adresses=new ArrayList<AddressResource>();

	public AddressListResource(Long customerId, @Valid List<Address> resource, final HttpServletRequest request) {
		for (Address addressEntity : resource) {
			AddressResource addressResource = new AddressResource(customerId, addressEntity);
			addressResource.add(linkTo(methodOn(AddressHateoasController.class).update(request, customerId, addressEntity.getId(), addressEntity)).withSelfRel());
			adresses.add(addressResource);
		}
		
	}

	public List<AddressResource> getAdresses() {
		return adresses;
	}

}
