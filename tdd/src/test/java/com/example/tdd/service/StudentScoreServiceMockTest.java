package com.example.tdd.service;

import com.example.tdd.controller.response.ExamFailStudentResponse;
import com.example.tdd.controller.response.ExamPassStudentResponse;
import com.example.tdd.model.StudentFail;
import com.example.tdd.model.StudentPass;
import com.example.tdd.repository.StudentFailRepository;
import com.example.tdd.repository.StudentPassRepository;
import com.example.tdd.repository.StudentScoreRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

public class StudentScoreServiceMockTest {

    @Test
    @DisplayName("첫번째 Mock 테스트")
    public void firstSaveScoreMockTest() {
        // given
        StudentScoreService studentScoreService = new StudentScoreService(
                Mockito.mock(StudentScoreRepository.class),
                Mockito.mock(StudentPassRepository.class),
                Mockito.mock(StudentFailRepository.class)
        );
        String givenStudentName = "StudentA";
        String givenExam = "testexam";
        Integer givenKorScore = 80;
        Integer givenEnglishCore = 100;
        Integer givenMathScore = 60;

        // when
        studentScoreService.saveScore(
                givenStudentName,
                givenExam,
                givenKorScore,
                givenEnglishCore,
                givenMathScore
        );
    }

    @Test
    @DisplayName("성적 저장 로직 검증 / 60점 이상인 경우")
    public void saveScoreMockTest() {
        // given : 평균점수가 60점 이상인 경우
        StudentScoreRepository studentScoreRepository = Mockito.mock(StudentScoreRepository.class);
        StudentPassRepository studentPassRepository = Mockito.mock(StudentPassRepository.class);
        StudentFailRepository studentFailRepository = Mockito.mock(StudentFailRepository.class);

        StudentScoreService studentScoreService = new StudentScoreService(
                studentScoreRepository,
                studentPassRepository,
                studentFailRepository
        );
        String givenStudentName = "StudentA";
        String givenExam = "testexam";
        Integer givenKorScore = 80;
        Integer givenEnglishCore = 100;
        Integer givenMathScore = 60;

        // when
        studentScoreService.saveScore(
                givenStudentName,
                givenExam,
                givenKorScore,
                givenEnglishCore,
                givenMathScore
        );

        // then
        Mockito.verify(studentScoreRepository, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(studentPassRepository, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(studentFailRepository, Mockito.times(0)).save(Mockito.any());
    }

    @Test
    @DisplayName("성적 저장 로직 검증 / 60점 미만인 경우")
    public void saveScoreMockTest2() {
        // given : 평균점수가 60점 미만인 경우
        StudentScoreRepository studentScoreRepository = Mockito.mock(StudentScoreRepository.class);
        StudentPassRepository studentPassRepository = Mockito.mock(StudentPassRepository.class);
        StudentFailRepository studentFailRepository = Mockito.mock(StudentFailRepository.class);

        StudentScoreService studentScoreService = new StudentScoreService(
                studentScoreRepository,
                studentPassRepository,
                studentFailRepository
        );
        String givenStudentName = "StudentA";
        String givenExam = "testexam";
        Integer givenKorScore = 40;
        Integer givenEnglishCore = 60;
        Integer givenMathScore = 60;

        // when
        studentScoreService.saveScore(
                givenStudentName,
                givenExam,
                givenKorScore,
                givenEnglishCore,
                givenMathScore
        );

        // then
        Mockito.verify(studentScoreRepository, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(studentPassRepository, Mockito.times(0)).save(Mockito.any());
        Mockito.verify(studentFailRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    @DisplayName("합격자 명단 가져오기 검증")
    public void getPassStudentsListTest() {
        // given : 평균점수가 60점 이상인 경우
        StudentScoreRepository studentScoreRepository = Mockito.mock(StudentScoreRepository.class);
        StudentPassRepository studentPassRepository = Mockito.mock(StudentPassRepository.class);
        StudentFailRepository studentFailRepository = Mockito.mock(StudentFailRepository.class);

        StudentPass expectStudent1 = StudentPass.builder().id(1L).studentName("StudentA").exam("testexam").avgScore(70.0).build();
        StudentPass expectStudent2 = StudentPass.builder().id(2L).studentName("StudentB").exam("testexam").avgScore(80.0).build();
        StudentPass notExpectStudent3 = StudentPass.builder().id(3L).studentName("StudentC").exam("secondexam").avgScore(90.0).build();

        Mockito.when(studentPassRepository.findAll()).thenReturn(List.of(
                expectStudent1,
                expectStudent2,
                notExpectStudent3
        ));

        StudentScoreService studentScoreService = new StudentScoreService(
                studentScoreRepository,
                studentPassRepository,
                studentFailRepository
        );
        String givenTestExam = "testexam";

        // when
        var expectResponses = List.of(expectStudent1, expectStudent2)
                .stream()
                .map((pass) -> new ExamPassStudentResponse(pass.getStudentName(), pass.getAvgScore()))
                .toList();
        List<ExamPassStudentResponse> responses = studentScoreService.getPassStudentsList(givenTestExam);

        // then
        Assertions.assertIterableEquals(expectResponses, responses);
    }

    @Test
    @DisplayName("불합격자 명단 가져오기 검증")
    public void getFailStudentsListTest() {
        // given : 평균점수가 60점 미만인 경우
        StudentScoreRepository studentScoreRepository = Mockito.mock(StudentScoreRepository.class);
        StudentPassRepository studentPassRepository = Mockito.mock(StudentPassRepository.class);
        StudentFailRepository studentFailRepository = Mockito.mock(StudentFailRepository.class);

        StudentFail expectStudent1 = StudentFail.builder().id(1L).studentName("StudentA").exam("testexam").avgScore(50.0).build();
        StudentFail expectStudent2 = StudentFail.builder().id(2L).studentName("StudentB").exam("testexam").avgScore(45.0).build();
        StudentFail notExpectStudent3 = StudentFail.builder().id(3L).studentName("StudentC").exam("secondexam").avgScore(35.0).build();

        Mockito.when(studentFailRepository.findAll()).thenReturn(List.of(
                expectStudent1,
                expectStudent2,
                notExpectStudent3
        ));

        StudentScoreService studentScoreService = new StudentScoreService(
                studentScoreRepository,
                studentPassRepository,
                studentFailRepository
        );
        String givenTestExam = "testexam";

        // when
        var expectFailLisst = List.of(expectStudent1, expectStudent2)
                .stream()
                .map((fail) -> new ExamFailStudentResponse(fail.getStudentName(), fail.getAvgScore())
                ).toList();
        List<ExamFailStudentResponse> responses = studentScoreService.getFailStudentsList(givenTestExam);

        // then
        Assertions.assertIterableEquals(expectFailLisst, responses);
    }
}
