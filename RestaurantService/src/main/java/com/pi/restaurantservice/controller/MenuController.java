package com.pi.restaurantservice.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pi.restaurantservice.dto.MenuRequest;
import com.pi.restaurantservice.dto.MenuResponse;
import com.pi.restaurantservice.service.MenuService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/restaurants/{restaurantId}/menus")
public class MenuController {

	private final MenuService menuService;

	public MenuController(MenuService menuService) {
		this.menuService = menuService;
	}

	@PostMapping
	public ResponseEntity<MenuResponse> create(@PathVariable Long restaurantId,
			@Valid @RequestBody MenuRequest request) {
		MenuResponse created = menuService.create(restaurantId, request);
		return ResponseEntity.created(URI.create("/api/restaurants/" + restaurantId + "/menus/" + created.id()))
				.body(created);
	}

	@GetMapping
	public List<MenuResponse> findByRestaurant(@PathVariable Long restaurantId) {
		return menuService.findByRestaurant(restaurantId);
	}

	@GetMapping("/{menuId}")
	public MenuResponse findById(@PathVariable Long restaurantId, @PathVariable Long menuId) {
		return menuService.findById(restaurantId, menuId);
	}

	@PutMapping("/{menuId}")
	public MenuResponse update(@PathVariable Long restaurantId, @PathVariable Long menuId,
			@Valid @RequestBody MenuRequest request) {
		return menuService.update(restaurantId, menuId, request);
	}

	@DeleteMapping("/{menuId}")
	public ResponseEntity<Void> delete(@PathVariable Long restaurantId, @PathVariable Long menuId) {
		menuService.delete(restaurantId, menuId);
		return ResponseEntity.noContent().build();
	}
}
