package fi.uutisjuttu.uutisjuttu.repository;

import fi.uutisjuttu.uutisjuttu.domain.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Long> {
    public News findByUrl(String url);
}
