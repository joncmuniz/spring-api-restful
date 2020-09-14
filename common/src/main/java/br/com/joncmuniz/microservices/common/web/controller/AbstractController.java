package br.com.joncmuniz.microservices.common.web.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.joncmuniz.microservices.common.persistence.model.INameableEntity;
import br.com.joncmuniz.microservices.common.web.RestPreconditions;
import br.com.joncmuniz.microservices.common.web.events.AfterResourceCreatedEvent;

public abstract class AbstractController<T extends INameableEntity> extends AbstractReadOnlyController<T> {

    @Autowired
    public AbstractController(final Class<T> clazzToSet) {
        super(clazzToSet);
    }

    

    protected final void createInternal(final T resource, final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
    	checkOnCreate(resource);
        final T existingResource = getService().create(resource);
        publishEvent(uriBuilder, response, existingResource);
    }

    protected void publishEvent(final UriComponentsBuilder uriBuilder, final HttpServletResponse response,
			final T existingResource) {
		eventPublisher.publishEvent(new AfterResourceCreatedEvent<T>(clazz, uriBuilder, response, existingResource.getId()
            .toString()));
	}

    protected void checkOnCreate(final T resource) {
		RestPreconditions.checkRequestElementNotNull(resource);
        RestPreconditions.checkRequestState(resource.getId() == null,"The element informaded has Id");
	}

    
    protected final void updateInternal(final long id, final T resource) {
        checkOnUpdate(id, resource);        
        getService().update(resource);
    }
    
    protected void checkOnUpdate(final long id, final T resource) {
    	RestPreconditions.checkRequestElementNotNull(resource);
        RestPreconditions.checkRequestElementNotNull(resource.getId());
        RestPreconditions.checkRequestState(resource.getId() == id);
        RestPreconditions.checkNotNull(getService().findOne(resource.getId()));

    }

    protected final void deleteByIdInternal(final long id) {
        // InvalidDataAccessApiUsageException - ResourceNotFoundException
        // IllegalStateException - ResourceNotFoundException
        // DataAccessException - ignored
        getService().delete(id);
    }

}