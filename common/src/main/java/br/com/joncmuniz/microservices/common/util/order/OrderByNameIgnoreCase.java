package br.com.joncmuniz.microservices.common.util.order;

import com.google.common.collect.Ordering;

import br.com.joncmuniz.microservices.common.persistence.model.INameableEntity;

public final class OrderByNameIgnoreCase<T extends INameableEntity> extends Ordering<T> {

    public OrderByNameIgnoreCase() {
        super();
    }

    

    @Override
    public final int compare(final T left, final T right) {
        return left.getName()
            .compareToIgnoreCase(right.getName());
    }

}