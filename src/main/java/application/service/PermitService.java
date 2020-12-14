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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PermitService {
    private final IUserRepository iUserRepository;
    private final UserService userService;
    private final IRoleRepository roleRepository;
    private final IPhysicalExamRepository physicalExamRepository;
    private final IUserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

    public PermitService(IUserRepository iUserRepository,
                         UserService userService,
                         IRoleRepository roleRepository,
                         IPhysicalExamRepository physicalExamRepository,
                         IUserRoleRepository userRoleRepository,
                         PasswordEncoder passwordEncoder) {
        this.iUserRepository = iUserRepository;
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.physicalExamRepository = physicalExamRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Map<Long, List<RoleEntity>> getPermit() {
        Map<Long, List<RoleEntity>> mapRole = new HashMap<>();
        List<UserEntity> userEntities = iUserRepository.findAll();
        if (!CollectionUtils.isEmpty(userEntities)) {
            userEntities.forEach(userEntity -> {
                List<RoleEntity> roleEntities = userService.getListRoleActiveOfUser(userEntity.getId());
                mapRole.put(userEntity.getId(), roleEntities);
            });
        }
        return mapRole;
    }

    public List<RoleEntity> getAllRole() {
        return roleRepository.findAll();
    }

    public List<UserRoleEntity> getAllUserRoles() {
        return userRoleRepository.findAll();
    }

    public List<PhysicalExamEntity> getAllPhysicalExam() {
        return physicalExamRepository.findAll();
    }

    public PhysicalExamEntity findPhysicalExamById(Long id) {
        return physicalExamRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Not found by id " + id));
    }

    public void savePhysicalExam(PhysicalExamEntity examEntity) {
        physicalExamRepository.save(examEntity);
    }

    public void deletePhysicalExam(PhysicalExamEntity entity) {
        physicalExamRepository.delete(entity);
    }

    public void saveNewUserRole(UserRoleEntity userRoleEntity) {
        UserEntity userEntity = userRoleEntity.getUser();
        userEntity.setId(null);
        userEntity.setPasswordHash(passwordEncoder.encode(userEntity.getPassword()));
        UserEntity userSaved = iUserRepository.save(userEntity);

        RoleEntity roleEntity = roleRepository.findById(userRoleEntity.getRole().getId()).get();

        userRoleEntity.setUser(userSaved);
        userRoleEntity.setRole(roleEntity);
        userRoleEntity.setStatus(true);
        userRoleEntity.setId(UserRoleId.builder().userId(userSaved.getId()).roleId(roleEntity.getId()).build());

        userRoleRepository.save(userRoleEntity);
    }

    public void saveUpdateUserRole(UserRoleEntity input) {
        Optional<UserRoleEntity> optional = userRoleRepository.findUserRoleOfUser(input.getId().getUserId());
//        userRoleRepository.save(userRoleEntity);
    }
}
