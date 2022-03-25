/*
 * Copyright (C) 2013, 2019, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.rabobank.socle.data.entity.base;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Date;
import java.util.Objects;

@Data
@NoArgsConstructor
@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @CreatedDate
    private Date createdOn;
    @LastModifiedDate
    private Date lastUpdated;

    @Override
    public int hashCode() {
        if (getId() == null) {
            return super.hashCode();
        }
        return Objects.hashCode(getId());
    }

    /**
     * This is based on hibernates recommendation not to use identity equality.
     *
     * @param obj The object to compare.
     * @return True if either object is an entity of the same class and their ids are equal. When both entities have a
     * null id this also returns true.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        BaseEntity other = (BaseEntity) obj;
        return Objects.equals(getId(), other.getId());
    }
}
