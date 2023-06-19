package com.itgate.demo.Controlleur;

import com.itgate.demo.dao.BlogRepository;
import com.itgate.demo.dao.ClientRepository;
import com.itgate.demo.dao.ComentaireRepository;
import com.itgate.demo.models.Blog;
import com.itgate.demo.models.Client;
import com.itgate.demo.models.Comentaire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/users/commentaire")
public class ComentaireController {
    @Autowired
    ComentaireRepository comentaireRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    BlogRepository blogRepository;
    @GetMapping("/get")
    public List<Comentaire> getAll(){ return comentaireRepository.findAll();}

    @GetMapping("/getone/{id}")
    public Comentaire getCommentaire(@PathVariable long id) {
        return comentaireRepository.findOne(id);
    }

    @PostMapping("/save")
    public Comentaire save(@RequestBody Comentaire comentaire)
    {return comentaireRepository.save(comentaire);}

    @PostMapping("/add/{id_client}/{id_blog}")
    public Comentaire saveCommentaire(@RequestBody Comentaire comentaire,@PathVariable Long id_client,@PathVariable Long id_blog ) {
        Client client = clientRepository.findOne(id_client);
        comentaire.setClient(client);
        Blog blog = blogRepository.findOne(id_blog);
        comentaire.setBlog(blog);
        return comentaireRepository.save(comentaire);
    }

    @DeleteMapping("/delete/{id}")
    public HashMap<String,String> deleteCommentaire(@PathVariable Long id){
        HashMap message=new HashMap();
        try {

            comentaireRepository.delete(id);
            message.put("status","delete");
            return message;
        }catch (Exception e) {
            message.put("status", " not found");
            return message;
        }}

}
