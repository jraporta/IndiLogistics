package com.hackathon.inditex.Controllers;

import com.hackathon.inditex.DTO.CreateCenterData;
import com.hackathon.inditex.DTO.MessageResponse;
import com.hackathon.inditex.Entities.Center;
import com.hackathon.inditex.Services.CenterService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class CenterController {

    private CenterService centerService;

    @PostMapping("/api/centers")
    public ResponseEntity<MessageResponse> createCenter(@RequestBody CreateCenterData data){
        centerService.createCenter(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("Logistics center created successfully."));
    }

    @GetMapping("/api/centers")
    public ResponseEntity<List<Center>> getAllCenters(){
        return ResponseEntity.ok(centerService.getAllCenters());
    }

}
