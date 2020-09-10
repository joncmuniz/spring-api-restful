package br.com.joncmuniz.microservices.api.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.joncmuniz.microservices.api.client.persitence.model.Customer;
import br.com.joncmuniz.microservices.api.client.util.ApiMappings;
import br.com.joncmuniz.microservices.api.service.ICustomerService;
import br.com.joncmuniz.microservices.common.util.QueryConstants;
import br.com.joncmuniz.microservices.common.web.controller.AbstractController;


@Controller
@RequestMapping(value = ApiMappings.Plural.CUSTOMERS)
public class CustomersController extends AbstractController<Customer> {

    @Autowired
    private ICustomerService service;

    public CustomersController() {
        super(Customer.class);
    }
    
    @RequestMapping(params = { QueryConstants.PAGE, QueryConstants.SIZE, QueryConstants.SORT_BY },produces = "application/json; charset=UTF-8", method = RequestMethod.GET)
    @ResponseBody
    public List<Customer> findAllPaginatedAndSorted(@RequestParam(value = QueryConstants.PAGE) final int page, @RequestParam(value = QueryConstants.SIZE) final int size, @RequestParam(value = QueryConstants.SORT_BY) final String sortBy,
        @RequestParam(value = QueryConstants.SORT_ORDER) final String sortOrder, final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        return findPaginatedAndSortedInternal(page, size, sortBy, sortOrder, uriBuilder, response);
    }

    @RequestMapping(params = { QueryConstants.PAGE, QueryConstants.SIZE },produces = "application/json; charset=UTF-8", method = RequestMethod.GET)
    @ResponseBody
    public List<Customer> findAllPaginated(@RequestParam(value = QueryConstants.PAGE) final int page, @RequestParam(value = QueryConstants.SIZE) final int size, final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        return findPaginatedInternal(page, size, uriBuilder, response);
    }

    @RequestMapping(params = { QueryConstants.SORT_BY }, produces = "application/json; charset=UTF-8",method = RequestMethod.GET)
    @ResponseBody
    public List<Customer> findAllSorted(@RequestParam(value = QueryConstants.SORT_BY) final String sortBy, @RequestParam(value = QueryConstants.SORT_ORDER) final String sortOrder) {
        return findAllSortedInternal(sortBy, sortOrder);
    }

    @RequestMapping(method = RequestMethod.GET,produces = "application/json; charset=UTF-8")
    @ResponseBody
    public List<Customer> findAll(final HttpServletRequest request, final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        return findAllInternal(request, uriBuilder, response);
    }

    @RequestMapping(value = "/{id}", produces = "application/json; charset=UTF-8", method = RequestMethod.GET)
    @ResponseBody
    public Customer findOne(@PathVariable("id") final Long id, final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        return findOneInternal(id, uriBuilder, response);
    }

    @RequestMapping(produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Customer create(@RequestBody @Valid final Customer resource, final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        createInternal(resource, uriBuilder, response);
        return resource;
    }

    @RequestMapping(produces = "application/json; charset=UTF-8", value = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public Customer update(@PathVariable("id") final Long id, @RequestBody @Valid final Customer resource) {
    	resource.setId(id);
        updateInternal(id, resource);
        return resource;
    }

    @RequestMapping(value = "/{id}",produces = "application/json; charset=UTF-8", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") final Long id) {
        deleteByIdInternal(id);
    }

    @Override
    protected final ICustomerService getService() {
        return service;
    }

}