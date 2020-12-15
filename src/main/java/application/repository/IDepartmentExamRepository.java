package application.repository;

import application.entity.DepartmentExamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDepartmentExamRepository extends JpaRepository<DepartmentExamEntity, Long> {

}