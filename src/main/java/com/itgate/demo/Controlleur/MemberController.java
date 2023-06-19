package com.itgate.demo.Controlleur;

import com.itgate.demo.dao.MemberRepository;
import com.itgate.demo.models.Member;
import com.itgate.demo.utils.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
@RequestMapping("/users/member")
public class MemberController {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private StorageService storage;

    private final Path rootLocation = Paths.get("upload-dir");

    @GetMapping("/get")
    public List<Member> getMember(){return memberRepository.findAll();}

    @PostMapping("/post")
    public Member postMember(@RequestBody Member m){return memberRepository.save(m);}

    @PostMapping("/save")
    public Member imageProduct (@RequestParam("file") MultipartFile file, Member member ){
        storage.store(file);
        member.setImg(file.getOriginalFilename());
        return memberRepository.save(member);
    }

    @PutMapping("/update/{id}")
    public Member updateMember(@RequestParam("file") MultipartFile file, Member m,@PathVariable Long id){
        Member memberExistant =memberRepository.findOne(id);
        memberExistant.setName(m.getName());
        memberExistant.setFb(m.getFb());
        memberExistant.setDescription(m.getDescription());
        memberExistant.setInstagramme(m.getInstagramme());
        memberExistant.setTwitter(m.getTwitter());
        memberExistant.setLinkedin(m.getLinkedin());
        try {
            int i = (int) new Date().getTime();
            System.out.println("Integer : " + i);

            String fileName = Integer.toString(new Random().nextInt(1000000000));
            String ext=file.getOriginalFilename().substring(file.getOriginalFilename().indexOf('.'), file.getOriginalFilename().length());
            String name=file.getOriginalFilename().substring(0,file.getOriginalFilename().indexOf('.'));
            String original=name+fileName+ext;
            Files.copy(file.getInputStream(), this.rootLocation.resolve(original));
            memberExistant.setImg(original);

        } catch (Exception e) {
            throw new RuntimeException("FAIL!");
        }
        return memberRepository.saveAndFlush(memberExistant);
    }
    @DeleteMapping("/delete/{id}")
    public HashMap<String,String> deleteMember(@PathVariable Long id){
        HashMap message = new HashMap();
        try{
            memberRepository.delete(id);
            message.put("status","member deleted !");
            return message;
        }catch(Exception e){
            message.put("status","member not found !");
            return message;
        }
    }
    @GetMapping ("/getone/{id}")
    public Member getMember(@PathVariable Long id){
        return memberRepository.findOne(id);
    }
}
