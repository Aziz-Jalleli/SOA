package com.pi.restaurantservice.mapper;

import org.springframework.stereotype.Component;

import com.pi.restaurantservice.dto.AdresseRequest;
import com.pi.restaurantservice.dto.AdresseResponse;
import com.pi.restaurantservice.dto.RestaurantRequest;
import com.pi.restaurantservice.dto.RestaurantResponse;
import com.pi.restaurantservice.entity.Adresse;
import com.pi.restaurantservice.entity.Restaurant;

@Component
public class RestaurantMapper {

	public Restaurant toEntity(RestaurantRequest request) {
		Restaurant restaurant = new Restaurant();
		updateEntity(restaurant, request);
		return restaurant;
	}

	public void updateEntity(Restaurant restaurant, RestaurantRequest request) {
		restaurant.setNom(request.nom().trim());
		restaurant.setAdresse(toEntity(request.adresse()));
	}

	public Adresse toEntity(AdresseRequest request) {
		return new Adresse(
				request.numero().trim(),
				request.rue().trim(),
				request.cite().trim(),
				request.ville().trim());
	}

	public RestaurantResponse toResponse(Restaurant restaurant) {
		return new RestaurantResponse(restaurant.getId(), restaurant.getNom(), toResponse(restaurant.getAdresse()));
	}

	public AdresseResponse toResponse(Adresse adresse) {
		if (adresse == null) {
			return null;
		}
		return new AdresseResponse(adresse.getNumero(), adresse.getRue(), adresse.getCite(), adresse.getVille());
	}
}
