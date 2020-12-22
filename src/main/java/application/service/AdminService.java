package application.service;

import application.entity.*;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public interface AdminService {

    Map<Long, List<RoleEntity>> getPermit();

    List<RoleEntity> getAllRole();

    List<UserRoleEntity> getAllUserRoles();

    void deleteUserPermission(Long userId);

    List<PhysicalExamEntity> getAllPhysicalExam();

    PhysicalExamEntity findPhysicalExamById(Long id);

    void saveOrUpdatePhysicalExam(PhysicalExamEntity physicalExamEntity);

    void deletePhysicalExam(PhysicalExamEntity entity);

    void saveNewUserRole(UserRoleEntity userRoleEntity);

    void saveUpdateUserRole(UserRoleEntity input);

    List<ExaminationEntity> findAllExamination();

    ExaminationEntity findExaminationById(Long examinationId);

    void saveOrUpdateExamination(ExaminationEntity examinationEntity);

    void deleteExamination(Long examinationId);

    List<DepartmentExamEntity> findAllDepartmentExams();

    DepartmentExamEntity findDepartmentExamById(Long departmentExamId);

    void saveOrUpdateDepartmentExam(DepartmentExamEntity departmentExamEntity);

    void deleteDepartmentExam(Long departmentExamId);

    TreeMap<String, Double> getStatisticHeight();

    TreeMap<String, Double> getStatisticWeight();

    TreeMap<String, Long> getStatisticHealthyType(long year);

    long getTotalQuantityUsers();

    long getTotalQuantityPhysicalExam();

    long getTotalQuantityDepartmentExam();

    long getTotalQuantityExamination();

}
