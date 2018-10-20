package ru.personal.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.personal.dto.UserDTO;
import ru.personal.dto.UserProfileDTO;
import ru.personal.models.Advertisement;
import ru.personal.models.ControlAccessPage;
import ru.personal.models.Guest;
import ru.personal.models.User;
import ru.personal.repositories.AdvertisementRepository;
import ru.personal.repositories.UserRepository;
import ru.personal.security.JwtTokenUtil;
import ru.personal.services.interfaces.ControlAccessService;
import ru.personal.services.interfaces.UserService;

import java.time.LocalDateTime;
import java.util.*;

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

    @Autowired
    private UserService userService;

    @Autowired
    private AdvertisementRepository advertisementRepository;

    @Override
    public UserProfileDTO getUserProfile(String username, String token) {
        User user = userService.getUserByToken(token);
        User guestProfile = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("user not found by <"+username+">"));
        saveGuest(guestProfile, user);
        ControlAccessPage controlAccessPage = guestProfile.getControlAccessPage();
        if (controlAccessPage != null){
            if (controlAccessPage.getIsClosed()){
                if(controlAccessPage.getFriends().contains(user)){
                    return getUserDTO(guestProfile);
                }else if (controlAccessPage.getUsersRequest().contains(user))
                    return getPrivateUserDTO(guestProfile, true);
                else
                    return getPrivateUserDTO(guestProfile, false);
            }
        }
        return getUserDTO(guestProfile);
    }

    private void saveGuest(User guestUser, User user){
        if (user.equals(guestUser)){
            return;
        }
        Guest guestP = Guest.builder()
                .enteredDate(System.currentTimeMillis()/1000L)
                .guest(user)
                .build();
        List<Guest> guests = guestUser.getGuests();
        if (guests == null){
            guests = new ArrayList<>();
        }
        if (guests.size()>10){
            guests.remove(0);
        }
        guests.add(guestP);
        guestUser.setGuests(guests);
        userRepository.save(guestUser);
    }

    @Override
    public void addNewRequestUser(String username, String token) {
        User user = userService.getUserByToken(token);
        User guestProfile = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("user not found by <" + username + ">"));
        ControlAccessPage controlAccessPage = guestProfile.getControlAccessPage();
        if (controlAccessPage == null) {
            controlAccessPage = new ControlAccessPage();
            controlAccessPage.setIsClosed(false);
            controlAccessPage.setUsersRequest(new ArrayList<>());
            controlAccessPage.setFriends(new ArrayList<>());
        }
        if (!controlAccessPage.getFriends().contains(user) && !controlAccessPage.getUsersRequest().contains(user)) {
            controlAccessPage.getUsersRequest().add(user);
            guestProfile.setControlAccessPage(controlAccessPage);
            userRepository.save(guestProfile);
        }
    }

    @Override
    public void accept(String username, String token) throws Exception {
        User user = userService.getUserByToken(token);
        User requestedUser = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new Exception("Requested user not found"));
        ControlAccessPage controlAccessPage = user.getControlAccessPage();
        if (controlAccessPage == null){
            return;
        }
        // if guest user is requested then do guest user role is follower
        if(controlAccessPage.getUsersRequest().contains(requestedUser)){
            controlAccessPage.getUsersRequest().remove(requestedUser);
            if (!controlAccessPage.getFriends().contains(requestedUser)){
                controlAccessPage.getFriends().add(requestedUser);
                user.setControlAccessPage(controlAccessPage);
                userRepository.save(user);
            }
        }
    }

    @Override
    public void decline(String username, String token) throws Exception {
        User user = userService.getUserByToken(token);
        User requestedUser = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new Exception("Requested user not found"));
        ControlAccessPage controlAccessPage = user.getControlAccessPage();
        controlAccessPage.getUsersRequest().removeIf(u->u.getId().equals(requestedUser.getId()));
    }

    @Override
    public void deleteFollower(String username, String token) throws Exception {
        User user = userService.getUserByToken(token);
        User requestedUser = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new Exception("Requested user not found"));
        ControlAccessPage controlAccessPage = user.getControlAccessPage();
        controlAccessPage.getFriends().removeIf(u-> u.getId().equals(requestedUser.getId()));
    }

    @Override
    public UserDTO getRequestedUsers(String token) {
        User user = userService.getUserByToken(token);
        ControlAccessPage controlAccessPage = user.getControlAccessPage();
        if (controlAccessPage == null){
            return null;
        }
        UserDTO userDTO = UserDTO.builder()
                .lastname(new ArrayList<>())
                .name(new ArrayList<>())
                .profilePhoto(new ArrayList<>())
                .username(new ArrayList<>())
                .build();
        List<User> usersRequest = controlAccessPage.getUsersRequest();
        usersRequest.forEach(user1 ->{
            userDTO.getLastname().add(user1.getLastName());
            userDTO.getName().add(user1.getName());
            userDTO.getProfilePhoto().add(user1.getProfilePhotoPath());
            userDTO.getUsername().add(user1.getUsername());
        });
        return userDTO;
    }

    @Override
    public UserDTO getFriends(String token) {
        User user = userService.getUserByToken(token);
        ControlAccessPage controlAccessPage = user.getControlAccessPage();
        if (controlAccessPage == null){
            return null;
        }
        List<User> friends = controlAccessPage.getFriends();
        UserDTO userDTO = UserDTO.builder()
                    .lastname(new ArrayList<>())
                    .name(new ArrayList<>())
                    .profilePhoto(new ArrayList<>())
                    .username(new ArrayList<>())
                    .build();
        friends.forEach(user1 -> {
                userDTO.getLastname().add(user1.getLastName());
                userDTO.getName().add(user1.getName());
                userDTO.getProfilePhoto().add(user1.getProfilePhotoPath());
                userDTO.getUsername().add(user1.getUsername());
            });
        return userDTO;
    }

    @Override
    public void closeProfile(String token, Boolean status) {
        User user = userService.getUserByToken(token);
        ControlAccessPage controlAccessPage = user.getControlAccessPage();
        if (controlAccessPage == null){
            controlAccessPage = new ControlAccessPage();
        }
        controlAccessPage.setIsClosed(status);
        user.setControlAccessPage(controlAccessPage);
        userRepository.save(user);
    }

    private UserProfileDTO getUserDTO(User user){
        Advertisement advertisement = advertisementRepository.findFirstByUserId(user.getId()).orElse(null);
        return UserProfileDTO.builder()
                .name(user.getName())
                .lastName(user.getLastName())
                .coverPhotoPath(user.getCoverPhotoPath())
                .profilePhotoPath(user.getProfilePhotoPath())
                .qrImagePath(user.getQrImagePath())
                .socialNetwork(user.getSocialNetwork())
                .phoneNumber(user.getPhoneNumber())
                .username(user.getUsername())
                .advertisement(advertisement)
                .city(user.getCity())
                .country(user.getCountry())
                .bCity(user.getBCity())
                .bCountry(user.getBCountry())
                .bExtra(user.getBExtra())
                .status(user.getStatus())
                .withUsername(user.getWithUsername())
                .build();
    }

    private UserProfileDTO getPrivateUserDTO(User guestProfile, Boolean isRequested){
        return UserProfileDTO
                .builder()
                .username(guestProfile.getUsername())
                .name(guestProfile.getName())
                .lastName(guestProfile.getLastName())
                .qrImagePath(guestProfile.getQrImagePath())
                .profilePhotoPath(guestProfile.getProfilePhotoPath())
                .coverPhotoPath(guestProfile.getCoverPhotoPath())
                .isRequested(isRequested)
                .build();
    }
}
