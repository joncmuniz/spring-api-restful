package br.com.joncmuniz.microservices.api.service.impl;

import javax.sql.DataSource;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

import br.com.joncmuniz.microservices.api.persistence.dao.ICustomerJpaDao;
import br.com.joncmuniz.microservices.api.spring.ApiPersistenceJpaConfig;


@ExtendWith(SpringExtension.class)
@DataJpaTest
@ContextConfiguration(classes = { ApiPersistenceJpaConfig.class }, loader = AnnotationConfigContextLoader.class)
@TestPropertySource(properties = {"persistenceTarget=postgres"})
@TestInstance(Lifecycle.PER_CLASS)
@TestExecutionListeners({
    DependencyInjectionTestExecutionListener.class,
    TransactionDbUnitTestExecutionListener.class
})	

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CustomerRepositoryIntegrationTest {

	@Autowired
    public ICustomerJpaDao customerRepository;
	@Autowired
	public ApplicationContext appContext;
	
	@Autowired
	public DataSource dataSource;
	
	@Test
	@DatabaseSetup("classpath:createCustomer.xml")
	public void whenValidName_thenCustomersShouldBeFound() {
		Assertions.assertNotNull(customerRepository.findByName("customer1"));
		
	}
}