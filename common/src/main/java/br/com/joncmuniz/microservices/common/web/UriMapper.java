package br.com.joncmuniz.microservices.common.web;

import org.springframework.stereotype.Component;

import br.com.joncmuniz.microservices.common.persistence.model.IEntity;

@Component
public class UriMapper implements IUriMapper {

    public UriMapper() {
        super();
    }

    

    /**
     */
    @Override
    public <T extends IEntity> String getUriBase(final Class<T> clazz) {
        return clazz.getSimpleName()
            .toString()
            .toLowerCase() + "s";
    }

}