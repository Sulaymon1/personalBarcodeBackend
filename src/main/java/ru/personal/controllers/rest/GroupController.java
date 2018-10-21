package ru.personal.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.personal.dto.GroupDTO;
import ru.personal.dto.GroupListDTO;
import ru.personal.dto.UserDTO;
import ru.personal.form.GroupForm;
import ru.personal.services.interfaces.GroupService;

/**
 * Date 21.10.2018
 *
 * @author Hursanov Sulaymon
 * @version v1.0
 **/
@RestController
@RequestMapping("/group")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @PostMapping(value = "/create", consumes = "application/json")
    public ResponseEntity createGroup(@RequestBody GroupForm groupForm){
        groupService.createGroup(groupForm);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/addMember")
    public ResponseEntity addUser(@RequestParam String token,@RequestParam Long groupID,
                                  @RequestParam String username) throws Exception {
       groupService.addUser(token, groupID, username);
       return ResponseEntity.ok().build();
    }
    @PostMapping("/deleteMember")
    public ResponseEntity deleteUser(@RequestParam String token, @RequestParam Long groupID,
                                     @RequestParam String username) throws Exception{
        groupService.deleteUser(token, groupID, username);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/setMeetingLocation")
    public ResponseEntity setMeetingLocation(@RequestParam String token, @RequestParam Long groupID,
                                             @RequestParam Double longitude, @RequestParam Double latitude,
                                             @RequestParam Long meetingTime) throws Exception {
        groupService.setMeetingLocation(token, groupID, longitude, latitude, meetingTime);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/deleteGroup")
    public ResponseEntity deleteGroup(@RequestParam String token, @RequestParam Long groupID) throws Exception {
        groupService.deleteGroup(token, groupID);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/renameGroupTitle")
    public ResponseEntity renameGroupTitle(@RequestParam String token, @RequestParam Long groupID,
                                           @RequestParam String title) throws Exception {
        groupService.renameGroupTitle(token, groupID, title);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/changeGroupPhoto")
    public ResponseEntity changeGroupPhoto(@RequestParam String token, @RequestParam Long groupID,
                                           @RequestParam String imgBase64GroupPhoto) throws Exception {
        groupService.changeGroupPhoto(token, groupID, imgBase64GroupPhoto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/leaveGroup")
    public ResponseEntity leaveGroup(@RequestParam String token, @RequestParam Long groupID){
        groupService.leaveGroup(token, groupID);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getGroupByID")
    public ResponseEntity<GroupDTO> getGroupByID(String token, Long groupID){
        return ResponseEntity.ok(groupService.getGroupByID(token, groupID));
    }

    @GetMapping("/getGroups")
    public ResponseEntity<GroupListDTO> getGroups(String token){
        return ResponseEntity.ok(groupService.getGroups(token));
    }

    @GetMapping("/getMembers")
    public ResponseEntity<UserDTO> getMembers(String token, Long groupID){
        return ResponseEntity.ok(groupService.getMembers(token, groupID));
    }
}
