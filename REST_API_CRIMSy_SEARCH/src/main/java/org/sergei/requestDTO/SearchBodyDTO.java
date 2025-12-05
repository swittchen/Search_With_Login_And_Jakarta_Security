package org.sergei.requestDTO;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SearchBodyDTO {
    private List<String> searchQuery;
    private List<String> searchTarget;
    private List<String> searchValue;
    private String molFile;

    // for testing
    private String status;

    // Constructor for JSON-B transformer in tomee plume container
    public SearchBodyDTO() {
    }

    public SearchBodyDTO(String[] searchQuery, String searchTarget, String searchValue, String molFile) {
        this.searchQuery = Arrays.stream(searchQuery).collect(Collectors.toList());
        this.searchTarget = Arrays.stream(searchQuery).collect(Collectors.toList());
        this.searchValue = Arrays.stream(searchQuery).collect(Collectors.toList());
        this.molFile = molFile;
    }

    public List<String> getSearchQuery() {
        return searchQuery;
    }

    public SearchBodyDTO setSearchQuery(List<String> searchQuery) {
        this.searchQuery = searchQuery;
        return this;
    }

    public List<String> getSearchTarget() {
        return searchTarget;
    }

    public SearchBodyDTO setSearchTarget(List<String> searchTarget) {
        this.searchTarget = searchTarget;
        return this;
    }

    public List<String> getSearchValue() {
        return searchValue;
    }

    public SearchBodyDTO setSearchValue(List<String> searchValue) {
        this.searchValue = searchValue;
        return this;
    }

    public String getMolFile() {
        return molFile;
    }

    public SearchBodyDTO setMolFile(String molFile) {
        this.molFile = molFile;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public SearchBodyDTO setStatus(String status) {
        this.status = status;
        return this;
    }
}
