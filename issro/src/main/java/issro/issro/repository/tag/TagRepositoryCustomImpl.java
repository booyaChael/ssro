package issro.issro.repository.tag;

import com.querydsl.jpa.impl.JPAQueryFactory;
import issro.issro.domain.Tag;
import issro.issro.domain.Teacher;
import jakarta.persistence.EntityManager;

import java.util.List;

import static issro.issro.domain.QTag.tag;
import static issro.issro.domain.QTeacher.teacher;

public class TagRepositoryCustomImpl implements TagRepositoryCustom{

  private final JPAQueryFactory queryFactory;

  public TagRepositoryCustomImpl(EntityManager em) {
    queryFactory = new JPAQueryFactory(em);
  }

  @Override
  public List<Tag> findTagByTeacher(Teacher findTeacher) {
    return queryFactory
            .selectFrom(tag)
            .join(tag.teacher, teacher).fetchJoin()
            .where(teacher.id.eq(findTeacher.getId()))
            .fetch();
  }

  @Override
  public Tag findTagByTeacherAndTagName(Teacher findTeacher, String tagName) {
    return queryFactory
            .selectFrom(tag)
            .join(tag.teacher, teacher).fetchJoin()
            .where(teacher.id.eq(findTeacher.getId()), tag.tagName.eq(tagName))
            .fetchOne();

  }


}
