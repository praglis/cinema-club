package com.misernandfriends.cinemaclub.controller;

import com.misernandfriends.cinemaclub.serviceInterface.config.ImportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RequestMapping("import")
public class ImportController {

    private final ImportService importService;

    public ImportController(ImportService importService) {
        this.importService = importService;
    }

    @PostMapping("/loadTestPreferences")
    public ResponseEntity<Object> loadTestPreferences(@RequestParam(value = "file") String filePath) throws IOException {
        importService.importPreferences(new File(filePath), new File(new File(filePath).getParent(), "user"));
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/loadTestUsers")
    public ResponseEntity<Object> loadTestUsers(@RequestParam(value = "file") String filePath) throws IOException {
        importService.importUsers(new File(filePath));
        return ResponseEntity.noContent().build();
    }
}
