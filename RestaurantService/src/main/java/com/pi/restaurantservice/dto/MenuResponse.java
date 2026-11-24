package com.pi.restaurantservice.dto;

import java.math.BigDecimal;

public record MenuResponse(Long id, String nom, BigDecimal prix, Long restaurantId) {
}
