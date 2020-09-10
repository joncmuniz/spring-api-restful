package br.com.joncmuniz.microservices.common.web.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.RepresentationModel;

import br.com.joncmuniz.microservices.common.persistence.model.INameableEntity;
import br.com.joncmuniz.microservices.common.persistence.service.IRawService;
import br.com.joncmuniz.microservices.common.web.RestPreconditions;
import br.com.joncmuniz.microservices.common.web.exception.ResourceNotFoundException;

/**
 * @author jonathan
 *
 * @param <D>
 * @param <E>
 */
public abstract class AbstractHateoasController<D extends RepresentationModel, E extends INameableEntity> {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected ApplicationEventPublisher eventPublisher;

    /**
     * @param id
     * @return
     */
    protected final D findOneInternal(final Long id) {
        final E entityNotNull = RestPreconditions.checkNotNull(getService().findOne(id));
        return convert(entityNotNull);
    }


    /**
     * @param request
     * @return
     */
    protected final List<D> findAllInternal(final HttpServletRequest request) {
        if (request.getParameterNames()
            .hasMoreElements()) {
            throw new ResourceNotFoundException();
        }

        return convertList(getService().findAll());
    }

    /**
     * @param page
     * @param size
     * @param sortBy
     * @param sortOrder
     * @return
     */
    protected final List<D> findPaginatedAndSortedInternal(final int page, final int size, final String sortBy, final String sortOrder) {
        final Page<E> resultPage = getService().findAllPaginatedAndSortedRaw(page, size, sortBy, sortOrder);
        if (page > resultPage.getTotalPages()) {
            throw new ResourceNotFoundException();
        }

        return convertList(resultPage.getContent());
    }

    /**
     * @param page
     * @param size
     * @return
     */
    protected final List<D> findPaginatedInternal(final int page, final int size) {
        final Page<E> resultPage = getService().findAllPaginatedRaw(page, size);
        if (page > resultPage.getTotalPages()) {
            throw new ResourceNotFoundException();
        }

        return convertList(resultPage.getContent());
    }

    /**
     * @param sortBy
     * @param sortOrder
     * @return
     */
    protected final List<D> findAllSortedInternal(final String sortBy, final String sortOrder) {
        return convertList(getService().findAllSorted(sortBy, sortOrder));
    }


    /**
     * @return
     */
    protected final long countInternal() {
        return getService().count();
    }


    /**
     * @param resource
     */
    protected final void createInternal(final E resource) {
        RestPreconditions.checkRequestElementNotNull(resource);
        RestPreconditions.checkRequestState(resource.getId() == null);
        getService().create(resource);
    }

    /**
     * @param id
     * @param resource
     */
    protected final void updateInternal(final long id, final E resource) {
        RestPreconditions.checkRequestElementNotNull(resource);
        RestPreconditions.checkRequestElementNotNull(resource.getId());
        RestPreconditions.checkRequestState(resource.getId() == id );
        RestPreconditions.checkNotNull(getService().findOne(resource.getId()));

        getService().update(resource);
    }


    /**
     * @param id
     */
    protected final void deleteByIdInternal(final long id) {
        getService().delete(id); }
    
    /**
     * @param resource
     */
    protected void checkOnCreate(final E resource) {
		RestPreconditions.checkRequestElementNotNull(resource);
        RestPreconditions.checkRequestState(resource.getId() == null);
	}

    

    public long count() {
        return countInternal();
    }

    

    protected abstract IRawService<E> getService();

    private final List<D> convertList(final List<E> entities) {
        return entities.stream()
            .map(this::convert)
            .collect(Collectors.toList());
    }

    protected abstract D convert(final E entity);

}