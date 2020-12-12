package application.data.repository;

import application.data.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserRoleRepository extends JpaRepository<UserRole, Long> {

    @Query("select u from UserRole u where u.userId = :id")
    List<UserRole> findRolesOfUser(@Param("id") Integer userId);
 }
