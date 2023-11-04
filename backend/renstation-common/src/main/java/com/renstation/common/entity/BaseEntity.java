package com.renstation.common.entity;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.Setter;

/**
 * Comprehensive base entity that includes UUID, Auditing, Soft Delete, and Optimistic Locking.
 */
@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity extends SoftDeleteEntity {
    @Version
    private Long version;
}
