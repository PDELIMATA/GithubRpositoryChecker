package com.delimata.githubrepositorychecker.Model;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@JsonPropertyOrder({"name","owner","branches"})
public class Repositories {
    @JsonProperty("Repository Name")
    @JsonAlias("name")
    private String name;
    private Owner owner;
    @JsonIgnore
    private boolean fork;
    private Branch[] branches;
}

