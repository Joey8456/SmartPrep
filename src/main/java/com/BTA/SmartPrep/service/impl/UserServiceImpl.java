package com.BTA.SmartPrep.service.impl;

import com.BTA.SmartPrep.domain.CreateProfficiencyRequest;
import com.BTA.SmartPrep.domain.CreateUserRequest;
import com.BTA.SmartPrep.domain.dto.UserDto;
import com.BTA.SmartPrep.domain.entity.User;
import com.BTA.SmartPrep.mapper.ProfficiencyMapper;
import com.BTA.SmartPrep.repository.ProficiencyRepository;
import com.BTA.SmartPrep.repository.UserRepository;
import com.BTA.SmartPrep.service.ProfficiencyService;
import com.BTA.SmartPrep.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service //Marks UserServiceImpl as a bean, and injects any dependencies needed.
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ProficiencyRepository proficiencyRepository;
    private final ProfficiencyMapper profficiencyMapper;

    public UserServiceImpl (UserRepository userRepository, ProficiencyRepository proficiencyRepository,ProfficiencyMapper profficiencyMapper){
        this.userRepository = userRepository;
        this.proficiencyRepository = proficiencyRepository;
        this. profficiencyMapper = profficiencyMapper;
    }
    @Override
    public User createUser(CreateUserRequest request) {
        User user = new User(null, request.username(), request.email(), request.passhash() );
        User savedUser = userRepository.save(user);
        CreateProfficiencyRequest profficiencyRequest = new CreateProfficiencyRequest(savedUser.getId(), 1, 0);
        ProfficiencyService profficiencyService = new ProfficiencyServiceImpl(proficiencyRepository,profficiencyMapper);
        profficiencyService.createProfficiency(profficiencyRequest);

        return savedUser;
    }
    @Override
    public Optional<UserDto> getUser(UUID userId) {
        return userRepository.findById(userId)
                .map(user -> new UserDto(user.getId(), user.getUserName(), user.getEmail()));
    }

    @Override
    public User getUserLogin(String email, String passhash) {
        User user = userRepository.findByEmail(email);
        if (user.getPass_hash().equals(passhash)){
            return user;
        }
        else{
            throw new RuntimeException("Invalid email or password");
        }
    }
}
