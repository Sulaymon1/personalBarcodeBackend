package ru.personal.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.personal.form.UserForm;
import ru.personal.models.Token;
import ru.personal.models.User;
import ru.personal.repositories.UserRepository;
import ru.personal.security.JwtTokenUtil;
import ru.personal.services.interfaces.AuthenticationService;

import java.util.Optional;

/**
 * Date 03.07.2018
 *
 * @author Hursanov Sulaymon
 * @version v1.0
 **/
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public Token login(UserForm userForm) {
        Optional<User> userOptional = userRepository.findUserByPhoneNumberAndPin(userForm.getPhone(), Long.valueOf(userForm.getPincode()));
        if (userOptional.isPresent()){
            User user = userOptional.get();
            String token = jwtTokenUtil.generateToken(user);
            return new Token(token);
        }else throw new IllegalArgumentException("user not found by <"+userForm.getPhone()+">");
    }
}
