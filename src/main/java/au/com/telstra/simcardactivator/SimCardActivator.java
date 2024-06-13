package au.com.telstra.simcardactivator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SimCardActivator {

    public static void main(String[] args) {
        SpringApplication.run(SimCardActivator.class, args);
    }
}
package com.example.simactivation;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/sim")
public class SimCardController {

    @PostMapping("/activate")
    public String activateSim(@RequestBody SimCardRequest request) {
        RestTemplate restTemplate = new RestTemplate();
        String actuatorUrl = "http://localhost:8444/actuate";
        
        HttpEntity<SimCardRequest> entity = new HttpEntity<>(request);
        ResponseEntity<ActuatorResponse> response = restTemplate.exchange(actuatorUrl, HttpMethod.POST, entity, ActuatorResponse.class);
        
        boolean success = response.getBody() != null && response.getBody().isSuccess();
        return success ? "Activation successful" : "Activation failed";
    }
}

class SimCardRequest {
    private String iccid;
    private String customerEmail;

}

class ActuatorResponse {
    private boolean success;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}

