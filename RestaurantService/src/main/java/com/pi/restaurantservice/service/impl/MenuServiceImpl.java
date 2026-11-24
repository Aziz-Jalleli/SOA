package com.pi.restaurantservice.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pi.restaurantservice.dto.MenuRequest;
import com.pi.restaurantservice.dto.MenuResponse;
import com.pi.restaurantservice.entity.Menu;
import com.pi.restaurantservice.entity.Restaurant;
import com.pi.restaurantservice.exception.ResourceNotFoundException;
import com.pi.restaurantservice.mapper.MenuMapper;
import com.pi.restaurantservice.repository.MenuRepository;
import com.pi.restaurantservice.repository.RestaurantRepository;
import com.pi.restaurantservice.service.MenuService;

@Service
@Transactional(readOnly = true)
public class MenuServiceImpl implements MenuService {

	private final MenuRepository menuRepository;
	private final RestaurantRepository restaurantRepository;
	private final MenuMapper menuMapper;

	public MenuServiceImpl(MenuRepository menuRepository, RestaurantRepository restaurantRepository,
			MenuMapper menuMapper) {
		this.menuRepository = menuRepository;
		this.restaurantRepository = restaurantRepository;
		this.menuMapper = menuMapper;
	}

	@Override
	@Transactional
	public MenuResponse create(Long restaurantId, MenuRequest request) {
		Restaurant restaurant = getRestaurantOrThrow(restaurantId);
		Menu saved = menuRepository.save(menuMapper.toEntity(request, restaurant));
		return menuMapper.toResponse(saved);
	}

	@Override
	public List<MenuResponse> findByRestaurant(Long restaurantId) {
		getRestaurantOrThrow(restaurantId);
		return menuRepository.findByRestaurantId(restaurantId).stream().map(menuMapper::toResponse).toList();
	}

	@Override
	public MenuResponse findById(Long restaurantId, Long menuId) {
		return menuMapper.toResponse(getMenuOrThrow(restaurantId, menuId));
	}

	@Override
	@Transactional
	public MenuResponse update(Long restaurantId, Long menuId, MenuRequest request) {
		Menu menu = getMenuOrThrow(restaurantId, menuId);
		menuMapper.updateEntity(menu, request);
		return menuMapper.toResponse(menuRepository.save(menu));
	}

	@Override
	@Transactional
	public void delete(Long restaurantId, Long menuId) {
		menuRepository.delete(getMenuOrThrow(restaurantId, menuId));
	}

	private Restaurant getRestaurantOrThrow(Long restaurantId) {
		return restaurantRepository.findById(restaurantId).orElseThrow(
				() -> new ResourceNotFoundException("Restaurant introuvable avec l'id " + restaurantId));
	}

	private Menu getMenuOrThrow(Long restaurantId, Long menuId) {
		return menuRepository.findByIdAndRestaurantId(menuId, restaurantId)
				.orElseThrow(() -> new ResourceNotFoundException(
						"Menu " + menuId + " introuvable pour le restaurant " + restaurantId));
	}
}
