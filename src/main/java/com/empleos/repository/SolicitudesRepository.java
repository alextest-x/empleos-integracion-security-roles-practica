package com.empleos.repository;

import com.empleos.model.Solicitud;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface SolicitudesRepository extends JpaRepository<Solicitud, Integer> {


}
