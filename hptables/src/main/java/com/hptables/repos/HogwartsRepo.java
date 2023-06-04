package com.hptables.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hptables.model.HogwartsClass;

public interface HogwartsRepo extends JpaRepository<HogwartsClass, Long> {
  
}
