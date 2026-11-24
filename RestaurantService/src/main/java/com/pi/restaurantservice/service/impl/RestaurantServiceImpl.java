package com.pi.restaurantservice.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pi.restaurantservice.dto.RestaurantRequest;
import com.pi.restaurantservice.dto.RestaurantResponse;
import com.pi.restaurantservice.entity.Restaurant;
import com.pi.restaurantservice.exception.ResourceNotFoundException;
import com.pi.restaurantservice.mapper.RestaurantMapper;
import com.pi.restaurantservice.repository.RestaurantRepository;
import com.pi.restaurantservice.service.RestaurantService;

@Service
@Transactional(readOnly = true)
public class RestaurantServiceImpl implements RestaurantService {

	private final RestaurantRepository restaurantRepository;
	private final RestaurantMapper restaurantMapper;

	public RestaurantServiceImpl(RestaurantRepository restaurantRepository, RestaurantMapper restaurantMapper) {
		this.restaurantRepository = restaurantRepository;
		this.restaurantMapper = restaurantMapper;
	}

	@Override
	@Transactional
	public RestaurantResponse create(RestaurantRequest request) {
		Restaurant saved = restaurantRepository.save(restaurantMapper.toEntity(request));
		return restaurantMapper.toResponse(saved);
	}

	@Override
	public List<RestaurantResponse> findAll(String ville) {
		List<Restaurant> restaurants = (ville == null || ville.isBlank())
				? restaurantRepository.findAll()
				: restaurantRepository.findByAdresseVilleIgnoreCase(ville.trim());
		return restaurants.stream().map(restaurantMapper::toResponse).toList();
	}

	@Override
	public RestaurantResponse findById(Long id) {
		return restaurantMapper.toResponse(getOrThrow(id));
	}

	@Override
	@Transactional
	public RestaurantResponse update(Long id, RestaurantRequest request) {
		Restaurant restaurant = getOrThrow(id);
		restaurantMapper.updateEntity(restaurant, request);
		return restaurantMapper.toResponse(restaurantRepository.save(restaurant));
	}

	@Override
	@Transactional
	public void delete(Long id) {
		restaurantRepository.delete(getOrThrow(id));
	}

	private Restaurant getOrThrow(Long id) {
		return restaurantRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Restaurant introuvable avec l'id " + id));
	}
}
