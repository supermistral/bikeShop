package com.supershaun.bikeshop.repositories;

import com.supershaun.bikeshop.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findAllByCategoryId(Long id);
}
