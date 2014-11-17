package fi.uutisjuttu.uutisjuttu.repository;

import fi.uutisjuttu.uutisjuttu.domain.Uutinen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path="uutiset")
public interface UutinenRepository extends JpaRepository<Uutinen, Long> {
    public Uutinen findByUrl(String url);
}
