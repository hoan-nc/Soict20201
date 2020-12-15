package application.service;

import application.entity.PhysicalExamEntity;
import application.entity.RoleEntity;
import application.entity.UserEntity;
import application.entity.UserRoleEntity;
import application.entity.id.UserRoleId;
import application.repository.IPhysicalExamRepository;
import application.repository.IRoleRepository;
import application.repository.IUserRepository;
import application.repository.IUserRoleRepository;
import application.utils.Const;
import application.utils.StatusRegisterUserEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    private final IRoleRepository roleRepository;

    private final IUserRepository userRepository;

    private final IUserRoleRepository userRoleRepository;

    private final PasswordEncoder passwordEncoder;

    private final IPhysicalExamRepository physicalExamRepository;

    @Autowired
    public UserService(IRoleRepository roleRepository,
                       IUserRepository userRepository,
                       IUserRoleRepository userRoleRepository,
                       PasswordEncoder passwordEncoder,
                       IPhysicalExamRepository physicalExamRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
        this.physicalExamRepository = physicalExamRepository;
    }

    public StatusRegisterUserEnum registerNewUser(UserEntity userEntity) {
        // check existed user
        if (userRepository.findByUserNameIgnoreCase(userEntity.getUserName().trim()).isPresent()) {
            return StatusRegisterUserEnum.Existed_Username;
        }
        // save user
        userEntity.setId(null);
        userEntity.setPasswordHash(passwordEncoder.encode(userEntity.getPassword()));
        UserEntity userSaved = userRepository.save(userEntity);

        RoleEntity roleEntity = roleRepository.findById(Const.RoleId.USER).get();

        // insert new role
        UserRoleId userRoleId = new UserRoleId();
        userRoleId.setRoleId(roleEntity.getId());
        userRoleId.setUserId(userSaved.getId());

        UserRoleEntity userRoleEntity = new UserRoleEntity();
        userRoleEntity.setId(userRoleId);
        userRoleEntity.setRole(roleEntity);
        userRoleEntity.setUser(userSaved);
        userRoleEntity.setStatus(true);

        userRoleRepository.save(userRoleEntity);

        return StatusRegisterUserEnum.Success;
    }

    public UserEntity findOne(Long id) {
        return userRepository.findById(id).get();
    }

    public boolean saveUserRole(UserRoleEntity userRoleEntity) {
        try {
            userRoleRepository.save(userRoleEntity);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateUser(UserEntity userEntity) {
        try {
            userRepository.save(userEntity);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return false;
    }

    public boolean saveUser(UserEntity userEntity) {
        try {
            userRepository.save(userEntity);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return false;
    }

    public List<RoleEntity> getAllRoles() {
        return roleRepository.findAll();
    }

    public List<RoleEntity> getListRoleActiveOfUser(Long userId) {
        return roleRepository.findAllRoleOfUser(userId);
    }

    public List<PhysicalExamEntity> getAllPhysicalExamByUser(String username) {
        return physicalExamRepository.findAllByUser(username);
    }
   public UserEntity getByUsername(String userName){
        return userRepository.findByUserName(userName);
   }
}
