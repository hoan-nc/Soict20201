package application.service.impl;

import application.entity.*;
import application.entity.id.UserRoleId;
import application.repository.*;
import application.service.AdminService;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
public class AdminServiceImpl implements AdminService {
    private final IUserRepository iUserRepository;
    private final UserServiceImpl userServiceImpl;
    private final IRoleRepository roleRepository;
    private final IPhysicalExamRepository physicalExamRepository;
    private final IUserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final IExaminationRepository examinationRepository;
    private final IDepartmentExamRepository departmentExamRepository;

    public AdminServiceImpl(IUserRepository iUserRepository,
                            UserServiceImpl userServiceImpl,
                            IRoleRepository roleRepository,
                            IPhysicalExamRepository physicalExamRepository,
                            IUserRoleRepository userRoleRepository,
                            PasswordEncoder passwordEncoder,
                            IExaminationRepository examinationRepository,
                            IDepartmentExamRepository departmentExamRepository) {
        this.iUserRepository = iUserRepository;
        this.userServiceImpl = userServiceImpl;
        this.roleRepository = roleRepository;
        this.physicalExamRepository = physicalExamRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
        this.examinationRepository = examinationRepository;
        this.departmentExamRepository = departmentExamRepository;
    }

    @Override
    public Map<Long, List<RoleEntity>> getPermit() {
        Map<Long, List<RoleEntity>> mapRole = new HashMap<>();
        List<UserEntity> userEntities = iUserRepository.findAll();
        if (!CollectionUtils.isEmpty(userEntities)) {
            userEntities.forEach(userEntity -> {
                List<RoleEntity> roleEntities = userServiceImpl.getListRoleActiveOfUser(userEntity.getId());
                mapRole.put(userEntity.getId(), roleEntities);
            });
        }
        return mapRole;
    }

    @Override
    public List<RoleEntity> getAllRole() {
        return roleRepository.findAll();
    }

    @Override
    public List<UserRoleEntity> getAllUserRoles() {
        return userRoleRepository.findAll();
    }

    @Override
    public List<PhysicalExamEntity> getAllPhysicalExam() {
        return physicalExamRepository.findAll();
    }

    @Override
    public PhysicalExamEntity findPhysicalExamById(Long id) {
        return physicalExamRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Not found by id " + id));
    }

    @Override
    public void savePhysicalExam(PhysicalExamEntity examEntity) {
        physicalExamRepository.save(examEntity);
    }

    @Override
    public void deletePhysicalExam(PhysicalExamEntity entity) {
        physicalExamRepository.delete(entity);
    }

    @Override
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

    @Override
    public void saveUpdateUserRole(UserRoleEntity input) {
        Optional<UserRoleEntity> optional = userRoleRepository.findUserRoleOfUser(input.getId().getUserId());
//        userRoleRepository.save(userRoleEntity);
    }

    //Examination
    @Override
    public List<ExaminationEntity> findAllExamination() {
        Sort mSort = Sort.by(Sort.Order.desc("id"));
        return examinationRepository.findAll(mSort);
    }

    @Override
    public ExaminationEntity findExaminationById(Long examinationId) {
        Optional<ExaminationEntity> optional = examinationRepository.findById(examinationId);
        if (!optional.isPresent()) {
            throw new RuntimeException("Không tìm thấy đợt khám");
        }
        return optional.get();
    }

    @Override
    public void saveOrUpdateExamination(ExaminationEntity examinationEntity) {
        examinationRepository.save(examinationEntity);
    }


    //Department Exam
    @Override
    public List<DepartmentExamEntity> findAllDepartmentExams() {
        Sort mSort = Sort.by(Sort.Order.desc("id"));
        return departmentExamRepository.findAll(mSort);
    }

    @Override
    public DepartmentExamEntity findDepartmentExamById(Long departmentExamId) {
        Optional<DepartmentExamEntity> optional = departmentExamRepository.findById(departmentExamId);
        if (!optional.isPresent()) {
            throw new RuntimeException("Không tìm thấy đơn vị khám");
        }
        return optional.get();
    }

    @Override
    public void saveOrUpdateDepartmentExam(DepartmentExamEntity departmentExamEntity) {
        departmentExamRepository.save(departmentExamEntity);
    }

    //Statistic
    @Override
    public TreeMap<String, Double> getStatisticHeight() {
        TreeMap<String, Double> mapData = new TreeMap<>();
        physicalExamRepository.statisticHeightByYear().forEach(obj -> mapData.put(obj.getName().toString(), obj.getValue()));
        return mapData;
    }

    @Override
    public TreeMap<String, Double> getStatisticWeight() {
        TreeMap<String, Double> mapData = new TreeMap<>();
        physicalExamRepository.statisticWeightByYear().forEach(obj -> mapData.put(obj.getName().toString(), obj.getValue()));
        return mapData;
    }

}
