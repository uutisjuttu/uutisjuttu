package fi.uutisjuttu.uutisjuttu.repository;

import fi.uutisjuttu.uutisjuttu.domain.Uutinen;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UutinenRepository extends JpaRepository<Uutinen, Long> {
    
}
