package uz.ilmnajot.emailvertification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.ilmnajot.emailvertification.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsUserByEmail(String email);

    boolean existsUserBySendHashCode(String sendHashCode);

    Optional<User> findUserByEmail(String email);

    User findUserByConfirmCode(String confirmCode);
}
