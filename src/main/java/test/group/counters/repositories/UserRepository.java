package test.group.counters.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import test.group.counters.models.UserModel;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel, Long> {
    Optional<UserModel> findByUsername(String username);
}