package com.pi.restaurantservice.mapper;

import org.springframework.stereotype.Component;

import com.pi.restaurantservice.dto.MenuRequest;
import com.pi.restaurantservice.dto.MenuResponse;
import com.pi.restaurantservice.entity.Menu;
import com.pi.restaurantservice.entity.Restaurant;

@Component
public class MenuMapper {

	public Menu toEntity(MenuRequest request, Restaurant restaurant) {
		Menu menu = new Menu();
		menu.setRestaurant(restaurant);
		updateEntity(menu, request);
		return menu;
	}

	public void updateEntity(Menu menu, MenuRequest request) {
		menu.setNom(request.nom().trim());
		menu.setPrix(request.prix());
	}

	public MenuResponse toResponse(Menu menu) {
		return new MenuResponse(menu.getId(), menu.getNom(), menu.getPrix(), menu.getRestaurant().getId());
	}
}
