package application.repository;

import application.entity.PhysicalExamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPhysicalExamRepository extends JpaRepository<PhysicalExamEntity,Long> {

}
