package com.example.test.repository;

import com.example.test.model.StudentPass;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentPassRepository extends JpaRepository<StudentPass, Long> {
}
