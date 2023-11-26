package issro.issro.service;

import issro.issro.domain.Tag;
import issro.issro.domain.Teacher;
import issro.issro.dto.project.ProjectByTeacherDTO;
import issro.issro.dto.project.RequestProjectCreateDTO;
import issro.issro.repository.tag.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TagService {

  private final TagRepository tagRepository;

  @Transactional
  public List<Tag> createTagsByProjectByTeacherDTO(RequestProjectCreateDTO projectDTO, Teacher teacher) {
    List<String> tagNames = projectDTO.getTagNames();
    List<Tag> resultTagList = new ArrayList<>();
    List<Tag> savedTagList = new ArrayList<>();
    for (String tagName : tagNames) {
      Tag tag = tagRepository.findTagByTeacherAndTagName(teacher, tagName);
      if (tag == null) {
        Tag newTag = Tag.createTag(tagName, teacher);
        savedTagList.add(newTag);
        resultTagList.add(newTag);
      } else {
        resultTagList.add(tag);
      }
    }
    tagRepository.saveAll(savedTagList);
    return resultTagList;
  }

//  @Transactional
//  public List<Tag> createTags(Teacher teacher, List<String> addTagNames) {
//    if (addTagNames == null) {
//      return new ArrayList<Tag>();
//    }
//
//    List<Tag> tagList = addTagNames.stream()
//            .filter(tagName -> tagRepository.findTagByTeacherAndTagName(teacher, tagName) == null)
//            .map(tagName -> Tag.createTag(tagName, teacher)
//                )
//            .toList();
//    return tagList.size() == 0 ? new ArrayList<Tag>() : tagRepository.saveAll(tagList);
//  }
//
//  public List<Tag> findAllById(List<Long> tagIds) {
//    return tagRepository.findAllById(tagIds);
//  }
}
