package com.BTA.SmartPrep.service.impl;

import com.BTA.SmartPrep.domain.CreateProfficiencyRequest;
import com.BTA.SmartPrep.domain.UpdateProfficiencyRequest;
import com.BTA.SmartPrep.domain.dto.ProfficiencyDto;
import com.BTA.SmartPrep.domain.entity.Proficiency;
import com.BTA.SmartPrep.mapper.ProfficiencyMapper;
import com.BTA.SmartPrep.repository.ProficiencyRepository;
import com.BTA.SmartPrep.service.ProfficiencyService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfficiencyServiceImpl implements ProfficiencyService {
    private final ProficiencyRepository proficiencyRepository;
    private final ProfficiencyMapper profficiencyMapper;

    public ProfficiencyServiceImpl (ProficiencyRepository proficiencyRepository,ProfficiencyMapper profficiencyMapper){
        this.proficiencyRepository = proficiencyRepository;
        this.profficiencyMapper = profficiencyMapper;
    }
    @Override
    public Proficiency createProfficiency(CreateProfficiencyRequest request) {
        Proficiency.ProficiencyId id = new Proficiency.ProficiencyId(request.user_ID(), request.category_ID());
        Proficiency proficiency = new Proficiency(id, request.proficiency());
        proficiencyRepository.insertProficiency(proficiency.getId().getUserId(),1,1);
        proficiencyRepository.insertProficiency(proficiency.getId().getUserId(),2,1);
        proficiencyRepository.insertProficiency(proficiency.getId().getUserId(),3,1);
        return proficiency;
    }

    @Override
    public Proficiency updateProfficiency(String userId, int categoryId, UpdateProfficiencyRequest request) {
        Proficiency proficiency = proficiencyRepository
                .findByIdUserIdAndIdCategoryId(userId, categoryId)
                .orElseThrow(() -> new RuntimeException("Not found"));

        proficiency.setProficiency(request.proficiency());   // or req.proficiency()
        System.out.println(proficiency);
        return proficiencyRepository.save(proficiency);
    }

    @Override
    public ProfficiencyDto getProfficiency(String userId, int categoryID) {
        Proficiency proficiency = proficiencyRepository
                .findByIdUserIdAndIdCategoryId(userId,categoryID)
                .orElseThrow(() -> new RuntimeException("Not found"));

        return profficiencyMapper.toDto(proficiency);
    }

}

//@Service //Marks UserServiceImpl as a bean, and injects any dependencies needed.
//public class UserServiceImpl implements UserService {
//    private final UserRepository userRepository;
//
//    public UserServiceImpl (UserRepository userRepository){
//        this.userRepository = userRepository;
//    }
//
//    @Override
//    public User createUser(CreateUserRequest request) {
//        User user = new User(null, request.username(), request.email(), request.passhash() );
//        userRepository.insertUser(user.getUserName(), user.getEmail(), user.getPass_hash());
//        return user;
//    }
//
//    @Override
//    public List<User> listUsers() {
//        return userRepository.findAll();
//    }