package br.com.joncmuniz.microservices.common.util.order;

import com.google.common.collect.Ordering;

import br.com.joncmuniz.microservices.common.interfaces.IWithId;

public final class OrderById<T extends IWithId> extends Ordering<T> {

    public OrderById() {
        super();
    }

    

    @Override
    public final int compare(final T left, final T right) {
        return left.getId()
            .compareTo(right.getId());
    }

}