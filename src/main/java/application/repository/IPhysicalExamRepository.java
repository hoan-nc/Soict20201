package application.repository;

import application.domain.ChartCommon;
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


    @Query(value = "SELECT new application.domain.ChartCommon(p.year, avg (p.height)) " +
            "FROM PhysicalExamEntity p group by p.year order by p.year asc ")
    List<ChartCommon> statisticHeightByYear();

    @Query(value = "SELECT new application.domain.ChartCommon(p.year, avg (p.weight)) " +
            "FROM PhysicalExamEntity p group by p.year order by p.year asc ")
    List<ChartCommon> statisticWeightByYear();


    @Query(value = "SELECT new application.domain.ChartCommon(p.year, avg (p.height)) " +
            "FROM PhysicalExamEntity p where p.user.userName = :username " +
            "group by p.year order by p.year asc ")
    List<ChartCommon> statisticHeightByYearOfUser(@Param("username") String username);

    @Query(value = "SELECT new application.domain.ChartCommon(p.year, avg (p.weight)) " +
            "FROM PhysicalExamEntity p where p.user.userName = :username " +
            "group by p.year order by p.year asc ")
    List<ChartCommon> statisticWeightByYearOfUser(@Param("username") String username);

}
