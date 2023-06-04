package com.servermanagerfromscratch.service.implementation;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.Random;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import static com.servermanagerfromscratch.enumeration.Status.SERVER_UP;
import static com.servermanagerfromscratch.enumeration.Status.SERVER_DOWN;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.servermanagerfromscratch.model.Server;
import com.servermanagerfromscratch.repo.ServerRepo;
import com.servermanagerfromscratch.service.ServerService;

@RequiredArgsConstructor //it will create a constructor and add serverRepo to it, completing my dependency injection
@Service //marks it as a bean, its life cycle will be handled by the Spring Container. Other than that, the name 'Service' only has semantic value for better organization
@Transactional //groups sql operations into one. Offers reroll even. Essential for SQL operations
@Slf4j //provides a logger for me
public class ServerServiceImplementation implements ServerService {
  private final ServerRepo serverRepo; //dependency injection, we get a bunch of methods
  //such as save() delete() etc. Transforms my shit to SQL.

  @Override
  public Server create(Server server) {
    log.info("Saving new server: {}", server.getName());
    server.setImageUrl(setServerImageUrl());
    return serverRepo.save(server); //Again, looks cryptic but it's the JPA again doing magic on the ACTUAL database!
  }

  public Server ping(String ipAddress) {
    log.info("Pinging server IP: {}", ipAddress);
    Server server = serverRepo.findByIpAddress(ipAddress);
    try {
      InetAddress address = InetAddress.getByName(ipAddress);
      //InetAddress is a class that represents an IP address.
      boolean reachable = address.isReachable(10_000);
      server.setStatus(reachable ? SERVER_UP : SERVER_DOWN);
      serverRepo.save(server);
  } catch (UnknownHostException e) {
      log.error("Unable to resolve host: {}", ipAddress);
  } catch (IOException e) {
      log.error("I/O error occurred while pinging server: {}", ipAddress);
  }
    return server;
  }

  public Collection<Server> list(int limit) {
    log.info("Fetching all servers");
    return serverRepo.findAll(PageRequest.of(0, limit)).toList();
  }

  public Server get(Long id) {
    log.info("Fetching server by id: {}", id);
    return serverRepo.findById(id).get();
  }

  public Server update(Server server) {
    log.info("Updating server: {}", server.getName());
    return serverRepo.save(server); //JPA is smart and know if it needs to update or create a new server
  }

  public Boolean delete(Long id) {
    log.info("Deleting server by id: {}", id);
    serverRepo.deleteById(id);
    return true;
  }

  private String setServerImageUrl() {
    String[] imageNames = {
      "server1.png", 
      "server2.png", 
      "server3.png",
      "server4.png",
      "server5.png",
      "server6.png", 
      "server7.png",
      "server8.png"
    };
    int randomIndex = new Random().nextInt(8);
    
    return ServletUriComponentsBuilder.fromCurrentContextPath()
      .path("/server/image/" + imageNames[randomIndex])
      .toUriString();

      /**
       * ServletUriComponentsBuilder.fromCurrentContextPath()
       * returns the context path, which is the base URL.
       * path() appends something extra to it
       * toUriString() converts URL to string
       */
  }
}
