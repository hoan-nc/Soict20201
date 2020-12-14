package application.repository;

import application.entity.PhysicalExamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPhysicalExamRepository extends JpaRepository<PhysicalExamEntity, Long> {

    @Query("SELECT p FROM PhysicalExamEntity p where p.user.userName = :username ")
    List<PhysicalExamEntity> findAllByUser(@Param("username") String username);

}
