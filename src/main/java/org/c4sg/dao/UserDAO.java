package org.c4sg.dao;

import org.c4sg.constant.UserStatus;
import org.c4sg.entity.Project;
import org.c4sg.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;

import javax.transaction.Transactional;

@Component
public interface UserDAO extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
	
    String FIND_BY_ID_QUERY = "SELECT u FROM UserProject up " +
                                "JOIN up.user u " +
                                "JOIN up.project p " +
                                "WHERE p.id =:projId and up.status= :userProjStatus";
    
    String UPDATE_SLACK_STATUS = "UPDATE User u set u.chatFlag = :isSlackReg where u.id = :userId";
    
    //temporary until create date is added
    Page<User> findByStatus(Pageable pageable, UserStatus status);

    User findById(int id);
    User findByEmail(String email);
    List<User> findAllByOrderByIdDesc();    
    List<User> findByDeveloperFlagAndPublicProfileFlagOrderByDeveloperCommitDesc(String devFlag, String publicFlag);
    // List<User> findByRoleAndDisplayFlagOrderByGithubDesc(UserRole role, Boolean display);
  
    @Transactional
    @Modifying
    @Query(UPDATE_SLACK_STATUS)
    Integer updateIsSlackRegisteredFlag(@Param("isSlackReg") Boolean isSlackReg, @Param("userId") Integer userId);
  
    @Query(FIND_BY_ID_QUERY)
    List<User> findByUserProjectId(@Param("projId") Integer projId, @Param("userProjStatus") String userProjStatus);
    
}
