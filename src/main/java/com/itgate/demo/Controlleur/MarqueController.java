package com.itgate.demo.Controlleur;

import com.itgate.demo.dao.MarqueRepository;
import com.itgate.demo.models.Marque;
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
@RequestMapping("/users/marque")
public class MarqueController {

    @Autowired
    private MarqueRepository marqueRepository;

    @Autowired
    public StorageService storage;

    private final Path rootLocation = Paths.get("upload-dir");

    @GetMapping("/get")
    public List<Marque> getAllMarque(){return marqueRepository.findAll();}

    @PostMapping("/save")
    public Marque saveMarque(@RequestBody Marque marque){return marqueRepository.save(marque);}

    @PutMapping("/update/{id}")
    public Marque updateMarque(@RequestParam("file") MultipartFile file, Marque m, @PathVariable Long id){
        Marque marqueExistant = marqueRepository.findOne(id);
        marqueExistant.setTitle(m.getTitle());
        try {
            int i = (int) new Date().getTime();
            System.out.println("Integer : " + i);

            String fileName = Integer.toString(new Random().nextInt(1000000000));
            String ext=file.getOriginalFilename().substring(file.getOriginalFilename().indexOf('.'), file.getOriginalFilename().length());
            String name=file.getOriginalFilename().substring(0,file.getOriginalFilename().indexOf('.'));
            String original=name+fileName+ext;
            Files.copy(file.getInputStream(), this.rootLocation.resolve(original));
            marqueExistant.setImage(original);

        } catch (Exception e) {
            throw new RuntimeException("FAIL!");
        }
        return marqueRepository.saveAndFlush(marqueExistant);
    }
    @PostMapping("/add")
    public Marque addMarque(@RequestParam("file") MultipartFile file,Marque marque , @PathVariable Long id){

        try {

            int i = (int) new Date().getTime();
            System.out.println("Integer : " + i);
            String ch=Integer.toString(i);
            String fileName = Integer.toString(new Random().nextInt(1000000000));
            String ext=file.getOriginalFilename().substring(file.getOriginalFilename().indexOf('.'), file.getOriginalFilename().length());
            String name=file.getOriginalFilename().substring(0,file.getOriginalFilename().indexOf('.'));
            String original=name+fileName+ext;
            Files.copy(file.getInputStream(), this.rootLocation.resolve(original));
            marque.setImage(original);

        } catch (Exception e) {
            throw new RuntimeException("FAIL!");
        }

        return marqueRepository.save(marque);
    }
    @DeleteMapping("/delete/{id}")
    public HashMap<String,String> deleteMarque(@PathVariable Long id){
        HashMap message = new HashMap();
        try{
            marqueRepository.delete(id);
            message.put("status","Marque deleted !");
            return message;
        }catch(Exception e){
            message.put("status","Marque not found !");
            return message;
        }
    }

    @GetMapping ("/getone/{id}")
    public Marque getMember(@PathVariable Long id){
        return marqueRepository.findOne(id);
    }
}

