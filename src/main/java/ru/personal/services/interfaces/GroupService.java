package ru.personal.services.interfaces;

import ru.personal.dto.GroupDTO;
import ru.personal.dto.GroupListDTO;
import ru.personal.dto.UserDTO;
import ru.personal.form.GroupForm;

/**
 * Date 21.10.2018
 *
 * @author Hursanov Sulaymon
 * @version v1.0
 **/
public interface GroupService {
    Long createGroup(GroupForm groupForm);
    void addUser(String token, Long groupID, String username) throws Exception;
    void deleteUser(String token, Long groupID, String username) throws Exception;
    void setMeetingLocation(String token, Long groupID, Double longitude, Double latitude, Long meetingTime) throws Exception;
    void deleteGroup(String token, Long groupID) throws Exception;
    void renameGroupTitle(String token, Long groupID, String title) throws Exception;
    void changeGroupPhoto(String token, Long groupID, String imgBase64GroupPhoto) throws Exception;
    void leaveGroup(String token, Long groupID);
    GroupDTO getGroupByID(String token, Long groupID);
    GroupListDTO getGroups(String token);

    UserDTO getMembers(String token, Long groupID);

}
