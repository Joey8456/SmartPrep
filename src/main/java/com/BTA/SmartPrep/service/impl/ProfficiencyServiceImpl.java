package com.BTA.SmartPrep.service.impl;

import com.BTA.SmartPrep.domain.CreateProfficiencyRequest;
import com.BTA.SmartPrep.domain.UpdateProfficiencyRequest;
import com.BTA.SmartPrep.domain.entity.Proficiency;
import com.BTA.SmartPrep.repository.ProficiencyRepository;
import com.BTA.SmartPrep.service.ProfficiencyService;
import org.springframework.stereotype.Service;

@Service
public class ProfficiencyServiceImpl implements ProfficiencyService {
    private final ProficiencyRepository proficiencyRepository;

    public ProfficiencyServiceImpl (ProficiencyRepository proficiencyRepository){
        this.proficiencyRepository = proficiencyRepository;
    }
    @Override
    public Proficiency createProfficiency(CreateProfficiencyRequest request) {
        Proficiency.ProficiencyId id = new Proficiency.ProficiencyId(request.user_ID(), request.category_ID());
        Proficiency proficiency = new Proficiency(id, request.proficiency());
        proficiencyRepository.insertProficiency(proficiency.getId().getUserId(),proficiency.getId().getCategoryId(),proficiency.getProficiency());
        return proficiency;
    }

    @Override
    public Proficiency updateProfficiency(String proficencyId, UpdateProfficiencyRequest request) {
        return null;
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