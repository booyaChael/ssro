package issro.issro.repository.student;

import com.querydsl.jpa.impl.JPAQueryFactory;
import issro.issro.domain.Student;
import issro.issro.domain.Teacher;
import jakarta.persistence.EntityManager;

import java.util.List;

import static issro.issro.domain.QStudent.*;
import static issro.issro.domain.QTeacher.*;
import static issro.issro.domain.QTeacherStudent.*;

public class StudentRepositoryCustomImpl implements StudentRepositoryCustom{

  private final JPAQueryFactory queryFactory;

  public StudentRepositoryCustomImpl(EntityManager em) {
    queryFactory = new JPAQueryFactory(em);
  }

  @Override
  public List<Student> findStudentsByTeacher(Teacher findTeacher) {
    return queryFactory
            .selectFrom(student)
            .join(student.studentTeachers, teacherStudent).fetchJoin()
            .join(teacherStudent.teacher, teacher).fetchJoin()
            .where(teacher.id.eq(findTeacher.getId()))
            .fetch();
  }
}
