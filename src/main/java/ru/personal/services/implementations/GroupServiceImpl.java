package ru.personal.services.implementations;

import org.apache.commons.rng.simple.RandomSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.personal.constants.Image;
import ru.personal.dto.GroupDTO;
import ru.personal.dto.GroupListDTO;
import ru.personal.dto.UserDTO;
import ru.personal.form.GroupForm;
import ru.personal.models.Group;
import ru.personal.models.User;
import ru.personal.repositories.GroupRepository;
import ru.personal.security.JwtTokenUtil;
import ru.personal.services.interfaces.FileInfoService;
import ru.personal.services.interfaces.GroupService;
import ru.personal.services.interfaces.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Date 21.10.2018
 *
 * @author Hursanov Sulaymon
 * @version v1.0
 **/
@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserService userService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private FileInfoService fileInfoService;

    private Group getGroupByTokenAndGroupID(String token, Long groupID) throws Exception {
        User user = jwtTokenUtil.getUserFromToken(token);
        return groupRepository.findFirstByGroupIDAndAdmin_Id(groupID, user.getId())
                .orElseThrow(()-> new Exception("group not found by groupID <"+groupID+">"));
    }

    @Override
    public Long createGroup(GroupForm groupForm) {
        User user = userService.getUserByToken(groupForm.getToken());

        List<User> members = user.getControlAccessPage().getFriends()
                .stream()
                .filter(user1 -> groupForm.getUsername().contains(user1.getUsername()))
                .collect(Collectors.toList());

        String picName = fileInfoService.savePicture(groupForm.getImage(), Image.GroupPhoto);
        Group group = Group.builder()
                .admin(user)
                .groupPhoto(picName)
                .members(members)
                .groupID(RandomSource.create(RandomSource.MT).nextLong())
                .title(groupForm.getTitle())
                .build();
        Optional<Group> existingGroup = groupRepository.findFirstByGroupID(group.getGroupID());
        if (!existingGroup.isPresent()){
            Group save = groupRepository.save(group);
            return save.getGroupID();
        }else {
            createGroup(groupForm); // if group exist then recreate group with other random number
        }
        return null;
    }

    @Override
    public void addUser(String token, Long groupID, String username) throws Exception {
        Group group = getGroupByTokenAndGroupID(token, groupID);
        Optional<User> memberOptional = userService.getUserByUsername(username);
        if (group != null && memberOptional.isPresent()){
            if (group.getMembers() == null){
                group.setMembers(new ArrayList<>());
            }
            User user = memberOptional.get();
            if (!group.getMembers().contains(user))
                group.getMembers().add(user);
            groupRepository.save(group);
        }
    }

    @Override
    public void deleteUser(String token, Long groupID, String username) throws Exception {
        Group group = getGroupByTokenAndGroupID(token, groupID);
        Optional<User> userOptional = userService.getUserByUsername(username);
        if (group != null && userOptional.isPresent()){
            group.getMembers().remove(userOptional.get());
            groupRepository.save(group);
        }
    }

    @Override
    public void setMeetingLocation(String token, Long groupID, Double longitude, Double latitude, Long meetingTime) throws Exception {
        Group group = getGroupByTokenAndGroupID(token, groupID);
        if (group != null){
            group.setLongitude(longitude);
            group.setLatitude(latitude);
            group.setMeetingTime(meetingTime);
            groupRepository.save(group);
        }
    }

    @Override
    public void deleteGroup(String token, Long groupID) throws Exception {
        Group group = getGroupByTokenAndGroupID(token, groupID);
        if (group != null){
            groupRepository.removeByGroupID(group.getGroupID());
        }
    }

    @Override
    public void renameGroupTitle(String token, Long groupID, String title) throws Exception {
        Group group = getGroupByTokenAndGroupID(token, groupID);
        if (group != null){
            group.setTitle(title);
            groupRepository.save(group);
        }
    }

    @Override
    public void changeGroupPhoto(String token, Long groupID, String imgBase64GroupPhoto) throws Exception {
        User user = jwtTokenUtil.getUserFromToken(token);
        Group group = groupRepository.findFirstByGroupIDAndAdmin_Id(groupID, user.getId())
                .orElseThrow(()-> new Exception("group not found in change photo group method"));
        if (group != null){
            String groupPhoto = fileInfoService.savePicture(imgBase64GroupPhoto, Image.GroupPhoto);
            group.setGroupPhoto(groupPhoto);
            groupRepository.save(group);
        }
    }

    @Override
    public void leaveGroup(String token, Long groupID) {
        User user = userService.getUserByToken(token);
        Optional<Group> groupOptional = groupRepository.findFirstByGroupID(groupID);
        if (groupOptional.isPresent()){
            Group group = groupOptional.get();
            if (group.getAdmin().equals(user)){
                if (group.getMembers().size()>0){
                    group.setAdmin(group.getMembers().get(0));
                }
            }else
                group.getMembers().removeIf(user1 -> user1.equals(user));
            groupRepository.save(group);
        }
    }

    @Override
    public GroupDTO getGroupByID(String token, Long groupID) {
        User user = userService.getUserByToken(token);
        Optional<Group> groupOptional = groupRepository.findFirstByGroupID(groupID);
        if (groupOptional.isPresent()){
            Group group = groupOptional.get();
            if (group.getAdmin().equals(user)) {
                return GroupDTO.builder()
                        .adminUsername(user.getUsername())
                        .groupID(groupID)
                        .title(group.getTitle())
                        .groupPhoto(group.getGroupPhoto())
                        .isAdmin(true)
                        .longitude(group.getLongitude())
                        .latitude(group.getLatitude())
                        .meetingTime(group.getMeetingTime())
                        .build();
            }else if (group.getMembers() != null && group.getMembers().contains(user)){
                User admin = group.getAdmin();
                return GroupDTO.builder()
                        .adminUsername(admin.getUsername())
                        .groupID(groupID)
                        .title(group.getTitle())
                        .groupPhoto(group.getGroupPhoto())
                        .isAdmin(false)
                        .longitude(group.getLongitude())
                        .latitude(group.getLatitude())
                        .meetingTime(group.getMeetingTime())
                        .build();
            }
        }
        return null;
    }

    @Override
    public UserDTO getMembers(String token, Long groupID) {
        User user = userService.getUserByToken(token);
        Optional<Group> groupOptional = groupRepository.findFirstByGroupID(groupID);
        if (groupOptional.isPresent()){
            Group group = groupOptional.get();
            User admin = group.getAdmin();
            if (group.getMembers().contains(user) || group.getAdmin().equals(user)){
                UserDTO userDTO = UserDTO.builder()
                        .name(new ArrayList<>())
                        .lastname(new ArrayList<>())
                        .profilePhoto(new ArrayList<>())
                        .username(new ArrayList<>())
                        .build();

                userDTO.getName().add(admin.getName());
                userDTO.getLastname().add(admin.getLastName());
                userDTO.getProfilePhoto().add(admin.getProfilePhotoPath());
                userDTO.getUsername().add(admin.getUsername());
                group.getMembers().forEach(member ->{
                    userDTO.getName().add(member.getName());
                    userDTO.getLastname().add(member.getLastName());
                    userDTO.getProfilePhoto().add(member.getProfilePhotoPath());
                    userDTO.getUsername().add(member.getUsername());
                });

                return userDTO;
            }
        }
        return null;
    }

    @Override
    public GroupListDTO getGroups(String token) {
        User user = userService.getUserByToken(token);
        List<Group> groups = groupRepository.findAllByMembersInOrAdmin(user.getId());
        List<Group> allByAdmin = groupRepository.findAllByAdmin(user);
        GroupListDTO groupListDTO = GroupListDTO.builder()
                .groupID(new ArrayList<>())
                .title(new ArrayList<>())
                .groupPhoto(new ArrayList<>())
                .membersCount(new ArrayList<>())
                .build();
        allByAdmin.forEach(group -> {
            groupListDTO.getGroupID().add(group.getGroupID());
            groupListDTO.getTitle().add(group.getTitle());
            groupListDTO.getGroupPhoto().add(group.getGroupPhoto());
            if (group.getMembers()!=null){
                groupListDTO.getMembersCount().add((long) group.getMembers().size());
            }
        });
        groups.forEach(group -> {
            groupListDTO.getGroupID().add(group.getGroupID());
            groupListDTO.getTitle().add(group.getTitle());
            groupListDTO.getGroupPhoto().add(group.getGroupPhoto());
            if (group.getMembers()!=null){
                groupListDTO.getMembersCount().add((long) group.getMembers().size());
            }
        });

        return groupListDTO;
    }
}
