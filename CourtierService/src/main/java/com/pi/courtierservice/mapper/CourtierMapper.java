package com.pi.courtierservice.mapper;

import org.springframework.stereotype.Component;

import com.pi.courtierservice.dto.CourtierRequest;
import com.pi.courtierservice.dto.CourtierResponse;
import com.pi.courtierservice.dto.CourtierUpdateRequest;
import com.pi.courtierservice.dto.LocationRequest;
import com.pi.courtierservice.dto.LocationResponse;
import com.pi.courtierservice.entity.Courtier;
import com.pi.courtierservice.entity.Location;

@Component
public class CourtierMapper {

	public Courtier toEntity(CourtierRequest request) {
		Courtier courtier = new Courtier();
		courtier.setCin(request.cin().trim());
		courtier.setNom(request.nom().trim());
		courtier.setTelephone(request.telephone().trim());
		courtier.setDisponible(request.disponible() == null || request.disponible());
		courtier.setLocation(toEntity(request.location()));
		return courtier;
	}

	public void updateEntity(Courtier courtier, CourtierUpdateRequest request) {
		courtier.setNom(request.nom().trim());
		courtier.setTelephone(request.telephone().trim());
		courtier.setDisponible(request.disponible());
		if (request.location() != null) {
			applyLocation(courtier, request.location());
		}
	}

	/** Met à jour la position existante, ou en crée une si le courtier n'en a pas encore. */
	public void applyLocation(Courtier courtier, LocationRequest request) {
		if (courtier.getLocation() == null) {
			courtier.setLocation(toEntity(request));
		} else {
			courtier.getLocation().setLongitude(request.longitude());
			courtier.getLocation().setLatitude(request.latitude());
		}
	}

	public Location toEntity(LocationRequest request) {
		if (request == null) {
			return null;
		}
		return new Location(request.longitude(), request.latitude());
	}

	public CourtierResponse toResponse(Courtier courtier) {
		return new CourtierResponse(
				courtier.getCin(),
				courtier.getNom(),
				courtier.getTelephone(),
				courtier.isDisponible(),
				toResponse(courtier.getLocation()));
	}

	public LocationResponse toResponse(Location location) {
		if (location == null) {
			return null;
		}
		return new LocationResponse(location.getId(), location.getLongitude(), location.getLatitude());
	}
}
