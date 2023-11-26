package issro.issro.repository.project;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import issro.issro.domain.Project;
import issro.issro.domain.Teacher;
import issro.issro.dto.project.ProjectByDeadlineMonth;
import issro.issro.dto.project.QProjectByDeadlineMonth;
import jakarta.persistence.EntityManager;

import java.time.LocalDate;
import java.util.List;

import static issro.issro.domain.QProject.project;

public class ProjectRepositoryCustomImpl implements ProjectRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  public ProjectRepositoryCustomImpl(EntityManager em) {
    this.queryFactory = new JPAQueryFactory(em);
  }

  @Override
  public List<Project> getProjectByDate(Teacher teacher, LocalDate date) {
    return queryFactory
            .selectFrom(project)
            .where(project.teacher.eq(teacher), project.startDate.before(date.plusDays(1)), project.endDate.after(date.minusDays(1)))
            .fetch();
  }

  @Override
  public List<ProjectByDeadlineMonth> getProjectByDeadlineMonth(Teacher teacher, Integer month) {
    return queryFactory
            .select(new QProjectByDeadlineMonth(project.id, project.endDate))
            .from(project)
            .where(project.teacher.eq(teacher), projectMonthEq(month))
            .fetch();
  }

  private BooleanExpression projectMonthEq(Integer month) {
    return month == null ? null : project.endDate.month().eq(month);
  }
}
