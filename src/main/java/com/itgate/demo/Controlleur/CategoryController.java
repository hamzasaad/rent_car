package com.itgate.demo.Controlleur;

import com.itgate.demo.dao.CategoryRepository;
import com.itgate.demo.models.Category;
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
@RequestMapping("/users/category")
public class CategoryController {

    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    public StorageService storage ;

    private final Path rootLocation = Paths.get("upload-dir");

    @GetMapping("/get")
    public List<Category> getAllCategory(){return categoryRepository.findAll();}

    @PostMapping("/save")
    public Category saveCategory(@RequestBody Category c){return categoryRepository.save(c);}

    @PutMapping("/update/{id}")
    public Category updateCategory(@RequestParam("file") MultipartFile file, Category c, @PathVariable Long id){
        Category CategoryExistant =categoryRepository.findOne(id);
        CategoryExistant.setTitle(c.getTitle());
        try {
            int i = (int) new Date().getTime();
            System.out.println("Integer : " + i);
            String fileName = Integer.toString(new Random().nextInt(1000000000));
            String ext=file.getOriginalFilename().substring(file.getOriginalFilename().indexOf('.'), file.getOriginalFilename().length());
            String name=file.getOriginalFilename().substring(0,file.getOriginalFilename().indexOf('.'));
            String original=name+fileName+ext;
            Files.copy(file.getInputStream(), this.rootLocation.resolve(original));
            CategoryExistant.setImage(original);

        } catch (Exception e) {
            throw new RuntimeException("FAIL!");
        }
        return categoryRepository.saveAndFlush(CategoryExistant);
    }

    @DeleteMapping("/delete/{id}")
    public HashMap<String,String> deleteCategory(@PathVariable Long id){
        HashMap message = new HashMap();
        try{
            categoryRepository.delete(id);
            message.put("status","category deleted !");
            return message;
        }catch(Exception e){
            message.put("status","category not found !");
            return message;
        }
    }

    @PostMapping("/add")
    public Category addcategory(@RequestParam("file") MultipartFile file,Category category ){


        try {
            int i = (int) new Date().getTime();
            System.out.println("Integer : " + i);
            String ch=Integer.toString(i);
            String fileName = Integer.toString(new Random().nextInt(1000000000));
            String ext=file.getOriginalFilename().substring(file.getOriginalFilename().indexOf('.'), file.getOriginalFilename().length());
            String name=file.getOriginalFilename().substring(0,file.getOriginalFilename().indexOf('.'));
            String original=name+fileName+ext;
            Files.copy(file.getInputStream(), this.rootLocation.resolve(original));
            category.setImage(original);

        } catch (Exception e) {
            throw new RuntimeException("FAIL!");
        }

        return categoryRepository.save(category);
    }


    @GetMapping ("/getone/{id}")
    public Category getMember(@PathVariable Long id){
        return categoryRepository.findOne(id);
    }
}
