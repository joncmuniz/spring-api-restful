package br.com.joncmuniz.microservices.api.web.hateoas;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import br.com.joncmuniz.microservices.api.client.persitence.model.Address;
import br.com.joncmuniz.microservices.api.client.persitence.model.Customer;
import br.com.joncmuniz.microservices.api.client.persitence.model.Gender;
import br.com.joncmuniz.microservices.api.persistence.dao.IAddressJpaDao;
import br.com.joncmuniz.microservices.api.persistence.dao.ICustomerJpaDao;
import br.com.joncmuniz.microservices.api.service.impl.CustomerServiceImpl;
import br.com.joncmuniz.microservices.api.spring.ApiServiceConfig;
import br.com.joncmuniz.microservices.api.spring.ApiWebConfig;

@ExtendWith(SpringExtension.class)
@AutoConfigureJsonTesters
@SpringBootTest(classes = { ApiWebConfig.class, ApiServiceConfig.class })
@AutoConfigureMockMvc(addFilters = false)
@TestInstance(Lifecycle.PER_CLASS)
/*
 * @ExtendWith(SpringExtension.class)
 * 
 * @DataJpaTest
 * 
 * @ContextConfiguration(classes = {
 * ApiPersistenceJpaConfig.class,ApiWebConfig.class,ApiServiceConfig.class },
 * loader = AnnotationConfigContextLoader.class)
 * 
 * @TestPropertySource(properties = {"persistenceTarget=postgres"})
 * 
 * @TestInstance(Lifecycle.PER_CLASS)
 * 
 * @TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
 * TransactionDbUnitTestExecutionListener.class })
 * 
 * @AutoConfigureMockMvc(addFilters = false)
 * 
 * @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
 */
public class CustomerHateoasControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CustomerServiceImpl service;
	@MockBean
	private IAddressJpaDao addressJpaDao;
	@MockBean
	private CustomerHateoasController customerHateoasController;
	@MockBean
	private ICustomerJpaDao customerJpaDao;
	
	
	@BeforeAll
	public void init() {
		
		List<Customer> customers = new ArrayList<Customer>();
		
		Customer customer = new Customer();
		customer.setId(2L);
		customer.setCpf("123654798");
		customer.setGender(Gender.FEMALE);
		customer.setName("nameToSet");
		
		Address address = new Address();
		address.setDetail("detail");
		address.setMainAddress(true);
		address.setName("name");
		address.setNumber(444);
		customer.setAdresses(Arrays.asList(address));
		
	}
	

	@Test
	public void customer1ShouldReturnNotFoundCode() throws Exception {
		this.mockMvc.perform(get("/api/hateoas/customers/1")).andDo(print()).andExpect(status().isNotFound())
				.andExpect(content().string(containsString("")));
	}
	
}
