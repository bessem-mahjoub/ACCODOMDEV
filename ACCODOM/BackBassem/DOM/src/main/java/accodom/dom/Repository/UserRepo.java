package accodom.dom.Repository;



import accodom.dom.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByEmail(String username);

    Boolean existsByEmail(String email);


}
