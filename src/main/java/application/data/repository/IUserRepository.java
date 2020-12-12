package application.data.repository;

import application.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {

    List<User> findByUserName(@Param("userName") String userName);

    @Query("select u from User u where 1 = 1")
    List<User> getAll();
}
