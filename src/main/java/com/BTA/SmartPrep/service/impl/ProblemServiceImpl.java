package com.BTA.SmartPrep.service.impl;

import com.BTA.SmartPrep.domain.dto.ProblemDto;
import com.BTA.SmartPrep.domain.entity.Problem;
import com.BTA.SmartPrep.mapper.ProblemMapper;
import com.BTA.SmartPrep.repository.ProblemRepository;
import com.BTA.SmartPrep.service.ProblemService;
import org.springframework.stereotype.Service;

    @Service
    public class ProblemServiceImpl implements ProblemService {

        private final ProblemRepository problemRepository;
        private final ProblemMapper problemMapper;

        public ProblemServiceImpl(ProblemRepository problemRepository, ProblemMapper problemMapper) {
            this.problemRepository = problemRepository;
            this.problemMapper = problemMapper;
        }

        @Override
        public ProblemDto getRandomProblemByCategory(int categoryId) {
            Problem problem = problemRepository.findRandomByCategoryId(categoryId)
                    .orElseThrow(() -> new RuntimeException("No problem found for category " + categoryId));

            return problemMapper.toDto(problem);
        }
    }
