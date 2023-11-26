package issro.issro.controller;

import issro.issro.domain.Tag;
import issro.issro.domain.Teacher;
import issro.issro.dto.ResponseData;
import issro.issro.dto.tag.TagDTO;
import issro.issro.repository.TeacherRepository;
import issro.issro.repository.tag.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static issro.issro.service.TeacherService.checkTeacherRole;

@RestController
@RequiredArgsConstructor
public class TagController {

  private final TeacherRepository teacherRepository;
  private final TagRepository tagRepository;

  // 선생님이 들고 있는 태그 리스트 반환
  @GetMapping("/tag")
  public ResponseData<?> getTags(Authentication authentication) {
    if (authentication == null || !checkTeacherRole(authentication.getAuthorities())) {
      return new ResponseData<>(HttpStatus.BAD_REQUEST, null);
    }
    Teacher teacher = teacherRepository.findByEmail(authentication.getName());
    List<Tag> tagList = tagRepository.findTagByTeacher(teacher);
    List<TagDTO> tagDTOList = tagList.stream()
            .map(tag -> new TagDTO(tag.getId(), tag.getTagName()))
            .toList();
    return new ResponseData<List<TagDTO>>(HttpStatus.OK, tagDTOList);
  };

}
