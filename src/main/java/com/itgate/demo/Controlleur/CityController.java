package com.itgate.demo.Controlleur;

import com.itgate.demo.dao.CityRepository;
import com.itgate.demo.models.Category;
import com.itgate.demo.models.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/users/city")
public class CityController {
    @Autowired
    public CityRepository cityRepository;
    @GetMapping("/get")
    public List<City> getAllCategory(){return cityRepository.findAll();}

    @PostMapping("/save")
    public List<City> saveCategory(@RequestBody List<City> cities){return cityRepository.save(cities);}
}
