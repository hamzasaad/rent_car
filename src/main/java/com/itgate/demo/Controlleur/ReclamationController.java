package com.itgate.demo.Controlleur;

import com.itgate.demo.dao.ReclamationRepository;
import com.itgate.demo.models.Reclamation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/users/reclamation")
public class ReclamationController {

    @Autowired
    public ReclamationRepository reclamationRepository;

    @GetMapping("/get")
    public List<Reclamation> getReclamation(){return reclamationRepository.findAll();}

    @PostMapping("/post")
    public Reclamation postReclamation(@RequestBody Reclamation r){return reclamationRepository.save(r);}

    @PutMapping("/update")
    public Reclamation updateReclamation(@RequestBody Reclamation r,@PathVariable Long id){
        r.setId(id);
        return reclamationRepository.saveAndFlush(r);
    }
    @DeleteMapping("/delete/{id}")
    public HashMap<String,String> deleteReclamation(@PathVariable Long id){
        HashMap message = new HashMap();
        try{
            reclamationRepository.delete(id);
            message.put("status","reclamation deleted !");
            return message;
        }catch(Exception e){
            message.put("status","reclamation not found !");
            return message;
        }
    }
    @GetMapping ("/getone/{id}")
    public Reclamation getReclamtion(@PathVariable Long id){
        return reclamationRepository.findOne(id);
    }
}
