package com.servermanagerfromscratch.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.servermanagerfromscratch.model.Server;

public interface ServerRepo extends JpaRepository<Server, Long> {
  //this is a custom method I define
  Server findByIpAddress(String ipAddress); //jpn knows that after findBy it should look for the thing I typed in
  //Server findByName(String name) would also work if I wanted to find a server based on name. This will equal to an SQL query O.o wow
  //I can manipulate the database programatically from here too because of the JPA extension
  //It can convert java code to an SQL query and handles DB operations for me.
}
