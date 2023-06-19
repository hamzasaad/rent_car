package com.itgate.demo.Controlleur;

import com.itgate.demo.dao.ClientRepository;
import com.itgate.demo.dao.IAuthority;
import com.itgate.demo.models.Authority;
import com.itgate.demo.models.Client;
import com.itgate.demo.models.Role;
import com.itgate.demo.models.UserTokenState;
import com.itgate.demo.utils.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
@RequestMapping("/users/client")
public class ClientController {
    @Autowired
    ClientRepository clientRepository;

    @Autowired
    public IAuthority iAuthority;

    @Autowired
    public StorageService storage;

    private final Path rootLocation = Paths.get("upload-dir");

    @PutMapping("/accept/{id}")
    public Client acceptClient(@PathVariable Long id){
        Client ExistantClient = clientRepository.findOne(id);
        ExistantClient.setEnabled(true);
        ExistantClient.setStat("Accepted");
        return clientRepository.saveAndFlush(ExistantClient) ;
    }

    @RequestMapping("/register")
    public ResponseEntity<?> register(Client user, @RequestParam("file") MultipartFile file )
    {
        Authority authority=new Authority();
        authority.setName("CLIENT");
        iAuthority.save(authority);
        user.setRole(Role.CLIENT);
        user.setStat("en attente");
        user.setEnabled(false);
        user.setAuthorities(authority);
        user.setPassword(hash(user.getPassword()));

        try {

            String fileName = Integer.toString(new Random().nextInt(1000000000));
            String ext=file.getOriginalFilename().substring(file.getOriginalFilename().indexOf('.'), file.getOriginalFilename().length());
            String name=file.getOriginalFilename().substring(0,file.getOriginalFilename().indexOf('.'));
            String original=name+fileName+ext;
            Files.copy(file.getInputStream(), this.rootLocation.resolve(original));
            user.setImage(original);

        } catch (Exception e) {
            throw new RuntimeException("FAIL!");
        }

        clientRepository.save(user);

        return ResponseEntity.ok(new UserTokenState(null, 0, user));

    }

    public  String hash(String password) {
        String hashedPassword = null;
        int i = 0;
        while (i < 5) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            hashedPassword = passwordEncoder.encode(password);
            i++;
        }

        return hashedPassword;
    }

    @GetMapping("/get")
    public List<Client> getClient(){return clientRepository.findAll();}

    @PostMapping("/post")
    public Client postClient(@RequestBody Client c){return clientRepository.save(c);}

    public Client updateClient(@RequestBody Client c,@PathVariable Long id){
        c.setId(id);
        return clientRepository.saveAndFlush(c);
    }
    @DeleteMapping("/delete/{id}")
    public HashMap<String,String> deleteClient(@PathVariable Long id){
        HashMap message = new HashMap();
        try{
            clientRepository.delete(id);
            message.put("status","product deleted !");
            return message;
        }catch(Exception e){
            message.put("status","product not found !");
            return message;
        }
    }
    @GetMapping ("/getone/{id}")
    public Client getMember(@PathVariable Long id){
        return clientRepository.findOne(id);
    }

    @PutMapping("/image/{id}")
    public Client imageClient (@RequestParam("file") MultipartFile file, Client client,@PathVariable Long id ){
            Client ExistantClient = clientRepository.findOne(id);
        try {

            String fileName = Integer.toString(new Random().nextInt(1000000000));
            String ext=file.getOriginalFilename().substring(file.getOriginalFilename().indexOf('.'), file.getOriginalFilename().length());
            String name=file.getOriginalFilename().substring(0,file.getOriginalFilename().indexOf('.'));
            String original=name+fileName+ext;
            Files.copy(file.getInputStream(), this.rootLocation.resolve(original));
            ExistantClient.setImage(original);

        } catch (Exception e) {
            throw new RuntimeException("FAIL!");
        }
        return clientRepository.save(ExistantClient);
    }
    @PutMapping("/update/{id}")
    public Client updatelient(@RequestBody Client client,@PathVariable Long id){
        Client ClientExistant = clientRepository.findOne(id);
        ClientExistant.setFirstName(client.getFirstName());
        ClientExistant.setLastName(client.getLastName());
        ClientExistant.setUsername(client.getUsername());
        ClientExistant.setEmail(client.getEmail());
        ClientExistant.setPassword(client.getPassword());
        ClientExistant.setAdress(client.getAdress());
        ClientExistant.setCin_client(client.getCin_client());
        return clientRepository.saveAndFlush(ClientExistant);
    }

    @PutMapping("/password/{id}")
    public Boolean updatepasssword(@RequestBody Client client,@PathVariable Long id){
        Client ClientExistant = clientRepository.findOne(id);
        String newPassword = client.getPassword();
        String lastPassword = ClientExistant.getPassword();
        BCryptPasswordEncoder passwordCheck = new BCryptPasswordEncoder();
        Boolean check = passwordCheck.matches(newPassword,lastPassword);
        return check;
    }

    @GetMapping("/password")
    public boolean Verify(@Param("password") String password){


        return true;
    }
}
