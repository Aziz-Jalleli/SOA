package com.pi.restaurantservice.service;

import java.util.List;

import com.pi.restaurantservice.dto.RestaurantRequest;
import com.pi.restaurantservice.dto.RestaurantResponse;

public interface RestaurantService {

	RestaurantResponse create(RestaurantRequest request);

	/** Liste les restaurants, filtrés par ville si {@code ville} est renseignée. */
	List<RestaurantResponse> findAll(String ville);

	RestaurantResponse findById(Long id);

	RestaurantResponse update(Long id, RestaurantRequest request);

	void delete(Long id);
}
