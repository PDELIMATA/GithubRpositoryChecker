package com.delimata.githubrepositorychecker;

import com.delimata.githubrepositorychecker.Model.Repositories;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping("/api")
public class GithubRepositoryController {

    private final GithubRepositoryService githubRepositoryService;

    public GithubRepositoryController(GithubRepositoryService githubRepositoryService) {
        this.githubRepositoryService = githubRepositoryService;
    }

    @GetMapping(value = "/repositories/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Mono<Repositories[]> getGithubRepositories(@PathVariable String username) {
        return githubRepositoryService.fetchDataFromApi(username);
    }
}
