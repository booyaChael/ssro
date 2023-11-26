package issro.issro.service;

import issro.issro.domain.Project;
import issro.issro.domain.ProjectTag;
import issro.issro.domain.Tag;
import issro.issro.repository.ProjectTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectTagService {

  private final ProjectTagRepository projectTagRepository;

  @Transactional
  public void createProjectTagAndSave(List<Tag> tags, Project project) {
    List<ProjectTag> projectTagList = tags.stream()
            .map(tag -> ProjectTag.createProjectTag(project, tag))
            .toList();
    projectTagRepository.saveAll(projectTagList);
  }







}
