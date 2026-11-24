package com.pi.courtierservice.service;

import java.util.List;

import com.pi.courtierservice.dto.CourtierRequest;
import com.pi.courtierservice.dto.CourtierResponse;
import com.pi.courtierservice.dto.CourtierUpdateRequest;
import com.pi.courtierservice.dto.LocationRequest;

public interface CourtierService {

	CourtierResponse create(CourtierRequest request);

	List<CourtierResponse> findAll();

	List<CourtierResponse> findAvailable();

	CourtierResponse findByCin(String cin);

	CourtierResponse update(String cin, CourtierUpdateRequest request);

	CourtierResponse updateAvailability(String cin, boolean disponible);

	CourtierResponse updateLocation(String cin, LocationRequest request);

	void delete(String cin);
}
