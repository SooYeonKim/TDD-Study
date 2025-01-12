package com.example.test.service;

import com.example.test.IntegrationTest;
import com.example.test.MyCalculator;
import com.example.test.model.StudentScoreFixture;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class StudentScoreServiceIntegrationTest extends IntegrationTest {
  @Autowired private StudentScoreService studentScoreService;

  @Autowired private EntityManager entityManager;

  @Test
  public void savePassedStudentScoreTest() {
    // given
    var studentScore = StudentScoreFixture.passed();

    // when
    studentScoreService.saveScore(
        studentScore.getStudentName(),
        studentScore.getExam(),
        studentScore.getKorScore(),
        studentScore.getEnglishScore(),
        studentScore.getMathScore());
    entityManager.flush();
    entityManager.clear();

    // then
    var passedStudentResponses = studentScoreService.getPassStudentsList(studentScore.getExam());

    Assertions.assertEquals(1, passedStudentResponses.size());

    var passedStudentResponse = passedStudentResponses.get(0);

    var calculator = new MyCalculator(0.0);

    Assertions.assertEquals(studentScore.getStudentName(), passedStudentResponse.getStudentName());
    Assertions.assertEquals(
        calculator
            .add(studentScore.getKorScore().doubleValue())
            .add(studentScore.getEnglishScore().doubleValue())
            .add(studentScore.getMathScore().doubleValue())
            .divide(3.0)
            .getResult(),
        passedStudentResponse.getAvgScore());
  }

  @Test
  public void saveFailedStudentScoreTest() {
    // given
    var studentScore = StudentScoreFixture.failed();

    // when
    studentScoreService.saveScore(
        studentScore.getStudentName(),
        studentScore.getExam(),
        studentScore.getKorScore(),
        studentScore.getEnglishScore(),
        studentScore.getMathScore());
    entityManager.flush();
    entityManager.clear();

    // then
    var failedStudentResponses = studentScoreService.getFailStudentsList(studentScore.getExam());

    Assertions.assertEquals(1, failedStudentResponses.size());

    var failedStudentResponse = failedStudentResponses.get(0);

    var calculator = new MyCalculator(0.0);

    Assertions.assertEquals(studentScore.getStudentName(), failedStudentResponse.getStudentName());
    Assertions.assertEquals(
        calculator
            .add(studentScore.getKorScore().doubleValue())
            .add(studentScore.getEnglishScore().doubleValue())
            .add(studentScore.getMathScore().doubleValue())
            .divide(3.0)
            .getResult(),
        failedStudentResponse.getAvgScore());
  }
}
