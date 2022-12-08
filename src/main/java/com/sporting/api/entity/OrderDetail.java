package com.sporting.api.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name = "order_detail")
public class OrderDetail extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "order_id", nullable = false)
    private String orderId;

    @Column(name = "account_id", nullable = false)
    private String accountId;

    @Column(name = "product_id", nullable = false)
    private String productId;
}
