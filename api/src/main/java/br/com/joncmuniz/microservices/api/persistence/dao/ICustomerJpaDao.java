package br.com.joncmuniz.microservices.api.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import br.com.joncmuniz.microservices.api.client.persitence.model.Customer;
import br.com.joncmuniz.microservices.common.interfaces.IByNameApi;

public interface ICustomerJpaDao extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer>, IByNameApi<Customer> {

}
