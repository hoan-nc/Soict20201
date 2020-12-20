package application.service.impl;

import application.domain.UserChangePass;
import application.entity.PhysicalExamEntity;
import application.entity.RoleEntity;
import application.entity.UserEntity;
import application.entity.UserRoleEntity;
import application.entity.id.UserRoleId;
import application.repository.IPhysicalExamRepository;
import application.repository.IRoleRepository;
import application.repository.IUserRepository;
import application.repository.IUserRoleRepository;
import application.service.UserService;
import application.utils.Const;
import application.utils.StatusRegisterUserEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.TreeMap;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final IRoleRepository roleRepository;

    private final IUserRepository userRepository;

    private final IUserRoleRepository userRoleRepository;

    private final PasswordEncoder passwordEncoder;

    private final IPhysicalExamRepository physicalExamRepository;

    @Autowired
    public UserServiceImpl(IRoleRepository roleRepository,
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

    @Override
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

    @Override
    public List<RoleEntity> getListRoleActiveOfUser(Long userId) {
        return roleRepository.findAllRoleOfUser(userId);
    }

    @Override
    public List<PhysicalExamEntity> getAllPhysicalExamByUser(String username) {
        return physicalExamRepository.findAllByUser(username);
    }

    @Override
    public UserEntity getByUsername(String userName) {
        return userRepository.findByUserNameIgnoreCase(userName)
                .orElseThrow(() -> new IllegalArgumentException("Not found by user name " + userName));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUserInfo(UserEntity userEntity) {
        UserEntity userEntitySaved = userRepository.findById(userEntity.getId())
                .orElseThrow(() -> new IllegalArgumentException("Not found by id " + userEntity.getId()));
        userEntitySaved.setFullName(userEntity.getFullName());
        userEntitySaved.setClassName(userEntity.getClassName());
        userRepository.save(userEntitySaved);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void changePasswordUser(UserChangePass userChangePass) {
        UserEntity userEntity = userRepository.findByUserNameIgnoreCase(userChangePass.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Not found by user name " + userChangePass.getUsername()));

        userEntity.setPassword(userChangePass.getNewPassword());
        userEntity.setPasswordHash(passwordEncoder.encode(userEntity.getPassword()));

        userRepository.save(userEntity);
    }

    @Override
    public TreeMap<String, Double> getStatisticHeightOfUser(String username) {
        TreeMap<String, Double> mapData = new TreeMap<>();
        physicalExamRepository.statisticHeightByYearOfUser(username).forEach(obj -> mapData.put(obj.getName().toString(), obj.getValue()));
        return mapData;
    }

    @Override
    public TreeMap<String, Double> getStatisticWeightOfUser(String username) {
        TreeMap<String, Double> mapData = new TreeMap<>();
        physicalExamRepository.statisticWeightByYearOfUser(username).forEach(obj -> mapData.put(obj.getName().toString(), obj.getValue()));
        return mapData;
    }

}
