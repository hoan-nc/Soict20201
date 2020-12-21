package application.service.impl;

import application.entity.*;
import application.entity.id.UserRoleId;
import application.repository.*;
import application.service.AdminService;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
        Sort mSort = Sort.by(Sort.Order.asc("user.fullName"));
        return userRoleRepository.findAll(mSort);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUserPermission(Long userId) {
        UserRoleEntity userRoleEntity = userRoleRepository.findUserRoleOfUser(userId)
                .orElseThrow(() -> new IllegalArgumentException("Not found by id " + userId));
        userRoleRepository.delete(userRoleEntity);
        iUserRepository.deleteById(userId);
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
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdatePhysicalExam(PhysicalExamEntity physicalExamEntity) {
        UserEntity userEntity = iUserRepository.findById(physicalExamEntity.getUser().getId())
                .orElseThrow(() ->
                        new IllegalArgumentException("Not found user by id " + physicalExamEntity.getUser().getId()));
        ExaminationEntity examinationEntity = examinationRepository.findById(physicalExamEntity.getExamination().getId())
                .orElseThrow(() ->
                        new IllegalArgumentException("Not found examination by id " + physicalExamEntity.getExamination().getId()));
        DepartmentExamEntity departmentExamEntity = departmentExamRepository.findById(physicalExamEntity.getDepartmentExam().getId())
                .orElseThrow(() ->
                        new IllegalArgumentException("Not found department exam by id " + physicalExamEntity.getDepartmentExam().getId()));

        physicalExamEntity.setUser(userEntity);
        physicalExamEntity.setYear(examinationEntity.getYear());
        physicalExamEntity.setNameUser(userEntity.getFullName());
        physicalExamEntity.setExamination(examinationEntity);
        physicalExamEntity.setDepartmentExam(departmentExamEntity);

        physicalExamRepository.save(physicalExamEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deletePhysicalExam(PhysicalExamEntity entity) {
        physicalExamRepository.delete(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
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
    @Transactional(rollbackFor = Exception.class)
    public void saveUpdateUserRole(UserRoleEntity userRoleEntityCreated) {
        UserRoleEntity userRoleEntityDelete = userRoleRepository.findUserRoleOfUser(userRoleEntityCreated.getId().getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Not found by user id " + userRoleEntityCreated.getId().getUserId()));
        userRoleRepository.delete(userRoleEntityDelete);

        UserEntity userEntity = iUserRepository.findById(userRoleEntityCreated.getId().getUserId()).get();
        RoleEntity roleEntity = roleRepository.findById(userRoleEntityCreated.getId().getRoleId()).get();
        userRoleEntityCreated.setUser(userEntity);
        userRoleEntityCreated.setRole(roleEntity);
        userRoleEntityCreated.setStatus(true);

        userRoleRepository.save(userRoleEntityCreated);
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
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdateExamination(ExaminationEntity examinationEntity) {
        examinationRepository.save(examinationEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteExamination(Long examinationId) {
        examinationRepository.deleteById(examinationId);
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
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdateDepartmentExam(DepartmentExamEntity departmentExamEntity) {
        departmentExamRepository.save(departmentExamEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDepartmentExam(Long departmentExamId) {
        departmentExamRepository.deleteById(departmentExamId);
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

    @Override
    public long getTotalQuantityUsers() {
        return userRoleRepository.count();
    }

    @Override
    public long getTotalQuantityPhysicalExam() {
        return physicalExamRepository.count();
    }

    @Override
    public long getTotalQuantityDepartmentExam() {
        return departmentExamRepository.count();
    }

    @Override
    public long getTotalQuantityExamination() {
        return examinationRepository.count();
    }

}
