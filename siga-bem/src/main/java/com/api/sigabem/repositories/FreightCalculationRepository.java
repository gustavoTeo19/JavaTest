package com.api.sigabem.repositories;

import org.springframework.stereotype.Repository;

import com.api.sigabem.models.FreightCalculationModel;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface FreightCalculationRepository extends JpaRepository<FreightCalculationModel, UUID> {

}
