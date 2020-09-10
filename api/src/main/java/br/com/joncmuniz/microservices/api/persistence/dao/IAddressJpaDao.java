package br.com.joncmuniz.microservices.api.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import br.com.joncmuniz.microservices.api.client.persitence.model.Address;

public interface IAddressJpaDao extends JpaRepository<Address, Long>, JpaSpecificationExecutor<Address>{

}
