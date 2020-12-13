package application.repository;

import application.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRoleRepository extends JpaRepository<RoleEntity, Long> {

    @Query("SELECT r FROM RoleEntity r " +
            "inner join UserRoleEntity ur on r.id = ur.id.roleId and ur.status = true " +
            "inner join UserEntity u on ur.id.userId = u.id and u.id = :userId ")
    List<RoleEntity> findAllRoleOfUser(@Param("userId") Long userId);

}
