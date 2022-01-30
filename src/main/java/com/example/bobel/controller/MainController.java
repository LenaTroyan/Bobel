package com.example.bobel.controller;
import com.example.bobel.entity.Pet;
import com.example.bobel.entity.User;
import com.example.bobel.repository.PetRepository;
import com.example.bobel.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;


@Controller

public class MainController {
    @GetMapping("/")
    public String greeting(Map<String, Object> model) {
        return "greeting";
    }

    @Autowired
    private PetRepository petRepository;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("main")
    public String main ( @RequestParam(required=false, defaultValue="") String filter, Model model){
        Iterable<Pet> pets = petRepository.findAll();
        if (filter != null && !filter.isEmpty()) {
            pets = petRepository.findByBreed(filter);
        } else {
            pets = petRepository.findAll();
        }
        model.addAttribute("pets",pets);
        model.addAttribute("filter",filter);
        return "main";
    }

   @PostMapping("main")
    public String add(
            @RequestParam("file") MultipartFile file,
            @AuthenticationPrincipal User user,
            @RequestParam String petName,
            @RequestParam String breed, Map<String,Object> model
   ) throws IOException {
        Pet pet = new Pet(petName, breed,user);

       if (file != null && !file.getOriginalFilename().isEmpty()) {
           File uploadDir = new File(uploadPath);

           if (!uploadDir.exists()) {
               uploadDir.mkdir();
           }

           String uuidFile = UUID.randomUUID().toString();
           String resultFilename = uuidFile + "." + file.getOriginalFilename();

           file.transferTo(new File(uploadPath + "/" + resultFilename));

           pet.setFilename(resultFilename);
       }

        petRepository.save(pet);
        Iterable<Pet> pets= petRepository.findAll();
        model.put("pets",pets);
        return "main";
    }
}