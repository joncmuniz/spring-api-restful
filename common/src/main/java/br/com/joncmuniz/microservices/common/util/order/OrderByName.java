package br.com.joncmuniz.microservices.common.util.order;

import com.google.common.collect.Ordering;

import br.com.joncmuniz.microservices.common.interfaces.IWithName;

public final class OrderByName<T extends IWithName> extends Ordering<T> {

    public OrderByName() {
        super();
    }

    

    @Override
    public final int compare(final T left, final T right) {
        return left.getName()
            .compareTo(right.getName());
    }

}