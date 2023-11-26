package issro.issro.repository;

import issro.issro.domain.TeacherStudent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherStudentRepository extends JpaRepository<TeacherStudent, Long> {
}
