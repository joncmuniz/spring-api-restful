package br.com.joncmuniz.microservices.common.persistence.service;

import br.com.joncmuniz.microservices.common.persistence.model.INameableEntity;

public abstract class AbstractService<T extends INameableEntity> extends AbstractRawService<T> implements IService<T> {

    public AbstractService() {
        super();
    }

}