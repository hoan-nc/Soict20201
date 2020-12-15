package application.service;

import application.entity.*;
import application.entity.id.UserRoleId;
import application.repository.*;
import org.springframework.data.domain.Sort;
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
    private final IExaminationRepository examinationRepository;

    public PermitService(IUserRepository iUserRepository,
                         UserService userService,
                         IRoleRepository roleRepository,
                         IPhysicalExamRepository physicalExamRepository,
                         IUserRoleRepository userRoleRepository,
                         PasswordEncoder passwordEncoder,
                         IExaminationRepository examinationRepository) {
        this.iUserRepository = iUserRepository;
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.physicalExamRepository = physicalExamRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
        this.examinationRepository = examinationRepository;
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

    //Examination
    public List<ExaminationEntity> findAllExamination() {
        Sort mSort = Sort.by(Sort.Order.desc("id"));
        return examinationRepository.findAll(mSort);
    }

    public ExaminationEntity findExaminationById(Long examinationId) {
        Optional<ExaminationEntity> optional = examinationRepository.findById(examinationId);
        if (!optional.isPresent()) {
            throw new RuntimeException("Không tìm thấy đợt khám");
        }
        return optional.get();
    }

    public void saveOrUpdateExamination(ExaminationEntity examinationEntity) {
        examinationRepository.save(examinationEntity);
    }


}
