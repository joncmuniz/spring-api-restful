package br.com.joncmuniz.microservices.api.web.hateoas;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

import br.com.joncmuniz.microservices.api.client.persitence.model.Customer;
import br.com.joncmuniz.microservices.api.spring.ApiPersistenceJpaConfig;
import br.com.joncmuniz.microservices.api.spring.ApiServiceConfig;
import br.com.joncmuniz.microservices.api.spring.ApiWebConfig;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(Lifecycle.PER_CLASS)
@ContextConfiguration(classes = { ApiPersistenceJpaConfig.class, ApiWebConfig.class,
		ApiServiceConfig.class, WebsocketSourceConfigurationTest.class })
@AutoConfigureMockMvc(addFilters = false)
@TestPropertySource(properties = {"persistenceTarget=postgres"})
@TestExecutionListeners({
    DependencyInjectionTestExecutionListener.class,
    TransactionDbUnitTestExecutionListener.class
})	
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CustomerHateoasControllerTest {

	@Autowired
	private TestRestTemplate restTemplate;
	@LocalServerPort
	private int port;

	@BeforeAll
	public void init() {

		

	}

	@Test
	@DatabaseSetup("classpath:createCustomer.xml")
	public void customersShouldReturnExistingCustomer() throws Exception {
		ResponseEntity<Customer> response = this.restTemplate.getForEntity("http://localhost:" + port + "/api/customers/1",Customer.class);
		Assertions.assertTrue(HttpStatus.OK.equals(response.getStatusCode()));	
		Assertions.assertTrue("customer1".equals(response.getBody().getName()));
		String headerLinkResult = response.getHeaders().get("Link").toString();
		Assertions.assertTrue(headerLinkResult.contains("http://localhost:" + port + "/api/customers"));
	}
	
	@Test
	@DatabaseSetup("classpath:createCustomer.xml")
	public void hateoasCustomersShouldReturnExistingCustomerWithHateoasContext() throws Exception {
		ResponseEntity<CustomerResource> response = this.restTemplate.getForEntity("http://localhost:" + port + "/api/hateoas/customers/1", CustomerResource.class);
		Assertions.assertTrue(HttpStatus.OK.equals(response.getStatusCode()));
		Assertions.assertNotNull(response.getBody());
	}
	@Test
	public void wrongURIMustReturnError() throws Exception {
		Assertions.assertTrue(this.restTemplate.getForObject("http://localhost:" + port + "/api/hateoas/wrong/end/point/1", String.class).contains("HTTP Status 404 – Not Found"));
	}
	

}
