package org.mpo.newstracker.service;

import org.mpo.newstracker.entity.dao.UserDao;
import org.mpo.newstracker.entity.dto.CommonResponseDto;
import org.mpo.newstracker.entity.dto.UserDto;
import org.mpo.newstracker.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<CommonResponseDto> registerUser(UserDto userDto) {
        log.info("/sign_up: Trying to sign up user: "+userDto.toString());
        if (userDto == null) {
            throw new RuntimeException("User is null:");
        }
        //UserDao user = new UserDao(userDto);
        if (userRepository.existsByUsername(userDto.getUsername())) {
            throw new RuntimeException("User already exist in database");
        } else {
            UserDao user = new UserDao(userDto);
            userRepository.save(user);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(new CommonResponseDto("User: "  +userDto.getUsername()+" was added to the system!"));
    }


    public UserDao[] getUsers() {
        log.info("GET /admin/user/ : Getting all users from DB");
        return userRepository.findAll().stream().toArray(UserDao[]::new);
    }
}
