package issro.issro.repository.tag;

import issro.issro.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long>, TagRepositoryCustom {

}
