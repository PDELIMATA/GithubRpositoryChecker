package com.delimata.githubrepositorychecker;

import com.delimata.githubrepositorychecker.Model.Branch;
import com.delimata.githubrepositorychecker.Model.Repositories;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class GithubRepositoryService {
    private static final String GITHUB_API_URL = "https://api.github.com";
    private final RestTemplate restTemplate;
    private final HttpEntity<Void> httpEntity;

    public GithubRepositoryService() {
        this.restTemplate = new RestTemplate();
        this.httpEntity = new HttpEntity<>(new HttpHeaders());
    }

    public ResponseEntity<?> fetchDataFromApi(String username) {

        String url = GITHUB_API_URL + "/users/" + username + "/repos";

        ResponseEntity<Repositories[]> exchange = restTemplate.exchange(
                url,
                HttpMethod.GET,
                httpEntity,
                Repositories[].class
        );

        if (exchange.getStatusCode() == HttpStatus.NOT_FOUND) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {

            Repositories[] repositories = exchange.getBody();
            if (repositories == null || repositories.length == 0) {
                return ResponseEntity.ok(repositories);
            }

            String branchesUrl = GITHUB_API_URL + "/repos/" + username + "/";
            Arrays.stream(repositories)
                    .filter(repo -> !repo.isFork())
                    .forEach(repo -> {
                        String urlWithUsernameAndRepo = branchesUrl + repo.getName() + "/branches";
                        ResponseEntity<Branch[]> response = restTemplate.exchange(
                                urlWithUsernameAndRepo,
                                HttpMethod.GET,
                                httpEntity,
                                Branch[].class
                        );
                        repo.setBranches(response.getBody());
                    });

            return ResponseEntity.ok().body(repositories);
        }
    }



}