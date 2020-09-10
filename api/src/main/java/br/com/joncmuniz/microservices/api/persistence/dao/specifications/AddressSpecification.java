package br.com.joncmuniz.microservices.api.persistence.dao.specifications;

import org.springframework.data.jpa.domain.Specification;

import br.com.joncmuniz.microservices.api.client.persitence.model.Address;
import br.com.joncmuniz.microservices.api.client.persitence.model.Customer;

/**
 * @author jonathan
 *
 */
public class AddressSpecification { 
	
	/**
	 * @param customerId
	 * @return
	 */
	public static Specification<Address> getAdressesByCustomerId(Long customerId) { return (root, query, criteriaBuilder) -> {
			return criteriaBuilder.equal(root.get("customer"), new Customer(customerId));
		};
	}

	/**
	 * @param customerId
	 * @param addressId
	 * @return
	 */
	public static Specification<Address> getAdressesByIdAndCustomerId(Long customerId, Long addressId) {
		return (root, query, criteriaBuilder) -> {
			return criteriaBuilder.and(criteriaBuilder.equal(root.get("customer"), new Customer(customerId)),
					criteriaBuilder.equal(root.get("id"), addressId));
		};
	}
}
