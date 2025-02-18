package com.hackathon.inditex.controllers;

import com.hackathon.inditex.dto.CreateCenterData;
import com.hackathon.inditex.dto.MessageResponse;
import com.hackathon.inditex.entities.Center;
import com.hackathon.inditex.services.CenterService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class CenterController {

    private CenterService centerService;

    @PostMapping("/api/centers")
    public ResponseEntity<MessageResponse> createCenter(@RequestBody CreateCenterData data){
        log.info("Received Create Center Request.");
        centerService.createCenter(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("Logistics center created successfully."));
    }

    @GetMapping("/api/centers")
    public ResponseEntity<List<Center>> getAllCenters(){
        log.info("Received Get All Center Request.");
        return ResponseEntity.ok(centerService.getAllCenters());
    }

    @PatchMapping("api/centers/{id}")
    public ResponseEntity<MessageResponse> updateCenter(@PathVariable Long id, @RequestBody CreateCenterData data){
        log.info("Received Update Center Request.");
        centerService.updateCenter(id, data);
        return ResponseEntity.ok(new MessageResponse("Logistics center updated successfully."));
    }

    @DeleteMapping("api/centers/{id}")
    public ResponseEntity<MessageResponse> deleteCenter(@PathVariable Long id){
        log.info("Received Delete Center Request.");
        centerService.deleteCenter(id);
        return ResponseEntity.ok(new MessageResponse("Logistics center deleted successfully."));
    }

}
