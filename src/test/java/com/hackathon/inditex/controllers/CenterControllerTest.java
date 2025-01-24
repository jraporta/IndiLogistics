package com.hackathon.inditex.controllers;

import com.hackathon.inditex.dto.CreateCenterData;
import com.hackathon.inditex.dto.MessageResponse;
import com.hackathon.inditex.services.CenterService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class CenterControllerTest {

    @InjectMocks
    private CenterController centerController;

    @Mock
    private CenterService mockCenterService;

    @Test
    void createCenter_createsNewCenter(){
        CreateCenterData mockData = new CreateCenterData();

        String successMessage = "Logistics center created successfully.";
        ResponseEntity<MessageResponse> expectedResponse = ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new MessageResponse(successMessage));

        Mockito.doNothing().when(mockCenterService).createCenter(mockData);

        ResponseEntity<MessageResponse> actualResponse = centerController.createCenter(mockData);

        Assertions.assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        Assertions.assertEquals(expectedResponse.getBody(), actualResponse.getBody());
    }

}
