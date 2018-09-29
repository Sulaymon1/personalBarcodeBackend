package ru.personal.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.personal.dto.UserProfileDTO;
import ru.personal.models.ControlAccessPage;
import ru.personal.models.User;
import ru.personal.repositories.UserRepository;
import ru.personal.security.JwtTokenUtil;
import ru.personal.services.interfaces.ControlAccessService;

import java.util.Optional;

/**
 * Date 26.09.2018
 *
 * @author Hursanov Sulaymon
 * @version v1.0
 **/
@Service
public class ControlAccessServiceImpl implements ControlAccessService {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserProfileDTO getUserProfile(String username, String token) {
        User user = jwtTokenUtil.getUserFromToken(token);
        User guestProfile = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("user not found by <"+username+">"));
        ControlAccessPage controlAccessPage = guestProfile.getControlAccessPage();
        if (controlAccessPage != null){
            if (controlAccessPage.getIsClosed()){
                if(controlAccessPage.getFollowers().contains(user)){
                    return getUserDTO(guestProfile);
                }else if (controlAccessPage.getUsersRequest().contains(user))
                    return UserProfileDTO.builder().isRequested(true).build();
                else
                    return UserProfileDTO.builder().isRequested(false).build();
            }
        }
        return getUserDTO(guestProfile);
    }

    @Override
    public void addNewRequestUser(String username, String token) {
        User user = jwtTokenUtil.getUserFromToken(token);
        user = userRepository.findFirstByPhoneNumber(user.getPhoneNumber());
        User guestProfile = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("user not found by <" + username + ">"));
        ControlAccessPage controlAccessPage = guestProfile.getControlAccessPage();
        if (controlAccessPage != null) {
            if (controlAccessPage.getIsClosed()) {
                if (!controlAccessPage.getFollowers().contains(user) && !controlAccessPage.getUsersRequest().contains(user)) {
                    controlAccessPage.getUsersRequest().add(user);
                }
            }
        }
    }

    private UserProfileDTO getUserDTO(User user){
        return UserProfileDTO.builder()
                .name(user.getName())
                .lastName(user.getLastName())
                .birthday(user.getBirthday())
                .coverPhotoPath(user.getCoverPhotoPath())
                .profilePhotoPath(user.getProfilePhotoPath())
                .qrImagePath(user.getQrImagePath())
                .socialNetwork(user.getSocialNetwork())
                .phoneNumber(user.getPhoneNumber())
                .username(user.getUsername())
                .build();
    }
}
