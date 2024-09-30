package fall24.swp391.KoiOrderingSystem.repository;

import fall24.swp391.KoiOrderingSystem.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<User,Long> {
}
