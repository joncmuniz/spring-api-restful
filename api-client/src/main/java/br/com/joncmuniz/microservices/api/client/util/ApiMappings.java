package br.com.joncmuniz.microservices.api.client.util;

public class ApiMappings {


    public static final class Plural {
    	public static final String CUSTOMERS = "customers";
		public static final String ADRESSES = "adresses";
		public static final String EMPTY = "";
		
        
    }
    public static final class Singular {

        public static final String CUSTOMER = "customer";

    }

    public static final class Hateoas {
        private static final String HATEOAS = "/hateoas/";
        public static final String CUSTOMER = HATEOAS + Plural.CUSTOMERS;
        public static final String ADRESSES = HATEOAS + Plural.ADRESSES;
        public static final String EMPTY = HATEOAS + "";
    }

    private ApiMappings() {
        throw new AssertionError();
    }

    

}
