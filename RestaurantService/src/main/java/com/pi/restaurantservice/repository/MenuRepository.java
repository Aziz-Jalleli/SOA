package com.pi.restaurantservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pi.restaurantservice.entity.Menu;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

	List<Menu> findByRestaurantId(Long restaurantId);

	Optional<Menu> findByIdAndRestaurantId(Long id, Long restaurantId);
}
