package br.com.brunobrolesi.parking.repositories;

import br.com.brunobrolesi.parking.domain.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends JpaRepository <Vehicle, Integer> {

}