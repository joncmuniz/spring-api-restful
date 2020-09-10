package br.com.joncmuniz.microservices.api.web.hateoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.joncmuniz.microservices.api.client.persitence.model.Customer;
import br.com.joncmuniz.microservices.api.client.util.ApiMappings;
import br.com.joncmuniz.microservices.api.service.ICustomerService;
import br.com.joncmuniz.microservices.common.util.QueryConstants;
import br.com.joncmuniz.microservices.common.web.controller.AbstractHateoasController;

@Controller
@RequestMapping(value = ApiMappings.Hateoas.CUSTOMER)
public class CustomerHateoasController extends AbstractHateoasController<CustomerResource, Customer> {

	@Autowired
	private ICustomerService service;

	public CustomerHateoasController() {
		super();
	}

	@RequestMapping(params = { QueryConstants.PAGE, QueryConstants.SIZE,
			QueryConstants.SORT_BY }, method = RequestMethod.GET)
	@ResponseBody
	@SwaggerExposeApi
	public HttpEntity<CustomerListResource> findAllPaginatedAndSorted(final HttpServletRequest request, @RequestParam(value = QueryConstants.PAGE) final int page,
			@RequestParam(value = QueryConstants.SIZE) final int size,
			@RequestParam(value = QueryConstants.SORT_BY) final String sortBy,
			@RequestParam(value = QueryConstants.SORT_ORDER) final String sortOrder) {
		
		
		CustomerListResource customerListResource = new CustomerListResource();
		List<CustomerResource> customersResource = findPaginatedAndSortedInternal(page, size, sortBy, sortOrder);
		for (CustomerResource customerResource : customersResource) {
			customerResource.add(linkTo(methodOn(CustomerHateoasController.class).update(request, customerResource.getCustomer().getId(), customerResource.getCustomer())).withSelfRel());
		}
		customerListResource.setCustomers(customersResource);
		customerListResource.add(linkTo(CustomerHateoasController.class).withRel("customers"));

		ResponseEntity<CustomerListResource> responseEntity = new ResponseEntity<CustomerListResource>(
				customerListResource, HttpStatus.OK);
		return responseEntity;
		
		
	}

	@RequestMapping(params = { QueryConstants.PAGE, QueryConstants.SIZE }, method = RequestMethod.GET)
	@ResponseBody
	@SwaggerExposeApi
	public HttpEntity<CustomerListResource> findAllPaginated(final HttpServletRequest request,@RequestParam(value = QueryConstants.PAGE) final int page,
			@RequestParam(value = QueryConstants.SIZE) final int size) {
		
		CustomerListResource customerListResource = new CustomerListResource();
		List<CustomerResource> customersResource = findPaginatedInternal(page, size);
		for (CustomerResource customerResource : customersResource) {
			customerResource.add(linkTo(methodOn(CustomerHateoasController.class).update(request, customerResource.getCustomer().getId(), customerResource.getCustomer())).withSelfRel());
		}
		customerListResource.setCustomers(customersResource);
		customerListResource.add(linkTo(CustomerHateoasController.class).withRel("customers"));

		ResponseEntity<CustomerListResource> responseEntity = new ResponseEntity<CustomerListResource>(
				customerListResource, HttpStatus.OK);
		return responseEntity;
	}

	@RequestMapping(params = { QueryConstants.SORT_BY }, method = RequestMethod.GET)
	@ResponseBody
	@SwaggerExposeApi
	public HttpEntity<CustomerListResource> findAllSorted(final HttpServletRequest request,@RequestParam(value = QueryConstants.SORT_BY) final String sortBy,
			@RequestParam(value = QueryConstants.SORT_ORDER) final String sortOrder) {
		
		CustomerListResource customerListResource = new CustomerListResource();
		List<CustomerResource> customersResource = findAllSortedInternal(sortBy, sortOrder);
		for (CustomerResource customerResource : customersResource) {
			customerResource.add(linkTo(methodOn(CustomerHateoasController.class).update(request, customerResource.getCustomer().getId(), customerResource.getCustomer())).withSelfRel());
		}
		customerListResource.setCustomers(customersResource);
		customerListResource.add(linkTo(CustomerHateoasController.class).withRel("customers"));

		ResponseEntity<CustomerListResource> responseEntity = new ResponseEntity<CustomerListResource>(
				customerListResource, HttpStatus.OK);
		return responseEntity;
	}

	@RequestMapping(produces = "application/json; charset=UTF-8", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@SwaggerExposeApi
	public HttpEntity<CustomerListResource> findAll(final HttpServletRequest request) {

		CustomerListResource customerListResource = new CustomerListResource();
		List<CustomerResource> customersResource = findAllInternal(request);
		for (CustomerResource customerResource : customersResource) {
			customerResource.add(linkTo(methodOn(CustomerHateoasController.class).update(request, customerResource.getCustomer().getId(), customerResource.getCustomer())).withSelfRel());
		}
		customerListResource.setCustomers(customersResource);
		customerListResource.add(linkTo(CustomerHateoasController.class).withRel("customers"));

		ResponseEntity<CustomerListResource> responseEntity = new ResponseEntity<CustomerListResource>(
				customerListResource, HttpStatus.OK);
		return responseEntity;
	}
@Override
public long count() {
	// TODO Auto-generated method stub
	return super.count();
}
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
	public CustomerResource findOne(final HttpServletRequest request, @PathVariable("id") final Long id) {
		CustomerResource customerResource = findOneInternal(id);
		customerResource.add(linkTo(methodOn(CustomerHateoasController.class).findAll(request)).withSelfRel());
		return customerResource;
	}

	@PostMapping(produces = "application/json; charset=UTF-8")
	@ResponseStatus(HttpStatus.CREATED)
	@SwaggerExposeApi
	public HttpEntity<CustomerResource> create(final HttpServletRequest request,
			@RequestBody @Valid final Customer resource) {
		createInternal(resource);
		CustomerResource customerResource = new CustomerResource(resource, request);
		customerResource.add(linkTo(methodOn(CustomerHateoasController.class).findAll(request)).withSelfRel());
		customerResource.add(linkTo(methodOn(CustomerHateoasController.class).update(request, resource.getId(), resource)).withSelfRel());
		return new ResponseEntity<CustomerResource>(customerResource, HttpStatus.CREATED);
	}

	@RequestMapping(produces = "application/json; charset=UTF-8", value = "/{id}", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	@SwaggerExposeApi
	public HttpEntity<CustomerResource> update(final HttpServletRequest request, @PathVariable("id") final Long id,
			@RequestBody @Valid final Customer resource) {
		resource.setId(id);
		updateInternal(id, resource);
		CustomerResource customerResource = new CustomerResource(resource, request);
		customerResource.add(linkTo(methodOn(CustomerHateoasController.class).findAll(request)).withSelfRel());
		return new ResponseEntity<CustomerResource>(customerResource, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@SwaggerExposeApi
	public void delete(@PathVariable("id") final Long id) {
		deleteByIdInternal(id);
	}

	@Override
	protected final ICustomerService getService() {
		return service;
	}

	@Override
	protected final CustomerResource convert(final Customer entity) {
		return new CustomerResource(entity);
	}

}
