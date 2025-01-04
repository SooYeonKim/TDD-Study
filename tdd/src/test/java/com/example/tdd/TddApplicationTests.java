package com.example.tdd;

import com.example.tdd.model.StudentScoreFixture;
import com.example.tdd.repository.StudentScoreRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

class TddApplicationTests extends IntegrationTest {
	@Autowired
	private StudentScoreRepository studentScoreRepository;

	@Autowired
	private EntityManager entityManager;

	@Test
	void contextLoads() {
		var studentScore = StudentScoreFixture.passed();
		var savedStudentScore = studentScoreRepository.save(studentScore);

		entityManager.flush();
		entityManager.clear();

		var queryStudentScore = studentScoreRepository.findById(savedStudentScore.getId()).orElseThrow();

		Assertions.assertEquals(savedStudentScore.getId(), queryStudentScore.getId());
		Assertions.assertEquals(savedStudentScore.getStudentName(), queryStudentScore.getStudentName());
		Assertions.assertEquals(savedStudentScore.getMathScore(), queryStudentScore.getMathScore());
		Assertions.assertEquals(savedStudentScore.getEnglishScore(), queryStudentScore.getEnglishScore());
		Assertions.assertEquals(savedStudentScore.getKorScore(), queryStudentScore.getKorScore());
	}

}
