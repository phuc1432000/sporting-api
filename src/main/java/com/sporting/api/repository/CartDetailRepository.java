package com.sporting.api.repository;

import com.sporting.api.entity.CartDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartDetailRepository extends JpaRepository<CartDetail, Long> {
    CartDetail findByCartId(String cartId);
}
