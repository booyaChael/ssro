package issro.issro.service;

import issro.issro.repository.common.CommonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommonService {
  private final CommonRepository commonRepository;



}
