/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.uutisjuttu.uutisjuttu.repository;

import fi.uutisjuttu.uutisjuttu.domain.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author mikko
 */
public interface PublisherRepository extends JpaRepository<Publisher, Long> {
    Publisher findByName(String name);
}
