package com.pi.courtierservice.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public record LocationRequest(
		@NotNull(message = "La longitude est obligatoire")
		@DecimalMin(value = "-180.0", message = "La longitude doit être comprise entre -180 et 180")
		@DecimalMax(value = "180.0", message = "La longitude doit être comprise entre -180 et 180")
		Double longitude,

		@NotNull(message = "La latitude est obligatoire")
		@DecimalMin(value = "-90.0", message = "La latitude doit être comprise entre -90 et 90")
		@DecimalMax(value = "90.0", message = "La latitude doit être comprise entre -90 et 90")
		Double latitude) {
}
