package br.com.joncmuniz.microservices.api.client.persitence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.joncmuniz.microservices.common.interfaces.INameableDto;
import br.com.joncmuniz.microservices.common.persistence.model.INameableEntity;

@Entity
@XmlRootElement
public class Address implements INameableEntity, INameableDto {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonIgnore
	private Long id;

	@Column(nullable = false, unique = false, length = 35)
	private String name;

	@Column(nullable = false, unique = false)
	private String street;

	@Column(nullable = false, unique = false)
	private Integer number;

	@Column(nullable = false, unique = false, length = 50)
	private String detail;
	
	@Column(nullable = false, unique = false)
	private Boolean mainAddress;
	
	@ManyToOne
    @JoinColumn(name="customer_id", nullable=false)
	@JsonIgnore
    private Customer customer;
	
	public Address() {
	}

	public Address(Long addressId) {
		this.id=addressId;
	}

	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Boolean getMainAddress() {
		return mainAddress;
	}

	public void setMainAddress(Boolean mainAddress) {
		this.mainAddress = mainAddress;
	}
	
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Address other = (Address) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("id", id).append("name", name).toString();
	}
	
}
