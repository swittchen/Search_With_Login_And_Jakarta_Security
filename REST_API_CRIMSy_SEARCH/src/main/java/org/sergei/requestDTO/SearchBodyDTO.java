package org.sergei.requestDTO;

import java.util.List;

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

    public List<String> getSearchQuery() {
        return searchQuery;
    }
    public void setSearchQuery(List<String> searchQuery) {
        this.searchQuery = searchQuery;
    }

    public List<String> getSearchTarget() {
        return searchTarget;
    }
    public void setSearchTarget(List<String> searchTarget) {
        this.searchTarget = searchTarget;
    }

    public List<String> getSearchValue() {
        return searchValue;
    }
    public void setSearchValue(List<String> searchValue) {
        this.searchValue = searchValue;
    }

    public String getMolFile() {
        return molFile;
    }
    public void setMolFile(String molFile) {
        this.molFile = molFile;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
