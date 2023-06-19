package com.itgate.demo.Controlleur;

import com.itgate.demo.dao.CategoryproductRepository;
import com.itgate.demo.dao.ProduitRepository;
import com.itgate.demo.models.Categoryproduct;
import com.itgate.demo.models.Produit;
import com.itgate.demo.utils.StorageService;
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
@RequestMapping("/users/produit")
public class ProduitController {
    @Autowired
    ProduitRepository produitRepository;

    @Autowired
    CategoryproductRepository categoryproductRepository;

    @Autowired
    public StorageService storage;

    private final Path rootLocation = Paths.get("upload-dir");

    @GetMapping("/get")
    public List<Produit> getAllVoiture(){return produitRepository.findAll();}

    @PostMapping("/save")
    public Produit saveVoiture(@RequestBody Produit produit){return produitRepository.save(produit);}

    @PutMapping("/update/{id}/{id_categoryproduct}")
    public Produit updateProduit(@RequestParam("file") MultipartFile file, Produit produit, @PathVariable Long id, @PathVariable Long id_categoryproduct){
        Produit produitExistant = produitRepository.findOne(id);
        produitExistant.setDescription(produit.getDescription());
        produitExistant.setName(produit.getName());
        produitExistant.setPrix(produit.getPrix());


        Categoryproduct category = categoryproductRepository.findOne(id_categoryproduct);
        produitExistant.setCategoryproduct(category);

        return produitRepository.saveAndFlush(produitExistant);
    }

    @PostMapping("/add/{id_categoryproduct}")
    public Produit addProduct(@RequestParam("file") MultipartFile file,Produit produit,@PathVariable Long id_categoryproduct ){
        Categoryproduct category = categoryproductRepository.findOne(id_categoryproduct);
        produit.setCategoryproduct(category);

        try {
            String fileName = Integer.toString(new Random().nextInt(1000000000));
            String ext=file.getOriginalFilename().substring(file.getOriginalFilename().indexOf('.'), file.getOriginalFilename().length());
            String name=file.getOriginalFilename().substring(0,file.getOriginalFilename().indexOf('.'));
            String original=name+fileName+ext;
            Files.copy(file.getInputStream(), this.rootLocation.resolve(original));
            produit.setImage(original);

        } catch (Exception e) {
            throw new RuntimeException("FAIL!");
        }

        return produitRepository.save(produit);
    }

    @GetMapping ("/getone/{id}")
    public Produit getVoiture(@PathVariable Long id){
        return produitRepository.findOne(id);
    }

    @DeleteMapping("/delete/{id}")
    public HashMap<String,String> deleteVoiture(@PathVariable Long id){
        HashMap message = new HashMap();
        try{
            produitRepository.delete(id);
            message.put("status","produit deleted !");
            return message;
        }catch(Exception e){
            message.put("status","produit not found !");
            return message;
        }
    }
}
