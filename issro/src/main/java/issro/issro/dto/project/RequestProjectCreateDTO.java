package issro.issro.dto.project;

import issro.issro.domain.ProjectType;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class RequestProjectCreateDTO {

  //common
  private String projectCreator; // 교사 : nickName, 학생 name
  private String title;
  private String description;
  private ProjectType projectType;
  // createdDate : 오늘 날짜로 자체 처리하기

  //project
  private LocalDate startDate; //일회, 다회, 반복
  private LocalDate endDate; //일쇠, 다회, 반복
  private List<Integer> week; //반복 / 월수금[1,3,5]
  private int releaseDate; //반복 / n일 전 공개

  //studentProject
  private int maxCount; //다회성
  private List<Long> studentIds;

  //tag
  private List<String> tagNames;
}
