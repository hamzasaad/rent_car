package com.itgate.demo.Controlleur;

import com.itgate.demo.dao.CategoryRepository;
import com.itgate.demo.dao.MarqueRepository;
import com.itgate.demo.dao.VoitureRepository;
import com.itgate.demo.models.Category;
import com.itgate.demo.models.Marque;
import com.itgate.demo.models.Voiture;
import com.itgate.demo.utils.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

@RestController
@CrossOrigin("*")
@RequestMapping("/users/voiture")
public class VoitureController {

    @Autowired
    private VoitureRepository voitureRepository;

    @Autowired
    public StorageService storage;

    @Autowired
    public CategoryRepository categoryRepository;

    @Autowired
    public MarqueRepository marqueRepository;

    private final Path rootLocation = Paths.get("upload-dir");

    @GetMapping("/get")
    public List<Voiture> getAllVoiture(){return voitureRepository.findAll();}

    @PostMapping("/save")
    public Voiture saveVoiture(@RequestBody Voiture v){return voitureRepository.save(v);}

    //Inserer image
    @PostMapping("/image")
    public Voiture imageVoiture (@RequestParam("file") MultipartFile file, Voiture voiture ){
        try {
            int i = (int) new Date().getTime();
            System.out.println("Integer : " + i);

            String ch=Integer.toString(i);
            String fileName = Integer.toString(new Random().nextInt(1000000000));
            String ext=file.getOriginalFilename().substring(file.getOriginalFilename().indexOf('.'), file.getOriginalFilename().length());
            String name=file.getOriginalFilename().substring(0,file.getOriginalFilename().indexOf('.'));
            String original=name+fileName+ext;
            Files.copy(file.getInputStream(), this.rootLocation.resolve(original));
            voiture.setImg(original);

        } catch (Exception e) {
            throw new RuntimeException("FAIL!");
        }
        return voitureRepository.save(voiture);
    }

    @PutMapping("/update/{id}/{id_category}/{id_marque}")
    public Voiture updateVoiture(@RequestBody Voiture v,@PathVariable Long id,@PathVariable Long id_category,@PathVariable Long id_marque){
        Category category = categoryRepository.findOne(id_category);
        v.setCategory(category);

        Marque marque = marqueRepository.findOne(id_marque);
        v.setMarque(marque);
        v.setId(id);
        return voitureRepository.saveAndFlush(v);
    }
    @PutMapping("/update1/{id}/")
    public Voiture updateVoiture2(@RequestParam("file") MultipartFile file, Voiture v,@PathVariable Long id){
        Voiture voitureExistant =voitureRepository.findOne(id);
        storage.store(file);
        voitureExistant.setImg(file.getOriginalFilename());

        return voitureRepository.saveAndFlush(voitureExistant);
    }

    @PutMapping("/update2/{id}/{id_category}/{id_marque}")
    public Voiture updateVoiture1(@RequestParam("file") MultipartFile file, Voiture v,@PathVariable Long id,@PathVariable Long id_category,@PathVariable Long id_marque){
        Voiture voitureExistant =voitureRepository.findOne(id);
        voitureExistant.setSerie(v.getSerie());
        voitureExistant.setCarburant(v.getCarburant());
        voitureExistant.setCouleur(v.getCouleur());
        voitureExistant.setDescription(v.getDescription());
        voitureExistant.setModel(v.getModel());
        voitureExistant.setName(v.getName());
        voitureExistant.setTransmition(v.getTransmition());
        voitureExistant.setKlm(v.getKlm());
        voitureExistant.setNbr_place(v.getNbr_place());
        voitureExistant.setPrix(v.getPrix());

        try {
            String fileName = Integer.toString(new Random().nextInt(1000000000));
            String ext=file.getOriginalFilename().substring(file.getOriginalFilename().indexOf('.'), file.getOriginalFilename().length());
            String name=file.getOriginalFilename().substring(0,file.getOriginalFilename().indexOf('.'));
            String original=name+fileName+ext;
            Files.copy(file.getInputStream(), this.rootLocation.resolve(original));
            voitureExistant.setImg(original);

        } catch (Exception e) {
            throw new RuntimeException("FAIL!");
        }

        Category category = categoryRepository.findOne(id_category);
        voitureExistant.setCategory(category);

        Marque marque = marqueRepository.findOne(id_marque);
        voitureExistant.setMarque(marque);
        return voitureRepository.saveAndFlush(voitureExistant);
    }


    @DeleteMapping("/delete/{id}")
    public HashMap<String,String> deleteVoiture(@PathVariable Long id){
        HashMap message = new HashMap();
        try{
            voitureRepository.delete(id);
            message.put("status","voiture deleted !");
            return message;
        }catch(Exception e){
            message.put("status","voiture not found !");
            return message;
        }
    }

    @PostMapping("/add/{id_category}/{id_marque}")
    public Voiture addProduct(@RequestParam("file") MultipartFile file,Voiture voiture,@PathVariable Long id_category,@PathVariable Long id_marque ){
        Category category = categoryRepository.findOne(id_category);
        voiture.setCategory(category);

        Marque marque = marqueRepository.findOne(id_marque);
        voiture.setMarque(marque);

        try {
            String fileName = Integer.toString(new Random().nextInt(1000000000));
            String ext=file.getOriginalFilename().substring(file.getOriginalFilename().indexOf('.'), file.getOriginalFilename().length());
            String name=file.getOriginalFilename().substring(0,file.getOriginalFilename().indexOf('.'));
            String original=name+fileName+ext;
            Files.copy(file.getInputStream(), this.rootLocation.resolve(original));
            voiture.setImg(original);

        } catch (Exception e) {
            throw new RuntimeException("FAIL!");
        }

        return voitureRepository.save(voiture);
    }

    //get one voiture
    @GetMapping ("/getone/{id}")
    public Voiture getVoiture(@PathVariable Long id){
        return voitureRepository.findOne(id);
    }

}
