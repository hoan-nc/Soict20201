package application.data.service;

import application.constant.RoleIdConstant;
import application.constant.StatusRegisterUserEnum;
import application.constant.StatusRoleConstant;
import application.data.entity.Role;
import application.data.entity.User;
import application.data.entity.UserRole;
import application.data.repository.IRoleRepository;
import application.data.repository.IUserRepository;
import application.data.repository.IUserRoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserService {

    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private IRoleRepository roleRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IUserRoleRepository userRoleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public StatusRegisterUserEnum registerNewUser(User user) {
        logger.info("Start registerNewUser");
        try {
            // check existed user
            if (findUserByUsername(user.getUserName()) != null) {
                return StatusRegisterUserEnum.Existed_Username;
            }

            user.setPasswordHash(passwordEncoder.encode(user.getPassword()));
            // save user
            userRepository.save(user);


            // insert new role
            UserRole userRole = new UserRole();
            userRole.setRoleId(RoleIdConstant.Role_User);
            userRole.setUserId(user.getUserId());
            userRole.setStatus(StatusRoleConstant.ActiveStatus);

            userRoleRepository.save(userRole);

            return StatusRegisterUserEnum.Success;
        } catch (Exception ex) {
            logger.info("Exception on registerNewUser: " + ex.getMessage());
            return StatusRegisterUserEnum.Error_OnSystem;
        }
    }


    public User findOne(Long id) {
        return userRepository.findById(id).get();
    }



    public boolean saveUserRole(UserRole userRole) {
        try {
            userRoleRepository.save(userRole);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateUser(User user) {
        try {
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return false;
    }

    public boolean saveUser(User user) {
        try {
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return false;
    }

    public List<Role> getListRole() {
        return StreamSupport
                .stream(roleRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());

    }

    public User findUserByUsername(String username) {
        return StreamSupport
                .stream(userRepository.findByUserName(username).spliterator(), false)
                .findFirst().orElse(null);
    }

    public List<Role> getActiveListRole(Integer userId) {
        List<UserRole> listUserRoles = StreamSupport
                .stream(userRoleRepository.findRolesOfUser(userId).spliterator(), false).filter(
                        userRole -> userRole.getStatus() == StatusRoleConstant.ActiveStatus
                ).collect(Collectors.toList());

        return getListRole().stream().filter(role -> {
            return (listUserRoles.stream().filter(userRole -> userRole.getRoleId() == role.getRoleId()).findFirst().orElse(null) != null);
        }).collect(Collectors.toList());
    }


}
