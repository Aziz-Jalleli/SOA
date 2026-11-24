package com.pi.deliveryservice.client;

import com.pi.deliveryservice.dto.CourtierDto;
import com.pi.deliveryservice.exception.BusinessException;
import com.pi.deliveryservice.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Calls CourtierService through Eureka (service name, not host:port).
 * Endpoints used (from CourtierController):
 *   GET   /courtiers/{cin}
 *   GET   /courtiers/disponibles
 *   PATCH /courtiers/{cin}/disponibilite?disponible=true|false
 */
@Component
public class CourtierClient {

    private final RestTemplate restTemplate;
    private final String baseUrl;
    private final boolean enabled;

    public CourtierClient(RestTemplate restTemplate,
                          @Value("${courtier.service-name:CourtierService}") String serviceName,
                          @Value("${courtier.integration-enabled:true}") boolean enabled) {
        this.restTemplate = restTemplate;
        this.baseUrl = "http://" + serviceName;
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public CourtierDto getCourtier(String cin) {
        try {
            CourtierDto dto = restTemplate.getForObject(baseUrl + "/courtiers/{cin}", CourtierDto.class, cin);
            if (dto == null) throw new ResourceNotFoundException("Courier not found: " + cin);
            return dto;
        } catch (HttpClientErrorException.NotFound e) {
            throw new ResourceNotFoundException("Courier not found: " + cin);
        } catch (RestClientException e) {
            throw new BusinessException("CourtierService unavailable: " + e.getMessage());
        }
    }

    public List<CourtierDto> getAvailableCourtiers() {
        try {
            List<CourtierDto> list = restTemplate.exchange(
                    baseUrl + "/courtiers/disponibles", HttpMethod.GET, null,
                    new ParameterizedTypeReference<List<CourtierDto>>() {}).getBody();
            return list == null ? List.of() : list;
        } catch (RestClientException e) {
            throw new BusinessException("CourtierService unavailable: " + e.getMessage());
        }
    }

    public void setDisponible(String cin, boolean disponible) {
        try {
            restTemplate.exchange(baseUrl + "/courtiers/{cin}/disponibilite?disponible={d}",
                    HttpMethod.PATCH, null, Void.class, cin, disponible);
        } catch (RestClientException e) {
            throw new BusinessException("Could not update courier availability: " + e.getMessage());
        }
    }
}
