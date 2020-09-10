package br.com.joncmuniz.microservices.common.web.listeners;

import javax.servlet.http.HttpServletResponse;
import static br.com.joncmuniz.microservices.common.web.WebConstants.PATH_SEP;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.common.base.Preconditions;

import br.com.joncmuniz.microservices.common.util.LinkUtil;
import br.com.joncmuniz.microservices.common.web.IUriMapper;
import br.com.joncmuniz.microservices.common.web.events.MultipleResourcesRetrievedEvent;

@SuppressWarnings("rawtypes")
@Component
final class MultipleResourcesRetrievedDiscoverabilityListener implements ApplicationListener<MultipleResourcesRetrievedEvent> {

    @Autowired
    private IUriMapper uriMapper;

    public MultipleResourcesRetrievedDiscoverabilityListener() {
        super();
    }

    //

    @Override
    public final void onApplicationEvent(final MultipleResourcesRetrievedEvent ev) {
        Preconditions.checkNotNull(ev);

        discoverOtherRetrievalOperations(ev.getUriBuilder(), ev.getResponse(), ev.getClazz());
    }

    @SuppressWarnings("unchecked")
    final void discoverOtherRetrievalOperations(final UriComponentsBuilder uriBuilder, final HttpServletResponse response, final Class clazz) {
        final String uriForResourceCreation = uriBuilder.path(PATH_SEP + uriMapper.getUriBase(clazz) + "/q=name=something")
            .build()
            .encode()
            .toUriString();

        final String linkHeaderValue = LinkUtil.createLinkHeader(uriForResourceCreation, LinkUtil.REL_COLLECTION);
        response.addHeader(HttpHeaders.LINK, linkHeaderValue);
    }

}