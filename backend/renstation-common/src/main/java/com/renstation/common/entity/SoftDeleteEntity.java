package com.renstation.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

/**
 * Base entity providing soft delete functionality.
 */
@MappedSuperclass
@Getter
@Setter
public abstract class SoftDeleteEntity extends AuditableEntity {
    @Column(name = "deleted", nullable = false)
    private boolean deleted = false;
}
