package fi.uutisjuttu.uutisjuttu.repository;

import fi.uutisjuttu.uutisjuttu.domain.Kommentti;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KommenttiRepository extends JpaRepository<Kommentti, Long>{
    
}
