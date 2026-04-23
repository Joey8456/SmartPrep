package com.BTA.SmartPrep.controller;

import com.BTA.SmartPrep.domain.dto.ProfficiencyDto;
import com.BTA.SmartPrep.domain.dto.SolutionRequestDto;
import com.BTA.SmartPrep.domain.dto.UserDto;
import com.BTA.SmartPrep.service.SolutionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(path = "/api/v1/solution")
public class SolutionController {
    SolutionService solutionService;

    SolutionController(SolutionService solutionService){
        this.solutionService = solutionService;
    }

    @PostMapping
    public ResponseEntity<?> getSolution(@RequestBody SolutionRequestDto requestDto){
        String codeString = requestDto.codeString();
        long problemId = requestDto.problemId();
        System.out.println(codeString + problemId);
        solutionService.solutionGrade(codeString,problemId);
        return ResponseEntity.ok("test");
    }

}
