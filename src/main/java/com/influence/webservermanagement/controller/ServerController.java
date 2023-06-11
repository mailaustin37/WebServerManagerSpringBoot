package com.influence.webservermanagement.controller;

import com.influence.webservermanagement.model.MyResponse;
import com.influence.webservermanagement.model.Server;
import com.influence.webservermanagement.service.ServerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.influence.webservermanagement.model.Status.SERVER_UP;
import static java.time.LocalDateTime.now;

@Slf4j
@RestController
@RequestMapping("api/v1/server/")
@RequiredArgsConstructor
public class ServerController {
    private final ServerService serverService;

    @GetMapping("/all")
    public ResponseEntity<MyResponse> getServers() throws InterruptedException {
        TimeUnit.SECONDS.sleep(3);//sleep for 3 seconds
        log.info("ServerController::gerServers executing");
        ResponseEntity<MyResponse> responseEntity = ResponseEntity.ok(
                MyResponse.builder()
                        .timeStamp(now())
                        .data(Map.of("servers", serverService.getServers(20)))
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .message("Servers retrieved")
                        .build()
        );
        log.info("ServerController::gerServers executed.");
       return responseEntity;
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<MyResponse> getServer(@PathVariable Long id){
        return ResponseEntity.ok(
                MyResponse.builder()
                        .timeStamp(now())
                        .data(Map.of("server",serverService.getServer(id)))
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .message("Server retrieved")
                        .build()
        );
    }

    @GetMapping("/ping/{ipAddress}")
    public ResponseEntity<MyResponse> ping(@PathVariable String ipAddress) throws IOException {
        Server server = serverService.ping(ipAddress);
        return ResponseEntity.ok(
                MyResponse.builder()
                        .timeStamp(now())
                        .data(Map.of("server",server))
                        .message(server.getStatus() == SERVER_UP ? "Pinged Successfully" : "Pinging Failed")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    @PostMapping("/save")
    public ResponseEntity<MyResponse> save(@RequestBody @Valid Server server) {
        return ResponseEntity.ok(
                MyResponse.builder()
                        .timeStamp(now())
                        .data(Map.of("server",serverService.create(server)))
                        .status(HttpStatus.CREATED)
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Server successfully created")
                        .build()
        );
    }

    @DeleteMapping ("/delete/{id}")
    public ResponseEntity<MyResponse> deleteServer(@PathVariable Long id){
        return ResponseEntity.ok(
                MyResponse.builder()
                        .timeStamp(now())
                        .data(Map.of("deleted",serverService.delete(id)))
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .message("Server deleted successfully")
                        .build()
        );
    }

    @PutMapping("/update")
    public ResponseEntity<MyResponse> update(@RequestBody @Valid Server server) {
        return ResponseEntity.ok(
                MyResponse.builder()
                        .timeStamp(now())
                        .data(Map.of("server",serverService.update(server)))
                        .status(HttpStatus.CREATED)
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Server has been updated")
                        .build()
        );
    }

}