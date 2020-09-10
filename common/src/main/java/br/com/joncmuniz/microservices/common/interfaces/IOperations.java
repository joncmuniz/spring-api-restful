package br.com.joncmuniz.microservices.common.interfaces;

import java.io.Serializable;
import java.util.List;

public interface IOperations<T extends Serializable> {

    

    T findOne(final long id);

    /**
     * - contract: if nothing is found, an empty list will be returned to the calling client <br>
     */
    List<T> findAll();

    /**
     * - contract: if nothing is found, an empty list will be returned to the calling client <br>
     */
    List<T> findAllSorted(final String sortBy, final String sortOrder);

    /**
     * - contract: if nothing is found, an empty list will be returned to the calling client <br>
     */
    List<T> findAllPaginated(final int page, final int size);

    /**
     * - contract: if nothing is found, an empty list will be returned to the calling client <br>
     */
    List<T> findAllPaginatedAndSorted(final int page, final int size, final String sortBy, final String sortOrder);

    

    T create(final T resource);

    

    void update(final T resource);

    

    void delete(final long id);

    void deleteAll();

    

    long count();

}