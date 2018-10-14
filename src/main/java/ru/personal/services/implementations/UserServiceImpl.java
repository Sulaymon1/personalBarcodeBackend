package ru.personal.services.implementations;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import ru.personal.constants.Image;
import ru.personal.dto.GuestDto;
import ru.personal.dto.UserProfileDTO;
import ru.personal.mapper.UserMapper;
import ru.personal.models.Guest;
import ru.personal.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.personal.repositories.UserRepository;
import ru.personal.security.JwtTokenUtil;
import ru.personal.services.interfaces.FileInfoService;
import ru.personal.services.interfaces.UserService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    private UserMapper userMapper;

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
    public Boolean updateUsername(String token, String username, String qrImage){
        User user = getUserByToken(token);
        if (hasUsername(username)){
            user.setUsername(username);
            String imageName = fileInfoService.savePicture(qrImage, Image.QRimage);
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
        User user = userRepository.findFirstByPhoneNumber(userFromToken.getPhoneNumber());
        if (user == null){
            throw new IllegalArgumentException("user not found");
        }
        return user;
    }

    @Override
    public UserProfileDTO getUserDTOByToken(String token) {
        User user = getUserByToken(token);
        return userMapper.toUserDTO(user);
    }


    @Override
    public GuestDto getUserGuests(String token) {
        User user = getUserByToken(token);
        List<Guest> guests = user.getGuests();
        List<GuestDto> guestDtoList = new ArrayList<>();
        if (guests != null){
            List<String> lastName = new ArrayList<>();
            List<String> name = new ArrayList<>();
            List<String> profilePhoto = new ArrayList<>();
            List<LocalDateTime> date = new ArrayList<>();
            List<String> username = new ArrayList<>();
            guests.forEach(guest -> {
                User userGuest = guest.getGuest();
                lastName.add(userGuest.getLastName());
                name.add(userGuest.getName());
                profilePhoto.add(userGuest.getProfilePhotoPath());
                date.add(guest.getEnteredDate());
                username.add(userGuest.getUsername());
            });
            GuestDto guestDto = GuestDto.builder()
                    .lastname(lastName)
                    .name(name)
                    .profilePhoto(profilePhoto)
                    .date(date)
                    .username(username)
                    .build();
            return guestDto;
        }
        return null;
    }

    @Override
    public boolean hasUsername(String username) {
        return !userRepository.findUserByUsername(username).isPresent();
    }

    @Override
    public void updateUserInfo(String token, String name, String lastname) {
        User user = jwtTokenUtil.getUserFromToken(token);
        user = userRepository.findFirstByPhoneNumber(user.getPhoneNumber());
        if (name != null){
            user.setName(name);
        }
        if (lastname != null){
            user.setLastName(lastname);
        }

        userRepository.save(user);
    }

    @PostMapping("/updatePic")
    public ResponseEntity updatePic(){
        return ResponseEntity.ok().build();
    }

}
