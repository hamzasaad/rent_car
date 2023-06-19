package com.itgate.demo.Controlleur;

import com.itgate.demo.dao.EtatRepository;
import com.itgate.demo.dao.VoitureRepository;
import com.itgate.demo.models.Etat;
import com.itgate.demo.models.Voiture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@CrossOrigin("*")
@RequestMapping("/users/etat")

public class EtatController {
    @Autowired
    EtatRepository etatRepository;

    @Autowired
    VoitureRepository voitureRepository;


    @GetMapping("/get")
    public List<Etat> getPayement(){return etatRepository.findAll();}

    @GetMapping("/find")
    public List<Voiture> findAllByDate(String date){

        System.out.println("date in body " + date);
        SimpleDateFormat formatter5=new SimpleDateFormat("yyyy-MM-dd");
     //   List<Voiture> voitures=new ArrayList<>();
        List<Voiture> voitureList=voitureRepository.findAll();

        try {
            Date date5=formatter5.parse(date);
            System.out.println("voitureList before " + voitureList.size());

            List<Etat> etats=etatRepository.findAllByDate(date5);

            for (int i=0 ;i<etats.size();i++){

                voitureList.remove(etats.get(i).getVoiture());
            }

            System.out.println("voitureList after " + voitureList.size());



            return voitureList ;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return voitureList;

    }


    @PostMapping("/post")
    public Etat postPayement(@RequestBody Etat e){return etatRepository.save(e);}

    @PutMapping("/update")
    public Etat updatePayement(@RequestBody Etat e,@PathVariable Long id){
        e.setId(id);
        return etatRepository.saveAndFlush(e);
    }
    @DeleteMapping("/delete/{id}")
    public HashMap<String,String> deletePayement(@PathVariable Long id){
        HashMap message = new HashMap();
        try{
            etatRepository.delete(id);
            message.put("status","Etat deleted !");
            return message;
        }catch(Exception e){
            message.put("status","Etat not found !");
            return message;
        }
    }
}
