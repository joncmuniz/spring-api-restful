package br.com.joncmuniz.microservices.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.joncmuniz.microservices.api.client.persitence.model.Address;
import br.com.joncmuniz.microservices.api.client.persitence.model.Customer;
import br.com.joncmuniz.microservices.api.persistence.dao.IAddressJpaDao;
import br.com.joncmuniz.microservices.api.persistence.dao.specifications.AddressSpecification;
import br.com.joncmuniz.microservices.api.service.IAddressService;
import br.com.joncmuniz.microservices.api.service.ICustomerService;
import br.com.joncmuniz.microservices.common.persistence.service.AbstractService;
import br.com.joncmuniz.microservices.common.web.exception.ResourceNotFoundException;

@Service
@Transactional
public class AddresseServiceImpl extends AbstractService<Address> implements IAddressService {

	@Autowired
	IAddressJpaDao dao;

	@Autowired
	ICustomerService customerService;

	public AddresseServiceImpl() {
		super();
	}

	@Override
	protected final IAddressJpaDao getDao() {
		return dao;
	}

	@Override
	protected JpaSpecificationExecutor<Address> getSpecificationExecutor() {
		return dao;
	}

	@Override
	public void managerAddressFromCustomer(Long customerId, Address address) {
		Customer customer = customerService.findOne(customerId);
		if (customer == null) {
			throw new ResourceNotFoundException("Customer not found!");
		}
		address.setCustomer(customer);
		if (address.getId() == null) {
			create(address);
		} else {
			update(address);
		}
	}

	@Override
	public Address findByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteAddressFromCustomer(Long addressId, Long customerId) {
		Customer customer = customerService.findOne(customerId);
		if (customer == null) {
			throw new ResourceNotFoundException("Customer not found!");
		}
		isClientAddress(customerId, addressId);
		dao.delete(new Address(addressId));

	}

	private void isClientAddress(Long customerId, Long addressId) {
		if (getDao().findAll(AddressSpecification.getAdressesByIdAndCustomerId(customerId, addressId)).size() > 0) {
			throw new ResourceNotFoundException("This address has not relationship with this customer");
		}

	}

	@Override
	public List<Address> listAdressesFromCustomer(Long customerId) {
		return getDao().findAll(AddressSpecification.getAdressesByCustomerId(customerId));
	}

}