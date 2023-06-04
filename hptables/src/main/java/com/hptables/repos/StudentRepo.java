package com.hptables.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hptables.model.Student;

public interface StudentRepo extends JpaRepository<Student, Long> {
  
}