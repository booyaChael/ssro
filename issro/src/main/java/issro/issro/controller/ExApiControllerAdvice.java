package issro.issro.controller;

import com.auth0.jwt.exceptions.TokenExpiredException;
import issro.issro.error.DuplicationException;
import issro.issro.error.ErrorResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
public class ExApiControllerAdvice {

  @ExceptionHandler
  public ResponseEntity<ErrorResult> tokenExpiredException(TokenExpiredException e) {
    ErrorResult errorResult = new ErrorResult("다시 로그인해주세요", e.getMessage());
    return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler
  public ResponseEntity<ErrorResult> duplicationEx(DuplicationException e) {
    ErrorResult errorResult = new ErrorResult("이름이 중복입니다", e.getMessage());
    return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler
  public ResponseEntity<ErrorResult> sqlEx(SQLIntegrityConstraintViolationException e) {
    ErrorResult errorResult = new ErrorResult("아이디 또는 이름이 중복입니다", e.getMessage());
    return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler
  public ResponseEntity<ErrorResult> notBlankEx(MethodArgumentNotValidException e) {
    ErrorResult errorResult = new ErrorResult("빈칸이면 안됩니다.", e.getMessage());
    return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
  }

}
