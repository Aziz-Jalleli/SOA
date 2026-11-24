package com.pi.restaurantservice.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import com.pi.restaurantservice.dto.AdresseRequest;
import com.pi.restaurantservice.dto.MenuRequest;
import com.pi.restaurantservice.dto.MenuResponse;
import com.pi.restaurantservice.dto.RestaurantRequest;
import com.pi.restaurantservice.dto.RestaurantResponse;
import com.pi.restaurantservice.entity.Menu;
import com.pi.restaurantservice.entity.Restaurant;

class RestaurantMapperTest {

	private final RestaurantMapper restaurantMapper = new RestaurantMapper();
	private final MenuMapper menuMapper = new MenuMapper();

	@Test
	void restaurant_isMappedWithItsAddress() {
		RestaurantRequest request = new RestaurantRequest(" Chez Salah ",
				new AdresseRequest("12B", "Rue de la Liberté", "Cité Ennasr", "Tunis"));

		Restaurant restaurant = restaurantMapper.toEntity(request);
		RestaurantResponse response = restaurantMapper.toResponse(restaurant);

		assertThat(response.nom()).isEqualTo("Chez Salah");
		assertThat(response.adresse().numero()).isEqualTo("12B");
		assertThat(response.adresse().rue()).isEqualTo("Rue de la Liberté");
		assertThat(response.adresse().cite()).isEqualTo("Cité Ennasr");
		assertThat(response.adresse().ville()).isEqualTo("Tunis");
	}

	@Test
	void menu_isLinkedToItsRestaurant() {
		Restaurant restaurant = restaurantMapper.toEntity(new RestaurantRequest("Chez Salah",
				new AdresseRequest("1", "Rue A", "Cité B", "Tunis")));
		restaurant.setId(7L);

		Menu menu = menuMapper.toEntity(new MenuRequest("Couscous", new BigDecimal("18.50")), restaurant);
		MenuResponse response = menuMapper.toResponse(menu);

		assertThat(response.nom()).isEqualTo("Couscous");
		assertThat(response.prix()).isEqualByComparingTo("18.50");
		assertThat(response.restaurantId()).isEqualTo(7L);
	}
}
