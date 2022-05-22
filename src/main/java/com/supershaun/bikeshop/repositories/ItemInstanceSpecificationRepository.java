package com.supershaun.bikeshop.repositories;

import com.supershaun.bikeshop.models.ItemInstanceSpecification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemInstanceSpecificationRepository extends JpaRepository<ItemInstanceSpecification, Long> {
}
