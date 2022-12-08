package com.sporting.api.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name = "payment")
public class Payment extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "payment_id", nullable = false)
    private String paymentId;

    @Column(name = "payment_amount", nullable = false)
    private String paymentAmount;

    @Column(name = "type", nullable = false)
    private String type;
}