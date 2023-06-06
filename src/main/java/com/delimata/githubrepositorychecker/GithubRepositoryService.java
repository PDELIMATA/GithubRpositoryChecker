package com.delimata.githubrepositorychecker;

import com.delimata.githubrepositorychecker.Model.Branch;
import com.delimata.githubrepositorychecker.Model.Repositories;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class GithubRepositoryService {
    private static final String GITHUB_API_URL = "https://api.github.com";
    private final WebClient webClient;

    public GithubRepositoryService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<Repositories[]> fetchDataFromApi(String username) {
        String url = GITHUB_API_URL + "/users/" + username + "/repos";

        return webClient.get()
                .uri(url)
                .retrieve()
                .onStatus(HttpStatus.NOT_FOUND::equals, response -> Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .bodyToMono(Repositories[].class)
                .flatMap(repositories -> Mono.just(repositories)
                        .filter(repos -> repos.length > 0)
                        .flatMapMany(Flux::fromArray)
                        .filter(repo -> !repo.fork())
                        .flatMap(this::fetchBranchesForRepository)
                        .collectList()
                )
                .map(repositoriesList -> repositoriesList.toArray(new Repositories[0]));
    }

    private Mono<Repositories> fetchBranchesForRepository(Repositories repo) {
        String urlWithUsernameAndRepo = GITHUB_API_URL + "/repos/" + repo.owner().login() + "/" + repo.name() + "/branches";
        return webClient.get()
                .uri(urlWithUsernameAndRepo)
                .retrieve()
                .bodyToMono(Branch[].class)
                .map(branches -> new Repositories(repo.name(), repo.owner(), repo.fork(), branches));
    }
}
