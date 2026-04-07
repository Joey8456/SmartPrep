package com.BTA.SmartPrep.service.impl;

import com.BTA.SmartPrep.domain.dto.ProblemDto;
import com.BTA.SmartPrep.domain.entity.Problem;
import com.BTA.SmartPrep.domain.entity.Proficiency;
import com.BTA.SmartPrep.mapper.ProblemMapper;
import com.BTA.SmartPrep.repository.ProblemRepository;
import com.BTA.SmartPrep.repository.ProficiencyRepository;
import com.BTA.SmartPrep.service.ProblemService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
    public class ProblemServiceImpl implements ProblemService {

        private final ProblemRepository problemRepository;
        private final ProblemMapper problemMapper;
        private final ProficiencyRepository proficiencyRepository;

        public ProblemServiceImpl(ProblemRepository problemRepository, ProblemMapper problemMapper, ProficiencyRepository proficiencyRepository) {
            this.problemRepository = problemRepository;
            this.problemMapper = problemMapper;
            this.proficiencyRepository = proficiencyRepository;
        }

        @Override
        public ProblemDto getRandomProblemByCategory(int categoryId,String userId) {
            String difficulty = "";
            Optional<Proficiency> optionalProficiency = proficiencyRepository.findByIdUserIdAndIdCategoryId(userId, categoryId);

            if (optionalProficiency.isPresent()) {
                Proficiency proficiency = optionalProficiency.get();
                if (proficiency.getProficiency() >= 1 && proficiency.getProficiency() <= 33) {
                    difficulty = "Easy";
                } else if (proficiency.getProficiency() >= 34 && proficiency.getProficiency() <= 66) {
                    difficulty = "Medium";
                } else if (proficiency.getProficiency() >= 67 && proficiency.getProficiency() <= 100) {
                    difficulty = "Hard";
                }
            }

            Problem problem = problemRepository.findRandomByCategoryId(categoryId,difficulty)
                    .orElseThrow(() -> new RuntimeException("No problem found for category " + categoryId));

            return problemMapper.toDto(problem);
        }
    }
