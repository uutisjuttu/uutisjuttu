/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fi.uutisjuttu.uutisjuttu.service;

import fi.uutisjuttu.uutisjuttu.domain.Kayttaja;
import fi.uutisjuttu.uutisjuttu.repository.KayttajaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class KayttajaService {
    @Autowired
    private KayttajaRepository kayttajaRepository;

    public Kayttaja getAuthenticatedPerson() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return kayttajaRepository.findByTunnus(authentication.getName());
    }
}
