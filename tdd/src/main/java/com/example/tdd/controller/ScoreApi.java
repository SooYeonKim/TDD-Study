package com.example.tdd.controller;

import com.example.tdd.controller.request.SaveExamScoreRequest;
import com.example.tdd.controller.response.ExamFailStudentResponse;
import com.example.tdd.controller.response.ExamPassStudentResponse;
import com.example.tdd.service.StudentScoreService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ScoreApi {
    private final StudentScoreService studentScoreService;

    @PutMapping("/exam/{exam}/score")
    public void save(
            @PathVariable("exam") String exam,
            @RequestBody SaveExamScoreRequest request
    ) {
        studentScoreService.saveScore(
                request.getStudentName(),
                exam,
                request.getKorScore(),
                request.getEnglishScore(),
                request.getMathScore()
        );
    }

    @GetMapping("/exam/{exam}/pass")
    public List<ExamPassStudentResponse> pass(@PathVariable("exam") String exam) {
        return studentScoreService.getPassStudentsList(exam);
    }

    @GetMapping("/exam/{exam}/fail")
    public List<ExamFailStudentResponse> fail(@PathVariable("exam") String exam) {
        return studentScoreService.getFailStudentsList(exam);
    }

}
