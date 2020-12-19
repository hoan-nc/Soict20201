package application.service;

import application.entity.*;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public interface AdminService {

    Map<Long, List<RoleEntity>> getPermit();

    List<RoleEntity> getAllRole();

    List<UserRoleEntity> getAllUserRoles();

    List<PhysicalExamEntity> getAllPhysicalExam();

    PhysicalExamEntity findPhysicalExamById(Long id);

    void saveOrUpdatePhysicalExam(PhysicalExamEntity physicalExamEntity);

    void deletePhysicalExam(PhysicalExamEntity entity);

    void saveNewUserRole(UserRoleEntity userRoleEntity);

    void saveUpdateUserRole(UserRoleEntity input);

    List<ExaminationEntity> findAllExamination();

    ExaminationEntity findExaminationById(Long examinationId);

    void saveOrUpdateExamination(ExaminationEntity examinationEntity);

    List<DepartmentExamEntity> findAllDepartmentExams();

    DepartmentExamEntity findDepartmentExamById(Long departmentExamId);

    void saveOrUpdateDepartmentExam(DepartmentExamEntity departmentExamEntity);

    TreeMap<String, Double> getStatisticHeight();

    TreeMap<String, Double> getStatisticWeight();

}
