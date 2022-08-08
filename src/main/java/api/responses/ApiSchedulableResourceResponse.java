package api.responses;


import api.model.ApiSchedulableResource;

import java.util.List;

/**
 * author Miara Omer @ 08/08/2022
 */

public class ApiSchedulableResourceResponse {
    //region Members
    String                       responseMessage;
    List<ApiSchedulableResource> resources;
    //endregion

    //region Getters & Setters
    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public List<ApiSchedulableResource> getResources() {
        return resources;
    }

    public void setResources(List<ApiSchedulableResource> resources) {
        this.resources = resources;
    }
    //endregion
}
