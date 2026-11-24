package com.pi.restaurantservice.service;

import java.util.List;

import com.pi.restaurantservice.dto.MenuRequest;
import com.pi.restaurantservice.dto.MenuResponse;

public interface MenuService {

	MenuResponse create(Long restaurantId, MenuRequest request);

	List<MenuResponse> findByRestaurant(Long restaurantId);

	MenuResponse findById(Long restaurantId, Long menuId);

	MenuResponse update(Long restaurantId, Long menuId, MenuRequest request);

	void delete(Long restaurantId, Long menuId);
}
