package ru.personal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.personal.models.Group;
import ru.personal.models.User;

import java.util.List;
import java.util.Optional;

/**
 * Date 21.10.2018
 *
 * @author Hursanov Sulaymon
 * @version v1.0
 **/
public interface GroupRepository extends JpaRepository<Group, Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM groups g " +
            "LEFT JOIN groups_members g2 ON g.id=g2.group_id " +
            "WHERE g2.members_id = :userID")
    List<Group> findAllByMembersInOrAdmin(@Param("userID") Long userID);
    List<Group> findAllByAdmin(User user);
    Optional<Group> findFirstByGroupIDAndAdmin_Id(Long groupID, Long adminID);
    void removeByGroupID(Long groupID);
    Optional<Group> findFirstByGroupID(Long groupID);
}
