package com.pi.courtierservice.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pi.courtierservice.dto.CourtierRequest;
import com.pi.courtierservice.dto.CourtierResponse;
import com.pi.courtierservice.dto.CourtierUpdateRequest;
import com.pi.courtierservice.dto.LocationRequest;
import com.pi.courtierservice.service.CourtierService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/courtiers")
public class CourtierController {

	private final CourtierService courtierService;

	public CourtierController(CourtierService courtierService) {
		this.courtierService = courtierService;
	}

	@PostMapping
	public ResponseEntity<CourtierResponse> create(@Valid @RequestBody CourtierRequest request) {
		CourtierResponse created = courtierService.create(request);
		return ResponseEntity.created(URI.create("/api/courtiers/" + created.cin())).body(created);
	}

	@GetMapping
	public List<CourtierResponse> findAll() {
		return courtierService.findAll();
	}

	@GetMapping("/disponibles")
	public List<CourtierResponse> findAvailable() {
		return courtierService.findAvailable();
	}

	@GetMapping("/{cin}")
	public CourtierResponse findByCin(@PathVariable String cin) {
		return courtierService.findByCin(cin);
	}

	@PutMapping("/{cin}")
	public CourtierResponse update(@PathVariable String cin, @Valid @RequestBody CourtierUpdateRequest request) {
		return courtierService.update(cin, request);
	}

	@PatchMapping("/{cin}/disponibilite")
	public CourtierResponse updateAvailability(@PathVariable String cin, @RequestParam boolean disponible) {
		return courtierService.updateAvailability(cin, disponible);
	}

	@PutMapping("/{cin}/location")
	public CourtierResponse updateLocation(@PathVariable String cin, @Valid @RequestBody LocationRequest request) {
		return courtierService.updateLocation(cin, request);
	}

	@DeleteMapping("/{cin}")
	public ResponseEntity<Void> delete(@PathVariable String cin) {
		courtierService.delete(cin);
		return ResponseEntity.noContent().build();
	}
}
