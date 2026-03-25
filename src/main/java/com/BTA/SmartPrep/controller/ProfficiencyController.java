package com.BTA.SmartPrep.controller;


import com.BTA.SmartPrep.mapper.ProfficiencyMapper;
import com.BTA.SmartPrep.mapper.UserMapper;
import com.BTA.SmartPrep.repository.ProficiencyRepository;
import com.BTA.SmartPrep.repository.UserRepository;
import com.BTA.SmartPrep.service.ProfficiencyService;
import com.BTA.SmartPrep.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000")
@RestController //Tells spring look at this for API
@RequestMapping(path = "/api/v1/proficiencies")
public class ProfficiencyController {
    private final ProfficiencyService profficiencyService;
    private final ProfficiencyMapper profficiencyMapper;

    public ProfficiencyController(ProfficiencyService profficiencyService, ProfficiencyMapper profficiencyMapper){
        this.profficiencyService = profficiencyService;
        this.profficiencyMapper = profficiencyMapper;
    }

    @Autowired
    private ProficiencyRepository proficiencyRepository;
    @GetMapping("/add")
    public String addProfficiency() {
        proficiencyRepository.insertProficiency("0bc7d8a5-246f-11f1-977b-02a6110df1cb",1,10);
        return "User saved";
    }
}
