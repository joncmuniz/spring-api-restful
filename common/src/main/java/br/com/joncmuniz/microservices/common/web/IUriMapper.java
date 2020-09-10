package br.com.joncmuniz.microservices.common.web;

import br.com.joncmuniz.microservices.common.persistence.model.IEntity;

public interface IUriMapper {

	<T extends IEntity> String getUriBase(final Class<T> clazz);

}