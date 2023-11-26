package issro.issro.repository.tag;

import issro.issro.domain.Tag;
import issro.issro.domain.Teacher;

import java.util.List;

public interface TagRepositoryCustom {

  List<Tag> findTagByTeacher(Teacher teacher);

  Tag findTagByTeacherAndTagName(Teacher teacher, String tagName);



}
