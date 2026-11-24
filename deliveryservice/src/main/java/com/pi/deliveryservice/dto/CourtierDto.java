package com.pi.deliveryservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/** Minimal view of a courier as returned by CourtierService. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record CourtierDto(
        String cin,
        String nom,
        String telephone,
        Boolean disponible
) {
}
