package com.itgate.demo.Controlleur;

import com.itgate.demo.dao.ContratRepository;
import com.itgate.demo.dao.DemandeRepository;
import com.itgate.demo.dao.ResultRepository;
import com.itgate.demo.models.Contrat;
import com.itgate.demo.models.Demande;
import com.itgate.demo.models.ResultContrat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

@RestController
@CrossOrigin("*")
@RequestMapping("/users/contrat")
public class ContratController {
    @Autowired
    ContratRepository contratRepository;

    @Autowired
    ResultRepository resultRepository;
    @Autowired
    DemandeRepository demandeRepository;

    private final Path rootLocation = Paths.get("upload-dir");

    @GetMapping("/getr")
    public List<ResultContrat> getAllContractR() {
        return resultRepository.findAll();
    }

    @PostMapping("/saver/{id_demande}")
    public ResultContrat saveContractR(@RequestParam("file") MultipartFile file, ResultContrat c, @PathVariable Long id_demande) {
        Demande demande = demandeRepository.findOne(id_demande);
        c.setDemande(demande);
        try {
            String fileName = Integer.toString(new Random().nextInt(1000000000));
            String ext = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf('.'), file.getOriginalFilename().length());
            String name = file.getOriginalFilename().substring(0, file.getOriginalFilename().indexOf('.'));
            String original = name + fileName + ext;
            Files.copy(file.getInputStream(), this.rootLocation.resolve(original));
            c.setPdf(original);

        } catch (Exception e) {
            throw new RuntimeException("FAIL!");
        }

        return resultRepository.save(c);
    }

    @GetMapping("/get")
    public List<Contrat> getAllContract() {
        return contratRepository.findAll();
    }

    @PostMapping("/save")
    public Contrat saveContract(@RequestBody Contrat c) {
        return contratRepository.save(c);
    }

    @DeleteMapping("/delete/{id}")
    public HashMap<String, String> deleteContrat(@PathVariable Long id) {
        HashMap message = new HashMap();
        try {
            contratRepository.delete(id);
            message.put("status", "contract deleted !");
            return message;
        } catch (Exception e) {
            message.put("status", "contract not found !");
            return message;
        }
    }
    @DeleteMapping("/deleter/{id}")
    public HashMap<String, String> deleteContratr(@PathVariable Long id) {
        HashMap message = new HashMap();
        try {
            resultRepository.delete(id);
            message.put("status", "contract deleted !");
            return message;
        } catch (Exception e) {
            message.put("status", "contract not found !");
            return message;
        }
    }
}
