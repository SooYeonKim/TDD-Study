package com.example.tdd.repository;

import com.example.tdd.model.StudentFail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentFailRepository extends JpaRepository<StudentFail, Long> {
}
