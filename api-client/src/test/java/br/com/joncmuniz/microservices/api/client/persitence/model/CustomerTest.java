package br.com.joncmuniz.microservices.api.client.persitence.model;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

public class CustomerTest {
	
	private static int customersTests = 3;
	
	private static Map<String, List<Customer>> customers = new HashMap<String, List<Customer>>();
	
	@ParameterizedTest
	@ArgumentsSource(ArgumentsLoaderProvider.class)
	@DisplayName("validateCustomerAge")
	@Order(value = 1)
	public void validateCustomerAge(CustomerContextData customerContextData) {
		Assertions.assertTrue(customerContextData.getAgeExpected().equals(customerContextData.getCustomer().getAge()), "Validate age for Customer: "+ customerContextData.getCustomer().getName());
	}
	
	@BeforeAll
	public static void initTests() throws IOException, URISyntaxException {
		customers.put("validateCustomerAge", createRamdomCustomers());
	}
	
	private static List<Customer> createRamdomCustomers() {
		List<Customer> customers = new ArrayList<Customer>();
		Customer customer;
		for (int i = 0; i < customersTests; i++) {
			customer = new Customer();
			String customerName = RandomStringUtils.randomAlphabetic(10);
			Date birthDate = createDate();
			customer.setName(customerName);
			customer.setBirthDate(birthDate);
			customers.add(customer);
		}
		
		return customers;
	}

	private static Date createDate() {
		GregorianCalendar initialDate = new GregorianCalendar();
		initialDate.add(GregorianCalendar.YEAR, -100);
		
		GregorianCalendar finalDate = new GregorianCalendar();
		finalDate.add(GregorianCalendar.YEAR, -18);
		
		Calendar randomDate =GregorianCalendar.getInstance();
		randomDate.setTime(new Date(ThreadLocalRandom.current()
                .nextLong(initialDate.getTime().getTime(), finalDate.getTime().getTime())));
		return randomDate.getTime();
	}

	public static class ArgumentsLoaderProvider implements ArgumentsProvider {
		public ArgumentsLoaderProvider() {
		}

		@Override
		public Stream<? extends Arguments> provideArguments(final ExtensionContext context) {
			final List<Arguments> parametersTest = new ArrayList<>();
			
			for (Customer customer:customers.get(context.getDisplayName())) {
				CustomerContextData customerContextData = new CustomerContextData(customer, createExpectedAge(customer.getBirthDate()));
				parametersTest
						.add(Arguments.of(customerContextData));
			}
			return parametersTest.stream();
		}

		private Integer createExpectedAge(Date birthDate) {
			 return Period.between(birthDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), LocalDate.now())
					.getYears();
		}
	}
}
