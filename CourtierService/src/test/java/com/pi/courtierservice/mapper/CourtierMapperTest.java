package com.pi.courtierservice.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.pi.courtierservice.dto.CourtierRequest;
import com.pi.courtierservice.dto.CourtierResponse;
import com.pi.courtierservice.dto.LocationRequest;
import com.pi.courtierservice.entity.Courtier;

class CourtierMapperTest {

	private final CourtierMapper mapper = new CourtierMapper();

	@Test
	void toEntity_defaultsToAvailableAndMapsLocation() {
		CourtierRequest request = new CourtierRequest("12345678", " Ali ", "+21620123456", null,
				new LocationRequest(10.18, 36.80));

		Courtier courtier = mapper.toEntity(request);

		assertThat(courtier.getCin()).isEqualTo("12345678");
		assertThat(courtier.getNom()).isEqualTo("Ali");
		assertThat(courtier.isDisponible()).isTrue();
		assertThat(courtier.getLocation().getLongitude()).isEqualTo(10.18);
		assertThat(courtier.getLocation().getLatitude()).isEqualTo(36.80);
	}

	@Test
	void toResponse_handlesMissingLocation() {
		Courtier courtier = mapper.toEntity(new CourtierRequest("12345678", "Ali", "20123456", false, null));

		CourtierResponse response = mapper.toResponse(courtier);

		assertThat(response.disponible()).isFalse();
		assertThat(response.location()).isNull();
	}

	@Test
	void applyLocation_createsThenUpdatesLocation() {
		Courtier courtier = mapper.toEntity(new CourtierRequest("12345678", "Ali", "20123456", true, null));

		mapper.applyLocation(courtier, new LocationRequest(10.0, 36.0));
		assertThat(courtier.getLocation().getLongitude()).isEqualTo(10.0);

		mapper.applyLocation(courtier, new LocationRequest(11.0, 37.0));
		assertThat(courtier.getLocation().getLongitude()).isEqualTo(11.0);
		assertThat(courtier.getLocation().getLatitude()).isEqualTo(37.0);
	}
}
