package com.itgate.demo.Controlleur;

import com.itgate.demo.dao.CategoryproductRepository;
import com.itgate.demo.models.Categoryproduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/users/categoryproduct")
public class CategoryproductController {
    @Autowired
    CategoryproductRepository categoryproductRepository;
    @GetMapping("/get")
    public List<Categoryproduct> getAll(){
        return categoryproductRepository.findAll();
    }

    @PostMapping("/save")
    public Categoryproduct saveCategory(@RequestBody Categoryproduct categoryproduct){
        return categoryproductRepository.save(categoryproduct);
    }

    @PutMapping("/update/{id}")
    public Categoryproduct updateCategory(@RequestBody Categoryproduct categoryproduct,@PathVariable Long id){
        categoryproduct.setId(id);
        return categoryproductRepository.saveAndFlush(categoryproduct);
    }

    @DeleteMapping("/delete/{id}")
    public HashMap<String,String> deleteCategory(@PathVariable Long id){
        HashMap message = new HashMap();
        try{
            categoryproductRepository.delete(id);
            message.put("status","category deleted !");
            return message;
        }catch(Exception e){
            message.put("status","category not found !");
            return message;
        }
    }

    @GetMapping ("/getone/{id}")
    public Categoryproduct getMember(@PathVariable Long id){
        return categoryproductRepository.findOne(id);
    }
}
