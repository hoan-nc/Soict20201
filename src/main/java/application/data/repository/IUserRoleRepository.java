package application.data.repository;

import application.data.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRoleRepository extends JpaRepository<UserRole, Long> {

    @Query("select u from UserRole u where u.userId = :id")
    Iterable<UserRole> findRolesOfUser(@Param("id") Integer userId);
 }
