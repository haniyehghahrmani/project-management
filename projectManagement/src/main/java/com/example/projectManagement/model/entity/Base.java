package com.example.projectManagement.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder

@MappedSuperclass
public class Base {

    @Version
    @JsonIgnore
    private Long versionId;

    @JsonIgnore
    private boolean deleted;

    @JsonIgnore
    private boolean editing;
}
