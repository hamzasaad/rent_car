package com.itgate.demo.Controlleur;


import com.itgate.demo.dao.ServiceRepository;
import com.itgate.demo.models.Service;
import com.itgate.demo.utils.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.nio.file.*;
import java.util.*;


@RestController
@CrossOrigin("*")
@RequestMapping("/users/service")
public class ServiceController {
    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    public StorageService storage;

    private final Path rootLocation = Paths.get("upload-dir");

    @GetMapping("/get")
    public List<Service> getAllService(){return serviceRepository.findAll();}

    @PostMapping("/add")
    public Service addService(@RequestParam("file") MultipartFile file, Service service){

        try {
            int i = (int) new Date().getTime();
            System.out.println("Integer : " + i);
            String ch=Integer.toString(i);
            String fileName = Integer.toString(new Random().nextInt(1000000000));
            String ext=file.getOriginalFilename().substring(file.getOriginalFilename().indexOf('.'), file.getOriginalFilename().length());
            String name=file.getOriginalFilename().substring(0,file.getOriginalFilename().indexOf('.'));
            String original=name+fileName+ext;
            Files.copy(file.getInputStream(), this.rootLocation.resolve(original));
            service.setImage(original);

        } catch (Exception e) {
            throw new RuntimeException("FAIL!");
        }

        return serviceRepository.save(service);
    }

    @PutMapping("/update")
    public Service updateMarque(@RequestParam("file") MultipartFile file, Service service, @PathVariable Long id){
        Service serviceExistant = serviceRepository.findOne(id);
        serviceExistant.setTitle(service.getTitle());
        serviceExistant.setDescription(service.getDescription());
        try {
            int i = (int) new Date().getTime();
            System.out.println("Integer : " + i);
            String fileName = Integer.toString(new Random().nextInt(1000000000));
            String ext=file.getOriginalFilename().substring(file.getOriginalFilename().indexOf('.'), file.getOriginalFilename().length());
            String name=file.getOriginalFilename().substring(0,file.getOriginalFilename().indexOf('.'));
            String original=name+fileName+ext;
            Files.copy(file.getInputStream(), this.rootLocation.resolve(original));
            serviceExistant.setImage(original);

        } catch (Exception e) {
            throw new RuntimeException("FAIL!");
        }
        return serviceRepository.saveAndFlush(serviceExistant);
    }

    @DeleteMapping("/delete/{id}")
    public HashMap<String,String> deleteService(@PathVariable Long id){
        HashMap message = new HashMap();
        try{
            serviceRepository.delete(id);
            message.put("status","Service deleted !");
            return message;
        }catch(Exception e){
            message.put("status","Service not found !");
            return message;
        }
    }

    @GetMapping ("/getone/{id}")
    public Service getService(@PathVariable Long id){
        return serviceRepository.findOne(id);
    }
}
