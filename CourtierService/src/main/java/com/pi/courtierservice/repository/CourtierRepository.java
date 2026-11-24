package com.pi.courtierservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pi.courtierservice.entity.Courtier;

@Repository
public interface CourtierRepository extends JpaRepository<Courtier, String> {

	List<Courtier> findByDisponibleTrue();
}
