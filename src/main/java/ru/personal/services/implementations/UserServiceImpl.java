package ru.personal.services.implementations;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import ru.personal.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.personal.repositories.UserRepository;
import ru.personal.security.JwtTokenUtil;
import ru.personal.services.interfaces.FileInfoService;
import ru.personal.services.interfaces.UserService;

import java.time.LocalDate;

/**
 * Date 03.07.2018
 *
 * @author Hursanov Sulaymon
 * @version v1.0
 **/
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private FileInfoService fileInfoService;

    @Override
    public void saveUser(User user) {
        User user1 =userRepository.findFirstByPhoneNumber(user.getPhoneNumber());
        if (user1 != null){
            user1.setPin(user.getPin());
            userRepository.save(user1);
        }else
            userRepository.save(user);
    }

    @Override
    public Boolean updateUsername(String token, String username, String imageBytes){
        User user = getUserByToken(token);
        if (hasUsername(username)){
            user.setUsername(username);
            String imageName = fileInfoService.savePicture(imageBytes);
            user.setQrImagePath(imageName);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public User getUserByToken(String token) {
        log.info("got token");
        User userFromToken = jwtTokenUtil.getUserFromToken(token);
        return userRepository.findFirstByPhoneNumber(userFromToken.getPhoneNumber());
    }

    @Override
    public void updateUser(String token, String username, String name, String imageBytes) {
        User userT = jwtTokenUtil.getUserFromToken(token);
        User user = userRepository.findFirstByPhoneNumber(userT.getPhoneNumber());
        if (username != null){
            if (!userRepository.findUserByUsername(username).isPresent()){
                user.setUsername(username);
            }else throw new IllegalArgumentException("Error1");
        }else if (name != null){
            user.setName(name);
        }else if (imageBytes != null){
            String imageName = fileInfoService.savePicture(imageBytes);
            user.setPicName(imageName);
        }else throw new IllegalArgumentException("Error nothing to update");
        userRepository.save(user);
    }



    @Override
    public boolean hasUsername(String username) {
        return !userRepository.findUserByUsername(username).isPresent();
    }

    @Override
    public void updateUserInfo(String token, String name, String lastname, LocalDate birthday) {
        User user = jwtTokenUtil.getUserFromToken(token);
        user = userRepository.findFirstByPhoneNumber(user.getPhoneNumber());
        if (name != null){
            user.setName(name);
        }
        if (lastname != null){
            user.setLastName(lastname);
        }
        if (birthday != null){
            user.setBirthday(birthday);
        }
        userRepository.save(user);
    }

    @PostMapping("/updatePic")
    public ResponseEntity updatePic(){
        return ResponseEntity.ok().build();
    }

}
