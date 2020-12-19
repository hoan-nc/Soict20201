package application.repository;

import application.entity.UserRoleEntity;
import application.entity.id.UserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRoleRepository extends JpaRepository<UserRoleEntity, UserRoleId> {

    @Query("select u from UserRoleEntity u where u.id.userId = :id")
    Optional<UserRoleEntity> findUserRoleOfUser(@Param("id") Long userId);

    @Query("delete from UserRoleEntity ur where ur.user.id = :id")
    void deleteUserRoleByUserId(@Param("id") Long userId);

}
