package br.com.joncmuniz.microservices.common.web;

import org.springframework.stereotype.Component;

import br.com.joncmuniz.microservices.common.persistence.model.IEntity;

@Component
public class UriMapper implements IUriMapper {

    public UriMapper() {
        super();
    }

    

    /**
     * - note: at this point, the URI is transformed into plural (added `s`) in a hardcoded way - this will change in the future
     */
    @Override
    public <T extends IEntity> String getUriBase(final Class<T> clazz) {
        return clazz.getSimpleName()
            .toString()
            .toLowerCase() + "s";
    }

}