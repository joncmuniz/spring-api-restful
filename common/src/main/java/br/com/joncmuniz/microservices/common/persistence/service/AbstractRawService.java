package br.com.joncmuniz.microservices.common.persistence.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import br.com.joncmuniz.microservices.common.interfaces.IWithName;
import br.com.joncmuniz.microservices.common.persistence.exception.EntityNotFoundException;

@Transactional
public abstract class AbstractRawService<T extends IWithName> implements IRawService<T> {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected ApplicationEventPublisher eventPublisher;

    public AbstractRawService() {
        super();
    }

    @Override
    @Transactional(readOnly = true)
    public T findOne(final long id) {
        Optional<T> entity = getDao().findById(id);
        return entity.orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> findAll() {
        return Lists.newArrayList(getDao().findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<T> findAllPaginatedAndSortedRaw(final int page, final int size, final String sortBy, final String sortOrder) {
        final Sort sortInfo = constructSort(sortBy, sortOrder);
        return getDao().findAll(PageRequest.of(page, size, sortInfo));
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> findAllPaginatedAndSorted(final int page, final int size, final String sortBy, final String sortOrder) {
        final Sort sortInfo = constructSort(sortBy, sortOrder);
        final List<T> content = getDao().findAll(PageRequest.of(page, size, sortInfo))
            .getContent();
        if (content == null) {
            return Lists.newArrayList();
        }
        return content;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<T> findAllPaginatedRaw(final int page, final int size) {
        return getDao().findAll(PageRequest.of(page, size));
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> findAllPaginated(final int page, final int size) {
        final List<T> content = getDao().findAll(PageRequest.of(page, size))
            .getContent();
        if (content == null) {
            return Lists.newArrayList();
        }
        return content;
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> findAllSorted(final String sortBy, final String sortOrder) {
        final Sort sortInfo = constructSort(sortBy, sortOrder);
        return Lists.newArrayList(getDao().findAll(sortInfo));
    }

    @Override
    public T create(final T entity) {
        Preconditions.checkNotNull(entity);

        final T persistedEntity = getDao().save(entity);

        return persistedEntity;
    }

    @Override
    public void update(final T entity) {
        Preconditions.checkNotNull(entity);

        getDao().save(entity);
    }

    @Override
    public void deleteAll() {
        getDao().deleteAll();
    }

    @Override
    public void delete(final long id) {
        final T entity = getDao().findById(id).orElseThrow(EntityNotFoundException::new);

        getDao().delete(entity);
    }

    @Override
    public long count() {
        return getDao().count();
    }

    protected abstract PagingAndSortingRepository<T, Long> getDao();

    protected abstract JpaSpecificationExecutor<T> getSpecificationExecutor();

    protected final Sort constructSort(final String sortBy, final String sortOrder) {
        Sort sortInfo = Sort.unsorted();
        if (sortBy != null) {
            sortInfo = Sort.by(Direction.fromString(sortOrder), sortBy);
        }
        return sortInfo;
    }
}