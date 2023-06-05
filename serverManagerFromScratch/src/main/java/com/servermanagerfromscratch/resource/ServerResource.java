package com.servermanagerfromscratch.resource;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.CREATED;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import static java.time.LocalDateTime.now;

import lombok.RequiredArgsConstructor;
import com.servermanagerfromscratch.service.implementation.ServerServiceImplementation;
import com.servermanagerfromscratch.model.Response;
import com.servermanagerfromscratch.model.Server;

import static com.servermanagerfromscratch.enumeration.Status.SERVER_UP;

import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

@RestController
@RequestMapping("/server")
@RequiredArgsConstructor //required because final fields in the constructor should be required. Same thing as in ServerServiceImplementation.
public class ServerResource {
  private final ServerServiceImplementation serverService;

  @GetMapping("/list")
  public ResponseEntity<Response> getServers() throws InterruptedException {
    return ResponseEntity.ok(
      Response.builder()
        .timeStamp(now())
        .data(Map.of("servers", serverService.list(30)))
        .message("Servers retreived")
        .status(OK)
        .statusCode(OK.value())
        .build()
    );
  }

  @GetMapping("/ping/{ipAddress}")
  public ResponseEntity<Response> pingServer(@PathVariable("ipAddress") String ipAddress) throws IOException {
    Server server = serverService.ping(ipAddress);

    return ResponseEntity.ok(
      Response.builder()
        .timeStamp(now())
        .data(Map.of("server", server))
        .message(server.getStatus() == SERVER_UP ? "Ping success": "Ping failed")
        .status(OK)
        .statusCode(OK.value())
        .build()
    );
  }

  @PostMapping("/save")
  public ResponseEntity<Response> saveServer(@RequestBody @Valid Server server) {
    //we grab the body of the request which is going to be the server and check the validation we added
    return ResponseEntity.ok(
      Response.builder()
        .timeStamp(now())
        .data(Map.of("server", serverService.create(server)))
        .message("Server created")
        .status(CREATED)
        .statusCode(CREATED.value())
        .build()
    );
  }

  @GetMapping("/get/{id}")
  public ResponseEntity<Response> getServer(@PathVariable("id") Long id) {
    return ResponseEntity.ok(
      Response.builder()
        .timeStamp(now())
        .data(Map.of("server", serverService.get(id)))
        .message("Server retrieved")
        .status(OK)
        .statusCode(OK.value())
        .build()
    );
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<Response> deleteServer(@PathVariable("id") Long id) {
    return ResponseEntity.ok(
      Response.builder()
        .timeStamp(now())
        .data(Map.of("deleted", serverService.delete(id)))
        .message("Server deleted")
        .status(OK)
        .statusCode(OK.value())
        .build()
    );
  }

  @GetMapping(path = "/image/{imgName}", produces = IMAGE_PNG_VALUE)// with this we specify that we are not returning a json but a png file. We have to explicitly say this because json is the default.
  public byte[] getServerImage(@PathVariable("imgName") String image) throws IOException {
    //we go to the location of the image and see if we can read the bytes of that image
    return Files.readAllBytes(Paths.get("Frontend/images/" + image));
  }
}
