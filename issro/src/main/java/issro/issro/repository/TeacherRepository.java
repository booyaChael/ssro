package issro.issro.repository;

import issro.issro.domain.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
  Teacher findByEmail(String email);
  Teacher findByNickname(String nickname);
}
