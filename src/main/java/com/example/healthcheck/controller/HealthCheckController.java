package com.example.healthcheck.controller;

import com.example.healthcheck.entity.HealthCheck;
import com.example.healthcheck.repository.HealthCheckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
public class HealthCheckController {

    @Autowired
    private HealthCheckRepository healthCheckRepository;

    @GetMapping("/healthz")
    public ResponseEntity<Void> healthCheck(HttpServletRequest request) {
        try {
            // Check if request has query parameters
            if (request.getQueryString() != null && !request.getQueryString().isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .header("Cache-Control", "no-cache, no-store, must-revalidate")
                        .header("Pragma", "no-cache")
                        .header("X-Content-Type-Options", "nosniff")
                        .build();
            }

            // Check if request has a body/payload
            if (request.getContentLengthLong() > 0) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .header("Cache-Control", "no-cache, no-store, must-revalidate")
                        .header("Pragma", "no-cache")
                        .header("X-Content-Type-Options", "nosniff")
                        .build();
            }

            // Insert a new health check record
            HealthCheck healthCheck = new HealthCheck();
            healthCheckRepository.save(healthCheck);

            // Return 200 OK with no body and cache-control headers
            return ResponseEntity
                    .ok()
                    .header("Cache-Control", "no-cache, no-store, must-revalidate")
                    .header("Pragma", "no-cache")
                    .header("X-Content-Type-Options", "nosniff")
                    .build();

        } catch (Exception e) {
            // Return 503 Service Unavailable if database insert fails
            return ResponseEntity
                    .status(HttpStatus.SERVICE_UNAVAILABLE)
                    .header("Cache-Control", "no-cache, no-store, must-revalidate")
                    .header("Pragma", "no-cache")
                    .header("X-Content-Type-Options", "nosniff")
                    .build();
        }
    }

    // Handle non-GET methods - return 405 Method Not Allowed
    @RequestMapping(value = "/healthz", method = {RequestMethod.POST, RequestMethod.PUT,
            RequestMethod.DELETE, RequestMethod.PATCH})
    public ResponseEntity<Void> healthCheckMethodNotAllowed() {
        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .header("Cache-Control", "no-cache, no-store, must-revalidate")
                .header("Pragma", "no-cache")
                .header("X-Content-Type-Options", "nosniff")
                .build();
    }
}