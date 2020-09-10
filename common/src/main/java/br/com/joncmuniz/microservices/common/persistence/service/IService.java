package br.com.joncmuniz.microservices.common.persistence.service;

import br.com.joncmuniz.microservices.common.interfaces.IByNameApi;
import br.com.joncmuniz.microservices.common.interfaces.IWithName;

public interface IService<T extends IWithName> extends IRawService<T>, IByNameApi<T> {

}