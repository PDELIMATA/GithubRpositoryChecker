package com.delimata.githubrepositorychecker;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class GithubRepositoryController {

    private final GithubRepositoryService githubRepositoryService;

    public GithubRepositoryController(GithubRepositoryService githubRepositoryService) {
        this.githubRepositoryService = githubRepositoryService;
    }

    @GetMapping(value = "/repositories/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getGithubRepositories(@PathVariable String username, @RequestHeader("Accept") String acceptHeader) {
        if (!acceptHeader.equalsIgnoreCase(MediaType.APPLICATION_JSON_VALUE)) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }

        return githubRepositoryService.fetchDataFromApi(username);
    }
}
