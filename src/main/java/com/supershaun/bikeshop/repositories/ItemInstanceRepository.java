package com.supershaun.bikeshop.repositories;

import com.supershaun.bikeshop.models.ItemInstance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemInstanceRepository extends JpaRepository<ItemInstance, Long> {
}
