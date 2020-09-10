package br.com.joncmuniz.microservices.api.client.persitence.model;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.joncmuniz.microservices.common.interfaces.INameableDto;
import br.com.joncmuniz.microservices.common.persistence.model.INameableEntity;

@Entity
@XmlRootElement
public class Customer implements INameableEntity, INameableDto {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonIgnore
	private Long id;

	@Column(nullable = false, unique = false)
	private String name;

	@Column(unique = true, nullable = true)
	private String cpf;

	@Transient
	private Integer age;

	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date birthDate;

	@Enumerated(EnumType.STRING)
	private Gender gender;

	@OneToMany(mappedBy="customer", cascade = { CascadeType.REMOVE, CascadeType.PERSIST }, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Address> adresses;

	public Customer() {
		super();

	}

	public Customer(final Long id) {
		this.id=id;
	}
	
	public Customer(final String nameToSet, final String passwordToSet, final List<Address> adressesToSet,
			final Gender genderToSet) {
		super();

		name = nameToSet;
		adresses = adressesToSet;
		gender = genderToSet;
	}

	

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(final Long idToSet) {
		id = idToSet;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(final String nameToSet) {
		name = nameToSet;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Integer getAge() {
		GregorianCalendar date = new GregorianCalendar();
		date.setTime(birthDate);
		return Period.between(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), LocalDate.now())
				.getYears();
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}
	
	public List<Address> getAdresses() {
		return adresses;
	}

	public void setAdresses(List<Address> adresses) {
		this.adresses = adresses;
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
		Customer other = (Customer) obj;
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
