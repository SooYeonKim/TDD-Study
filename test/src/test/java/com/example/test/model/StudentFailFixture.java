package com.example.test.model;

import com.example.test.MyCalculator;

public class StudentFailFixture {
    public static StudentFail create(StudentScore studentScore) {
        var calculator = new MyCalculator();
        return StudentFail
                .builder()
                .exam(studentScore.getExam())
                .studentName(studentScore.getStudentName())
                .avgScore(calculator
                        .add(studentScore.getKorScore().doubleValue())
                        .add(studentScore.getEnglishScore().doubleValue())
                        .add(studentScore.getMathScore().doubleValue())
                        .divide(3.0)
                        .getResult()
                )
                .build();
    }

    public static StudentFail create(String studentName, String exam) {
        return StudentFail
                .builder()
                .studentName(studentName)
                .exam(exam)
                .avgScore(40.0)
                .build();
    }
}
