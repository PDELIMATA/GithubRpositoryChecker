package com.delimata.githubrepositorychecker.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public record Repositories(String name, Owner owner, @JsonIgnore boolean fork, Branch[] branches) {

}
