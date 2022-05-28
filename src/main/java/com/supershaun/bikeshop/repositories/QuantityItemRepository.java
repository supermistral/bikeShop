package com.supershaun.bikeshop.repositories;

import com.supershaun.bikeshop.models.QuantityItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuantityItemRepository extends JpaRepository<QuantityItem, Long> {
}
