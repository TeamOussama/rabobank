package com.rabobank.socle.service;

import com.rabobank.socle.common.exception.EntityNotFoundException;
import com.rabobank.socle.data.entity.base.BaseEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.annotation.Nonnull;

public class EntityBaseService<T extends BaseEntity> {

    private PagingAndSortingRepository<T, Long> repository;
    private Class<T> entityType;

    public EntityBaseService(Class<T> entityType, PagingAndSortingRepository<T, Long> repository) {
        this.entityType = entityType;
        this.repository = repository;
    }

    /**
     * Get the Entity from the database.
     *
     * @param entityId The id of the Entity to look up.
     * @return The entity from the database.
     * @throws EntityNotFoundException if the entity with that id cannot be found.
     */

    public T getEntityByIdThrowing(@Nonnull Long entityId) throws EntityNotFoundException {
        return repository.findById(entityId)
            .orElseThrow(() -> new EntityNotFoundException(entityType, entityId));
    }
}
