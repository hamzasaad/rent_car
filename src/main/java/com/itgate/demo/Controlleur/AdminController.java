package com.itgate.demo.Controlleur;

import com.itgate.demo.dao.AdminRepository;
import com.itgate.demo.dao.IAuthority;
import com.itgate.demo.models.Admin;
import com.itgate.demo.models.Authority;
import com.itgate.demo.models.Role;
import com.itgate.demo.models.UserTokenState;
import com.itgate.demo.utils.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

@RestController
@CrossOrigin("*")
@RequestMapping("users/admin")
public class AdminController {
    @Autowired
 public IAuthority iAuthority;
    @Autowired
    public AdminRepository adminRepository;

    @Autowired
    public StorageService storage;

    private final Path rootLocation = Paths.get("upload-dir");

    @RequestMapping("/register")
    public ResponseEntity<?> register(Admin admin, @RequestParam("file") MultipartFile file )
    {
        Authority authority=new Authority();
        authority.setName("ADMIN");
        iAuthority.save(authority);
        admin.setRole(Role.ADMIN);
        admin.setEnabled(true);
        admin.setAuthorities(authority);
        admin.setPassword(hash(admin.getPassword()));
        try {

            String fileName = Integer.toString(new Random().nextInt(1000000000));
            String ext=file.getOriginalFilename().substring(file.getOriginalFilename().indexOf('.'), file.getOriginalFilename().length());
            String name=file.getOriginalFilename().substring(0,file.getOriginalFilename().indexOf('.'));
            String original=name+fileName+ext;
            Files.copy(file.getInputStream(), this.rootLocation.resolve(original));
            admin.setImage(original);

        } catch (Exception e) {
            throw new RuntimeException("FAIL!");
        }

        adminRepository.save(admin);

        return ResponseEntity.ok(new UserTokenState(null, 0, admin));

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
    @PutMapping("/image/{id}")
    public Admin imageClient (@RequestParam("file") MultipartFile file, Admin admin,@PathVariable Long id ){
        Admin ExistantAdmin = adminRepository.findOne(id);
        try {

            String fileName = Integer.toString(new Random().nextInt(1000000000));
            String ext=file.getOriginalFilename().substring(file.getOriginalFilename().indexOf('.'), file.getOriginalFilename().length());
            String name=file.getOriginalFilename().substring(0,file.getOriginalFilename().indexOf('.'));
            String original=name+fileName+ext;
            Files.copy(file.getInputStream(), this.rootLocation.resolve(original));
            ExistantAdmin.setImage(original);

        } catch (Exception e) {
            throw new RuntimeException("FAIL!");
        }
        return adminRepository.save(ExistantAdmin);
    }
}
