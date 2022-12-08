package com.sporting.api.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name = "cart_detail")
public class CartDetail extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "cart_id", nullable = false)
    private String cartId;

    @Column(name = "account_id", nullable = false)
    private String accountId;

    @Column(name = "product_id", nullable = false)
    private String productId;

    @Column(name = "count", nullable = false)
    private int count;
}
