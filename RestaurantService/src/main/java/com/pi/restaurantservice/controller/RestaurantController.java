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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pi.restaurantservice.dto.RestaurantRequest;
import com.pi.restaurantservice.dto.RestaurantResponse;
import com.pi.restaurantservice.service.RestaurantService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

	private final RestaurantService restaurantService;

	public RestaurantController(RestaurantService restaurantService) {
		this.restaurantService = restaurantService;
	}

	@PostMapping
	public ResponseEntity<RestaurantResponse> create(@Valid @RequestBody RestaurantRequest request) {
		RestaurantResponse created = restaurantService.create(request);
		return ResponseEntity.created(URI.create("/api/restaurants/" + created.id())).body(created);
	}

	@GetMapping
	public List<RestaurantResponse> findAll(@RequestParam(required = false) String ville) {
		return restaurantService.findAll(ville);
	}

	@GetMapping("/{id}")
	public RestaurantResponse findById(@PathVariable Long id) {
		return restaurantService.findById(id);
	}

	@PutMapping("/{id}")
	public RestaurantResponse update(@PathVariable Long id, @Valid @RequestBody RestaurantRequest request) {
		return restaurantService.update(id, request);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		restaurantService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
