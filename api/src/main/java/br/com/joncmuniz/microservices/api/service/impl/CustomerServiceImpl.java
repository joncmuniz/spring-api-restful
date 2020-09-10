package br.com.joncmuniz.microservices.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.joncmuniz.microservices.api.client.persitence.model.Customer;
import br.com.joncmuniz.microservices.api.persistence.dao.ICustomerJpaDao;
import br.com.joncmuniz.microservices.api.service.ICustomerService;
import br.com.joncmuniz.microservices.common.persistence.service.AbstractService;

@Service
@Transactional
public class CustomerServiceImpl extends AbstractService<Customer> implements ICustomerService {

    @Autowired
    ICustomerJpaDao dao;

    public CustomerServiceImpl() {
        super();
    }

    

    // find

    @Override
    @Transactional(readOnly = true)
    public Customer findByName(final String name) {
        return dao.findByName(name);
    }

    

    @Override
    protected final ICustomerJpaDao getDao() {
        return dao;
    }

    @Override
    protected JpaSpecificationExecutor<Customer> getSpecificationExecutor() {
        return dao;
    }

}