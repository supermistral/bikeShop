package com.supershaun.bikeshop.repositories;

import com.supershaun.bikeshop.models.CategorySpecification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategorySpecificationRepository extends JpaRepository<CategorySpecification, Long> {
}
