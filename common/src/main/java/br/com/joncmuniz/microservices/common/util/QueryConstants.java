package br.com.joncmuniz.microservices.common.util;

public final class QueryConstants {

    public static final String QUESTIONMARK = "?";

    public static final String PAGE = "page";
    public static final String SIZE = "size";
    public static final String SORT_BY = "sortBy";
    public static final String SORT_ORDER = "sortOrder";

    public static final String ANY_SERVER = "%";
    public static final String ANY_CLIENT = "*";
    public static final String QUERY_PREFIX = QUESTIONMARK + "q=";
    public static final String SEPARATOR = ",";
    public static final String SEPARATOR_AMPER = "&";

    public static final String ID = "id"; 
    public static final String NAME = SearchField.name.toString();
    public static final String UUID = "uuid";

    private QueryConstants() {
        throw new AssertionError();
    }
}