package br.com.joncmuniz.microservices.api.web.event;

import org.springframework.stereotype.Component;

import br.com.joncmuniz.microservices.common.web.listeners.ResourceCreatedDiscoverabilityListener;

@Component
class SecResourceCreatedDiscoverabilityListener extends ResourceCreatedDiscoverabilityListener {

    public SecResourceCreatedDiscoverabilityListener() {
        super();
    }

    //

    @Override
    protected final String getBase() {
        return "/";
    }

}
