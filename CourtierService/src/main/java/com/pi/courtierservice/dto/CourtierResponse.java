package com.pi.courtierservice.dto;

public record CourtierResponse(
		String cin,
		String nom,
		String telephone,
		boolean disponible,
		LocationResponse location) {
}
