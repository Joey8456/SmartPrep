package com.BTA.SmartPrep.controller;

import com.BTA.SmartPrep.domain.dto.problem.SolutionRequestDto;
import com.BTA.SmartPrep.service.SolutionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(path = "/api/v1/solution")
public class SolutionController {
    SolutionService solutionService;

    SolutionController(SolutionService solutionService) {
        this.solutionService = solutionService;
    }


    @PostMapping
    public ResponseEntity<String> getSolution(@RequestBody SolutionRequestDto requestDto) {
        try {
            String grade = solutionService.solutionGrade(
                    requestDto.codeString(),
                    requestDto.problemId(),
                    requestDto.userId(),
                    requestDto.categoryId()
            );
            return ResponseEntity.ok(grade);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/run")
    public ResponseEntity<String> getRunSolution(@RequestBody SolutionRequestDto requestDto) {
        try {
            String grade = solutionService.solutionRunGrade(
                    requestDto.codeString(),
                    requestDto.problemId(),
                    requestDto.userId(),
                    requestDto.categoryId()
            );
            return ResponseEntity.ok(grade);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
