package com.BTA.SmartPrep.controller;


import com.BTA.SmartPrep.domain.UpdateProfficiencyRequest;
import com.BTA.SmartPrep.domain.dto.ProfficiencyDto;
import com.BTA.SmartPrep.domain.dto.UpdateProfficiencyRequestDto;
import com.BTA.SmartPrep.domain.dto.UserDto;
import com.BTA.SmartPrep.domain.entity.Proficiency;
import com.BTA.SmartPrep.domain.entity.User;
import com.BTA.SmartPrep.mapper.ProfficiencyMapper;
import com.BTA.SmartPrep.mapper.UserMapper;
import com.BTA.SmartPrep.repository.ProficiencyRepository;
import com.BTA.SmartPrep.repository.UserRepository;
import com.BTA.SmartPrep.service.ProfficiencyService;
import com.BTA.SmartPrep.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

//    @Autowired
//    private ProficiencyRepository proficiencyRepository;
//    @GetMapping("/add")
//    public String addProfficiency() {
//        proficiencyRepository.insertProficiency("0bc7d8a5-246f-11f1-977b-02a6110df1cb",1,10);


    @GetMapping("/{userId}/{categoryId}")
    public ResponseEntity<ProfficiencyDto> getProficiency(
            @PathVariable String userId,
            @PathVariable int categoryId) {

        return ResponseEntity.ok(profficiencyService.getProfficiency(userId,categoryId));
    }

    @PutMapping(path = "/{userId}/{categoryId}")
    public ResponseEntity<ProfficiencyDto> updateProfficiency(
            @PathVariable String userId,
            @PathVariable int categoryId,
            @RequestBody UpdateProfficiencyRequestDto updateProfficiencyRequestDto
    ){
        UpdateProfficiencyRequest updateProfficiencyRequest = profficiencyMapper.fromDto(updateProfficiencyRequestDto);
        Proficiency proficiency = profficiencyService.updateProfficiency(userId,categoryId,updateProfficiencyRequest);
        ProfficiencyDto proficiencyDto = profficiencyMapper.toDto(proficiency);
        return ResponseEntity.ok(proficiencyDto);
    }



//            @PathVariable UUID userId,
//    @RequestBody UpdateUserRequestDto updateUserRequestDto
//    )33
//        UpdateUserRequest updateUserRequest = userMapper.fromDto(updateUserRequestDto);
//        User user= userService.updateUser(userId,updateUserRequest);
//        UserDto userDto = userMapper.toDto(user);
//        return ResponseEntity.ok(userDto);}
}

