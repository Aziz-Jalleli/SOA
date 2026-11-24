package com.pi.restaurantservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pi.restaurantservice.entity.Restaurant;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

	List<Restaurant> findByAdresseVilleIgnoreCase(String ville);
}
