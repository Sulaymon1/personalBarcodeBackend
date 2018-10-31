package ru.personal.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.personal.constants.FriendStatus;
import ru.personal.dto.UserDTO;
import ru.personal.dto.UserProfileDTO;
import ru.personal.models.*;
import ru.personal.repositories.AdvertisementRepository;
import ru.personal.repositories.UserRepository;
import ru.personal.security.JwtTokenUtil;
import ru.personal.services.interfaces.ControlAccessService;
import ru.personal.services.interfaces.UserService;

import java.util.*;

/**
 * Date 26.09.2018
 *
 * @author Hursanov Sulaymon
 * @version v1.0
 *
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
        User guest = userService.getUserByToken(token);
        User ownerProfile = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("user not found by <"+username+">"));
        saveGuest(ownerProfile, guest);
        ControlAccessPage controlAccessPage = ownerProfile.getControlAccessPage();
        if (controlAccessPage != null){
            if (controlAccessPage.getIsClosed()){
                if (friendStatus(ownerProfile, guest) == FriendStatus.FRIENDS){
                    return getUserDTO(ownerProfile, FriendStatus.FRIENDS);
                }else {
                    return getPrivateUserDTO(ownerProfile, friendStatus(ownerProfile, guest));
                }
            }
        }
        return getUserDTO(ownerProfile, friendStatus(ownerProfile, guest));
    }

    private FriendStatus friendStatus(User ownerProfile, User guest){
        ControlAccessPage controlAccessPage = ownerProfile.getControlAccessPage();
        ControlAccessPage controlAccessPage1 = guest.getControlAccessPage();
        if (controlAccessPage != null){
            if(controlAccessPage.getFriends().contains(guest))
                return FriendStatus.FRIENDS;
            else if (controlAccessPage.getUsersRequest().contains(guest))
                return FriendStatus.REQUEST_SENT;
            else if (controlAccessPage1 != null && controlAccessPage1.getUsersRequest().contains(ownerProfile))
                return FriendStatus.REPLY_REQUEST;
        }else if (controlAccessPage1 != null && controlAccessPage1.getUsersRequest().contains(ownerProfile)){
            return FriendStatus.REPLY_REQUEST;
        }
        return FriendStatus.SEND_REQUEST;
    }

    private void saveGuest(User ownerProfile, User guest){
        if (guest.equals(ownerProfile)){
            return;
        }
        Guest guestP = Guest.builder()
                .enteredDate(System.currentTimeMillis()/1000L)
                .guest(guest)
                .build();
        List<Guest> guests = ownerProfile.getGuests();
        if (guests == null){
            guests = new ArrayList<>();
        }
        if (guests.size()>30){
            guests.remove(0);
        }
        if (guests.size() == 0){
            guests.add(guestP);
        }else {
            Guest guest1 = guests.get(guests.size()-1);
            if (!guest1.getGuest().equals(guest)){
                guests.add(guestP);
            }
        }
        ownerProfile.setGuests(guests);
        userRepository.save(ownerProfile);
    }

    @Override
    public void addNewRequestUser(String username, String token) {
        User guest = userService.getUserByToken(token);
        User ownerProfile = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("user not found by <" + username + ">"));
        ControlAccessPage controlAccessPage = ownerProfile.getControlAccessPage();
        if (controlAccessPage == null) {
            controlAccessPage = new ControlAccessPage();
            controlAccessPage.setIsClosed(false);
            controlAccessPage.setUsersRequest(new ArrayList<>());
            controlAccessPage.setFriends(new ArrayList<>());
        }


        if (!controlAccessPage.getFriends().contains(guest) && !controlAccessPage.getUsersRequest().contains(guest)) {
            controlAccessPage.getUsersRequest().add(guest);
            ownerProfile.setControlAccessPage(controlAccessPage);
            userRepository.save(ownerProfile);
        }
    }

    @Override
    public void accept(String username, String token) throws Exception {
        User user = userService.getUserByToken(token);
        User requestedUser = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new Exception("Requested user not found"));
        ControlAccessPage controlAccessPage = user.getControlAccessPage();
        ControlAccessPage controlAccessPage1 = requestedUser.getControlAccessPage();
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

                if (controlAccessPage1 == null){
                    controlAccessPage1 = new ControlAccessPage();
                    controlAccessPage1.setIsClosed(false);
                    controlAccessPage1.setUsersRequest(new ArrayList<>());
                    controlAccessPage1.setFriends(new ArrayList<>());
                }
                controlAccessPage1.getFriends().add(user);
                requestedUser.setControlAccessPage(controlAccessPage1);
                userRepository.save(requestedUser);
            }
        }
    }

    @Override
    public void decline(String username, String token) throws Exception {
        User user = userService.getUserByToken(token);
        User requestedUser = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new Exception("Requested user not found"));
        ControlAccessPage controlAccessPage = user.getControlAccessPage();
        if (controlAccessPage != null){
            controlAccessPage.getUsersRequest().removeIf(u->u.getId().equals(requestedUser.getId()));
            user.setControlAccessPage(controlAccessPage);
            userRepository.save(user);
        }
    }

    @Override
    public void deleteFriend(String username, String token) throws Exception {
        User user = userService.getUserByToken(token);
        User requestedUser = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new Exception("Requested user not found"));
        ControlAccessPage controlAccessPage = user.getControlAccessPage();
        ControlAccessPage controlAccessPage1 = requestedUser.getControlAccessPage();
        if (controlAccessPage != null){
            controlAccessPage.getFriends().removeIf(u-> u.getId().equals(requestedUser.getId()));
            user.setControlAccessPage(controlAccessPage);
            userRepository.save(user);
            if (controlAccessPage1 != null){
                controlAccessPage1.getFriends().removeIf(u-> u.getId().equals(user.getId()));
                requestedUser.setControlAccessPage(controlAccessPage1);
                userRepository.save(requestedUser);
            }
        }
    }
    @Override
    public void cancelRequest(String username, String token) throws Exception {
        User user = userService.getUserByToken(token);
        User requestedUser = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new Exception("Requested user not found"));
        ControlAccessPage controlAccessPage1 = requestedUser.getControlAccessPage();
        if (controlAccessPage1 != null){
            controlAccessPage1.getUsersRequest().removeIf(u-> u.getId().equals(user.getId()));
            requestedUser.setControlAccessPage(controlAccessPage1);
            userRepository.save(requestedUser);
        }
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
                    .userPhoneNumbers(new ArrayList<>())
                    .build();
        friends.forEach(user1 -> {
                userDTO.getUserPhoneNumbers().add(user1.getPhoneNumber());
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

    private UserProfileDTO getUserDTO(User user, FriendStatus friendStatus){
        Advertisement advertisement = advertisementRepository.findFirstByUserId(user.getId()).orElse(null);
        Location location = user.getLocation();
        Boolean locationStatus = null;
        if (location != null){
            locationStatus = location.getLocationStatus();
        }
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
                .locationStatus(locationStatus)
                .longitude(locationStatus!=null&&locationStatus?location.getLongitude():null)
                .latitude(locationStatus!= null&&locationStatus? location.getLatitude():null)
                .locationTime(locationStatus!=null&&locationStatus? location.getUnixtime(): null)
                .friendStatus(friendStatus)
                .withUsername(user.getWithUsername())
                .build();
    }

    private UserProfileDTO getPrivateUserDTO(User guestProfile, FriendStatus friendStatus){
        return UserProfileDTO
                .builder()
                .username(guestProfile.getUsername())
                .name(guestProfile.getName())
                .lastName(guestProfile.getLastName())
                .qrImagePath(guestProfile.getQrImagePath())
                .profilePhotoPath(guestProfile.getProfilePhotoPath())
                .coverPhotoPath(guestProfile.getCoverPhotoPath())
                .friendStatus(friendStatus)
                .build();
    }
}
