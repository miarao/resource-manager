package api.responses;

import api.model.ApiIdentifiableSchedulableResource;

import java.util.List;

/**
 * author Miara Omer @ 08/08/2022
 */

public class ApiCollidingResources {
    //region Members
    ApiIdentifiableSchedulableResource       resource;
    List<ApiIdentifiableSchedulableResource> collisions;
    //endregion

    //region Getters & Setters
    public ApiIdentifiableSchedulableResource getResource() {
        return resource;
    }

    public void setResource(ApiIdentifiableSchedulableResource resource) {
        this.resource = resource;
    }

    public List<ApiIdentifiableSchedulableResource> getCollisions() {
        return collisions;
    }

    public void setCollisions(List<ApiIdentifiableSchedulableResource> collisions) {
        this.collisions = collisions;
    }
    //endregion
}
