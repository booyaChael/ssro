package issro.issro.repository.project;

import issro.issro.domain.Project;
import issro.issro.domain.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long>, ProjectRepositoryCustom {
}
