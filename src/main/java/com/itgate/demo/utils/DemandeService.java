package com.itgate.demo.utils;

import com.itgate.demo.dao.ClientRepository;
import com.itgate.demo.dao.DemandeRepository;
import com.itgate.demo.dao.VoitureRepository;
import com.itgate.demo.models.Client;
import com.itgate.demo.models.Demande;
import com.itgate.demo.models.Voiture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class DemandeService {

    @Autowired
    public DemandeRepository demandeRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    VoitureRepository voitureRepository;

    public Demande saveDemandeService(Demande d,  Long id_client, Long id_voiture) {
        Client client = clientRepository.findOne(id_client);
        d.setClient(client);
        Voiture voiture = voitureRepository.findOne(id_voiture);
        d.setVoiture(voiture);

        return demandeRepository.save(d);
    }
}
