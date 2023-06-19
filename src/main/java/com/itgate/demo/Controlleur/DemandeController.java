package com.itgate.demo.Controlleur;

import com.itgate.demo.dao.ClientRepository;
import com.itgate.demo.dao.DemandeRepository;
import com.itgate.demo.dao.EtatRepository;
import com.itgate.demo.dao.VoitureRepository;
import com.itgate.demo.models.Client;
import com.itgate.demo.models.Demande;
import com.itgate.demo.models.Etat;
import com.itgate.demo.models.Voiture;
import com.itgate.demo.utils.DemandeService;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

@RestController
@CrossOrigin("*")
@RequestMapping("/users/demande")
public class DemandeController {
    @Autowired
    DemandeRepository demandeRepository;
     @Autowired
    ClientRepository clientRepository;
     @Autowired
    EtatRepository etatRepository;
     @Autowired
    VoitureRepository voitureRepository;
     @Autowired
     public DemandeService demandeService;

    @GetMapping("/get")
    public List<Demande> getDemande(){return demandeRepository.findAll();}

    @PostMapping("/post")
    public Demande postDemande(@RequestBody Demande d){return demandeRepository.save(d);}

    @PostMapping("/save/{id_client}/{id_voiture}")
    public Demande saveDemande(@RequestBody Demande d,Etat etat ,@PathVariable Long id_client,@PathVariable Long id_voiture){
        Client client = clientRepository.findOne(id_client);
        d.setClient(client);
        etat.setDate_depart(d.getDate_depart());
        etat.setDate_retour(d.getDate_retour());
        etat.setHeur_depart(d.getHeur_depart());
        etat.setHeur_retour(d.getHeur_retour());
        Etat E =etatRepository.save(etat);
        Voiture voiture = voitureRepository.findOne(id_voiture);
        E.setVoiture(voiture);
        etatRepository.save(E);
        d.setVoiture(voiture);
        long nbrj =Math.abs(d.getDate_retour().getTime() - d.getDate_depart().getTime());
        float nbrjr = (nbrj/86400000);
        float prix =nbrjr*voiture.getPrix();
        d.setPrix_totale(prix);

        return demandeRepository.save(d);
    }
    @PutMapping("/update/id/{id_voiture}/{id_client}")
    public Demande updateDemande(@RequestBody Demande d,@PathVariable Long id,@PathVariable Long id_voiture,@PathVariable Long id_client){
        Demande demandeExistant =demandeRepository.findOne(id);
        demandeExistant.setDate_depart(d.getDate_depart());
        demandeExistant.setDate_retour(d.getDate_retour());
        demandeExistant.setHeur_depart(d.getHeur_depart());
        demandeExistant.setHeur_retour(d.getHeur_retour());

        Voiture voiture = voitureRepository.findOne(id_voiture);
        demandeExistant.setVoiture(voiture);

        Client client = clientRepository.findOne(id_client);
        demandeExistant.setClient(client);
        return demandeRepository.saveAndFlush(demandeExistant);
    }
    @DeleteMapping("/delete/{id}")
    public HashMap<String,String> deleteDemande(@PathVariable Long id){
        HashMap message = new HashMap();
        try{
            demandeRepository.delete(id);
            message.put("status","demande deleted !");
            return message;
        }catch(Exception e){
            message.put("status","demande not found !");
            return message;
        }
    }
    @GetMapping ("/getone/{id}")
    public Demande getMember(@PathVariable Long id){
        return demandeRepository.findOne(id);
    }
    @PostMapping("/accept/{id}")
    public Demande acceptDemande(@PathVariable Long id){
        Demande ExistantDemande = demandeRepository.findOne(id);
        ExistantDemande.setStatus("Accepted");
        return demandeRepository.saveAndFlush(ExistantDemande) ;
    }


}
