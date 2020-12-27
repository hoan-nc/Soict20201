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

    @Query("SELECT count(a.id) from PhysicalExamEntity a where a.user.id = :userId ")
    long countAllByUserId(@Param("userId") Long userId);

    @Query("SELECT p FROM PhysicalExamEntity p where p.user.id = :userId ORDER BY p.id desc")
    List<PhysicalExamEntity> findAllByUserIdOrder(@Param("userId") Long userId);


    @Query(value = "SELECT new application.domain.ChartCommon(p.healthType, count (p.healthType)) " +
            "FROM PhysicalExamEntity p where p.year = :year " +
            "group by p.healthType ")
    List<ChartCommon> statisticHealthyTypeByYear(@Param("year") long year);

    @Query(value = "SELECT new application.domain.ChartCommon(p.year, count (p.nerve)) " +
            "FROM PhysicalExamEntity p where p.nerve = 'Bình thường' " +
            "group by p.year order by p.year asc ")
    List<ChartCommon> statisticNerveNormal();

    @Query(value = "SELECT new application.domain.ChartCommon(p.year, count (p.nerve)) " +
            "FROM PhysicalExamEntity p where p.nerve <> 'Bình thường' " +
            "group by p.year order by p.year asc ")
    List<ChartCommon> statisticNerveNotNormal();

    @Query(value = "SELECT new application.domain.ChartCommon(p.year, count (p.dermatology)) " +
            "FROM PhysicalExamEntity p where p.dermatology = 'Bình thường' " +
            "group by p.year order by p.year asc ")
    List<ChartCommon> statisticDermatologyNormal();

    @Query(value = "SELECT new application.domain.ChartCommon(p.year, count (p.dermatology)) " +
            "FROM PhysicalExamEntity p where p.dermatology <> 'Bình thường' " +
            "group by p.year order by p.year asc ")
    List<ChartCommon> statisticDermatologyNotNormal();

    @Query(value = "SELECT new application.domain.ChartCommon(p.year, count (p.insideMedical)) " +
            "FROM PhysicalExamEntity p where p.insideMedical = 'Bình thường' " +
            "group by p.year order by p.year asc ")
    List<ChartCommon> statisticInsideNormal();

    @Query(value = "SELECT new application.domain.ChartCommon(p.year, count (p.insideMedical)) " +
            "FROM PhysicalExamEntity p where p.insideMedical <> 'Bình thường' " +
            "group by p.year order by p.year asc ")
    List<ChartCommon> statisticInsideNotNormal();


    @Query(value = "SELECT new application.domain.ChartCommon(p.year, count (p.outsideMedical)) " +
            "FROM PhysicalExamEntity p where p.outsideMedical = 'Bình thường' " +
            "group by p.year order by p.year asc ")
    List<ChartCommon> statisticOutsideNormal();

    @Query(value = "SELECT new application.domain.ChartCommon(p.year, count (p.outsideMedical)) " +
            "FROM PhysicalExamEntity p where p.outsideMedical <> 'Bình thường' " +
            "group by p.year order by p.year asc ")
    List<ChartCommon> statisticOutsideNotNormal();

    @Query(value = "SELECT new application.domain.ChartCommon(p.year, count (p.earNoseThroat)) " +
            "FROM PhysicalExamEntity p where p.earNoseThroat = 'Bình thường' " +
            "group by p.year order by p.year asc ")
    List<ChartCommon> statisticEarNoseThroatNormal();

    @Query(value = "SELECT new application.domain.ChartCommon(p.year, count (p.earNoseThroat)) " +
            "FROM PhysicalExamEntity p where p.earNoseThroat <> 'Bình thường' " +
            "group by p.year order by p.year asc ")
    List<ChartCommon> statisticEarNoseThroatNotNormal();

    @Query(value = "SELECT new application.domain.ChartCommon(p.year, count (p.dentomaxillofacial)) " +
            "FROM PhysicalExamEntity p where p.dentomaxillofacial = 'Bình thường' " +
            "group by p.year order by p.year asc ")
    List<ChartCommon> statisticDentomaxiillofacialNormal();

    @Query(value = "SELECT new application.domain.ChartCommon(p.year, count (p.dentomaxillofacial)) " +
            "FROM PhysicalExamEntity p where p.dentomaxillofacial <> 'Bình thường' " +
            "group by p.year order by p.year asc ")
    List<ChartCommon> statisticDentomaxiillofacialNotNormal();

    @Query("SELECT count(u.year) " +
            "FROM PhysicalExamEntity u " +
            "where u.year = 2020 and upper(u.insideMedical) like :value ")
    Long getTotalCurrentInsideMedical(@Param("value") String value);

    @Query("SELECT count(u.year) " +
            "FROM PhysicalExamEntity u " +
            "where u.year = 2020")
    Long getTotalInsideMedicalInYear();

    @Query("SELECT count(u.year) " +
            "FROM PhysicalExamEntity u " +
            "where u.year = 2020 and upper(u.outsideMedical) like :value ")
    Long getTotalCurrentOutsideMedical(@Param("value") String value);

    @Query("SELECT count(u.year) " +
            "FROM PhysicalExamEntity u " +
            "where u.year = 2020")
    Long getTotalOutsideMedicalInYear();


    @Query("SELECT count(u.year) " +
            "FROM PhysicalExamEntity u " +
            "where u.year = 2020 and upper(u.earNoseThroat) like :value ")
    Long getTotalCurrentEarNoseThroat(@Param("value") String value);

    @Query("SELECT count(u.year) " +
            "FROM PhysicalExamEntity u " +
            "where u.year = 2020")
    Long getTotalEarNoseThroatInYear();

    @Query("SELECT count(u.year) " +
            "FROM PhysicalExamEntity u " +
            "where u.year = 2020 and upper(u.dentomaxillofacial) like :value ")
    Long getTotalCurrentDentomaxilloFacial(@Param("value") String value);

    @Query("SELECT count(u.year) " +
            "FROM PhysicalExamEntity u " +
            "where u.year = 2020")
    Long getTotalDentomaxilloFacialInYear();

}
