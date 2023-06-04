package com.hptables.services.implementations;

import java.util.Collection;

import org.springframework.stereotype.Service;

import com.hptables.model.Student;
import com.hptables.repos.StudentRepo;
import com.hptables.services.StudentService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class StudentServiceImpl implements StudentService{
  private final StudentRepo studentRepo;

  public Collection<Student> list() {
    log.info("Fetching all students");
    return studentRepo.findAll();
  }
}

