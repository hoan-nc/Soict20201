package application.repository;

import application.entity.UserRoleEntity;
import application.entity.id.UserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserRoleRepository extends JpaRepository<UserRoleEntity, UserRoleId> {

    @Query("select u from UserRoleEntity u where u.status = true and u.id.userId = :id")
    List<UserRoleEntity> findUserRoleOfUser(@Param("id") Long userId);
}
