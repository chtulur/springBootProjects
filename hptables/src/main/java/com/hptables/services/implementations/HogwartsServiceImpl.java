package com.hptables.services.implementations;

import java.util.Collection;

import org.springframework.stereotype.Service;

import com.hptables.model.HogwartsClass;
import com.hptables.repos.HogwartsRepo;
import com.hptables.services.HogwartsService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class HogwartsServiceImpl implements HogwartsService{
  private final HogwartsRepo hogwartsRepo;

  public Collection<HogwartsClass> list() {
    log.info("Fetching all classes");
    return hogwartsRepo.findAll();
  }
}
