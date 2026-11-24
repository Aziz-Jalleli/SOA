package com.pi.courtierservice.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pi.courtierservice.dto.CourtierRequest;
import com.pi.courtierservice.dto.CourtierResponse;
import com.pi.courtierservice.dto.CourtierUpdateRequest;
import com.pi.courtierservice.dto.LocationRequest;
import com.pi.courtierservice.entity.Courtier;
import com.pi.courtierservice.exception.DuplicateResourceException;
import com.pi.courtierservice.exception.ResourceNotFoundException;
import com.pi.courtierservice.mapper.CourtierMapper;
import com.pi.courtierservice.repository.CourtierRepository;
import com.pi.courtierservice.service.CourtierService;

@Service
@Transactional(readOnly = true)
public class CourtierServiceImpl implements CourtierService {

	private final CourtierRepository courtierRepository;
	private final CourtierMapper courtierMapper;

	public CourtierServiceImpl(CourtierRepository courtierRepository, CourtierMapper courtierMapper) {
		this.courtierRepository = courtierRepository;
		this.courtierMapper = courtierMapper;
	}

	@Override
	@Transactional
	public CourtierResponse create(CourtierRequest request) {
		String cin = request.cin().trim();
		if (courtierRepository.existsById(cin)) {
			throw new DuplicateResourceException("Un courtier avec le CIN " + cin + " existe déjà");
		}
		Courtier saved = courtierRepository.save(courtierMapper.toEntity(request));
		return courtierMapper.toResponse(saved);
	}

	@Override
	public List<CourtierResponse> findAll() {
		return courtierRepository.findAll().stream().map(courtierMapper::toResponse).toList();
	}

	@Override
	public List<CourtierResponse> findAvailable() {
		return courtierRepository.findByDisponibleTrue().stream().map(courtierMapper::toResponse).toList();
	}

	@Override
	public CourtierResponse findByCin(String cin) {
		return courtierMapper.toResponse(getOrThrow(cin));
	}

	@Override
	@Transactional
	public CourtierResponse update(String cin, CourtierUpdateRequest request) {
		Courtier courtier = getOrThrow(cin);
		courtierMapper.updateEntity(courtier, request);
		return courtierMapper.toResponse(courtierRepository.save(courtier));
	}

	@Override
	@Transactional
	public CourtierResponse updateAvailability(String cin, boolean disponible) {
		Courtier courtier = getOrThrow(cin);
		courtier.setDisponible(disponible);
		return courtierMapper.toResponse(courtierRepository.save(courtier));
	}

	@Override
	@Transactional
	public CourtierResponse updateLocation(String cin, LocationRequest request) {
		Courtier courtier = getOrThrow(cin);
		courtierMapper.applyLocation(courtier, request);
		return courtierMapper.toResponse(courtierRepository.save(courtier));
	}

	@Override
	@Transactional
	public void delete(String cin) {
		courtierRepository.delete(getOrThrow(cin));
	}

	private Courtier getOrThrow(String cin) {
		return courtierRepository.findById(cin)
				.orElseThrow(() -> new ResourceNotFoundException("Courtier introuvable avec le CIN " + cin));
	}
}
