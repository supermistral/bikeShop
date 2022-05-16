package com.supershaun.bikeshop.repositories;

import com.supershaun.bikeshop.models.ItemSpecification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemSpecificationRepository extends JpaRepository<ItemSpecification, Long> {
}
