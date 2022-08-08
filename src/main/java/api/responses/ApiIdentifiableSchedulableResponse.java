package api.responses;

import api.model.ApiIdentifiableSchedulableResource;

import java.util.List;

/**
 * author Miara Omer @ 08/08/2022
 */

public class ApiIdentifiableSchedulableResponse {

    //region Members
    List<ApiIdentifiableSchedulableResource> resources;
    //endregion

    //region Getters & Setters
    public List<ApiIdentifiableSchedulableResource> getResources() {
        return resources;
    }

    public void setResources(List<ApiIdentifiableSchedulableResource> resources) {
        this.resources = resources;
    }
    //endregion
}
