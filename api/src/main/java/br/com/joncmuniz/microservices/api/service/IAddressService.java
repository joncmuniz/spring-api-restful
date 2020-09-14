package br.com.joncmuniz.microservices.api.service;

import java.util.List;

import br.com.joncmuniz.microservices.api.client.persitence.model.Address;
import br.com.joncmuniz.microservices.common.persistence.service.IService;

public interface IAddressService extends IService<Address> {

	public void manageAddressFromCustomer(Long customerId, Address address);

	public void deleteAddressFromCustomer(Long addressId, Long customerId);

	public List<Address> listAdressesFromCustomer(Long customerId);
    

}
