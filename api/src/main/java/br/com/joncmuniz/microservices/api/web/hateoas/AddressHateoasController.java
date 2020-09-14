package br.com.joncmuniz.microservices.api.web.hateoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.joncmuniz.microservices.api.client.persitence.model.Address;
import br.com.joncmuniz.microservices.api.client.util.ApiMappings;
import br.com.joncmuniz.microservices.api.service.IAddressService;
import br.com.joncmuniz.microservices.common.web.controller.AbstractHateoasController;

@RestController
@RequestMapping(value = ApiMappings.Hateoas.EMPTY)
public class AddressHateoasController extends AbstractHateoasController<AddressResource, Address> {

	@Autowired
	private IAddressService service;

	@RequestMapping(value = {
			"customers/{customerId}/adresses" }, produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public HttpEntity<AddressResource> create(final HttpServletRequest request,@PathVariable("customerId") final Long customerId,
			@RequestBody @Valid final Address resource, final UriComponentsBuilder uriBuilder,
			final HttpServletResponse response) {
		service.manageAddressFromCustomer(customerId, resource);
		
		AddressResource addressResource = new AddressResource(customerId, resource);
		addressResource.add(linkTo(methodOn(AddressHateoasController.class).getAdressesForCustomer(request,customerId)).withSelfRel());
		addressResource.add(Link.of(linkTo(methodOn(AddressHateoasController.class).update(request, customerId, resource.getId(),resource)).toString().replace("{addressId}", resource.getId().toString())));
		addressResource.add(linkTo(methodOn(CustomerHateoasController.class).findOne(request, customerId)).withRel("customers"));

		return new ResponseEntity<AddressResource>(addressResource, HttpStatus.CREATED);
	}

	@RequestMapping(value = { "customers/{customerId}/adresses/{addressId}" }, method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	public HttpEntity<AddressResource> update(final HttpServletRequest request,@PathVariable("customerId") final Long customerId,
			@PathVariable("addressId") final Long addressId,
			@RequestBody @Valid final Address resource) {
		resource.setId(addressId);
		service.manageAddressFromCustomer(customerId, resource);
		AddressResource addressResource = new AddressResource(customerId, resource);
		addressResource.add(linkTo(methodOn(AddressHateoasController.class).getAdressesForCustomer(request,customerId)).withSelfRel());
		addressResource.add(Link.of(linkTo(methodOn(AddressHateoasController.class).update(request, customerId, addressId,resource)).toString().replace("{addressId}", resource.getId().toString())));
		addressResource.add(linkTo(methodOn(CustomerHateoasController.class).findOne(request, customerId)).withRel("customers"));
		return new ResponseEntity<AddressResource>(addressResource, HttpStatus.CREATED);

	}

	@RequestMapping(value = { "customers/{customerId}/adresses/{addressId}" }, method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(final HttpServletRequest request, @PathVariable("customerId") final Long customerId,
			@PathVariable("customerId") final Long addressId) {
		service.deleteAddressFromCustomer(addressId, customerId);
		
	}

	@RequestMapping(value = { "customers/{customerId}/adresses" }, method = RequestMethod.GET)
	@ResponseBody
	public HttpEntity<AddressListResource> getAdressesForCustomer(final HttpServletRequest request, @PathVariable("customerId") final Long customerId) {

		AddressListResource addressListResource = new AddressListResource(customerId,
				service.listAdressesFromCustomer(customerId),request);
		addressListResource.add(linkTo(methodOn(CustomerHateoasController.class).findOne(request,customerId)).withRel("customers"));
		return new ResponseEntity<AddressListResource>(addressListResource, HttpStatus.OK);
	}

	@Override
	protected final IAddressService getService() {
		return service;
	}

	@Override
	protected final AddressResource convert(final Address entity) {
		return new AddressResource(entity);
	}
}