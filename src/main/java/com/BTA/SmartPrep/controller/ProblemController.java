package com.BTA.SmartPrep.controller;

import com.BTA.SmartPrep.domain.dto.ProblemDto;
import com.BTA.SmartPrep.domain.dto.ProfficiencyDto;
import com.BTA.SmartPrep.service.ProblemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping(path = "/api/v1/problem")
@CrossOrigin(origins = "http://localhost:3000")
public class ProblemController {

    private final ProblemService problemService;

    public ProblemController(ProblemService problemService) {
        this.problemService = problemService;
    }

    @GetMapping("/{userId}/{categoryId}")
    public ResponseEntity<ProblemDto> getRandomProblemByCategory(
            @PathVariable int categoryId,
            @PathVariable String userId
    ) {
        return ResponseEntity.ok(problemService.getRandomProblemByCategory(categoryId,userId));
    }
}
