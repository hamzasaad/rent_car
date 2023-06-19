package com.itgate.demo.Controlleur;


import com.itgate.demo.dao.BlogRepository;
import com.itgate.demo.models.Blog;
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
@RequestMapping("/users/blog")
public class BlogController {
    @Autowired
    BlogRepository blogRepository;

    private final Path rootLocation = Paths.get("upload-dir");

    @GetMapping("/get")
    public List<Blog> getAll(){
        return blogRepository.findAll();
    }

    @PostMapping("/save")
    public Blog saveCategory(@RequestBody Blog blog){
        return blogRepository.save(blog);
    }
    @PutMapping("/update/{id}")
    public Blog updateBlog(@RequestParam("file") MultipartFile file, Blog blog, @PathVariable Long id){
        Blog BlogExistant =blogRepository.findOne(id);
        BlogExistant.setTitle(blog.getTitle());
        BlogExistant.setDescription(blog.getDescription());
        try {
            int i = (int) new Date().getTime();
            System.out.println("Integer : " + i);
            String fileName = Integer.toString(new Random().nextInt(1000000000));
            String ext=file.getOriginalFilename().substring(file.getOriginalFilename().indexOf('.'), file.getOriginalFilename().length());
            String name=file.getOriginalFilename().substring(0,file.getOriginalFilename().indexOf('.'));
            String original=name+fileName+ext;
            Files.copy(file.getInputStream(), this.rootLocation.resolve(original));
            BlogExistant.setImage(original);

        } catch (Exception e) {
            throw new RuntimeException("FAIL!");
        }
        return blogRepository.saveAndFlush(BlogExistant);
    }

    @PostMapping("/add")
    public Blog addBlog(@RequestParam("file") MultipartFile file,Blog blog ){

        try {
            int i = (int) new Date().getTime();
            System.out.println("Integer : " + i);
            String ch=Integer.toString(i);
            String fileName = Integer.toString(new Random().nextInt(1000000000));
            String ext=file.getOriginalFilename().substring(file.getOriginalFilename().indexOf('.'), file.getOriginalFilename().length());
            String name=file.getOriginalFilename().substring(0,file.getOriginalFilename().indexOf('.'));
            String original=name+fileName+ext;
            Files.copy(file.getInputStream(), this.rootLocation.resolve(original));
            blog.setImage(original);

        } catch (Exception e) {
            throw new RuntimeException("FAIL!");
        }

        return blogRepository.save(blog);
    }

    @DeleteMapping("/delete/{id}")
    public HashMap<String,String> deleteBlog(@PathVariable Long id){
        HashMap message = new HashMap();
        try{
            blogRepository.delete(id);
            message.put("status","blog deleted !");
            return message;
        }catch(Exception e){
            message.put("status","blog not found !");
            return message;
        }
    }

    @GetMapping ("/getone/{id}")
    public Blog getBlog(@PathVariable Long id){
        return blogRepository.findOne(id);
    }
}
