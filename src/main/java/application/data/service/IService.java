package application.data.service;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IService {

    JpaRepository getRepository();
}
