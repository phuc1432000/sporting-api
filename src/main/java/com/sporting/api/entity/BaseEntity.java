package com.sporting.api.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@MappedSuperclass
@Data
public abstract class BaseEntity {
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", updatable = false)
    private Date createdDate;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified_date")
    private Date modifiedDate;

    @Column(name = "active", columnDefinition = "varchar(50) default '01'")
    private String active;

    @Column(name = "created_by", columnDefinition = "varchar(50) default 'ADMIN'")
    private String createdBy;

    @Column(name = "modified_by", columnDefinition = "varchar(50) default 'ADMIN'")
    private String modifiedBy;
    //end
}
