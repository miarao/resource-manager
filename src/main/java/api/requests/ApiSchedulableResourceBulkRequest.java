package api.requests;

import api.model.ApiSchedulableResource;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

/**
 * author Miara Omer @ 08/08/2022
 */

public class ApiSchedulableResourceBulkRequest {

    //region Members
    @NotEmpty
    List<ApiSchedulableResource> resources;
    //endregion

    //region Getters & Setters
    public List<ApiSchedulableResource> getResources() {
        return resources;
    }

    public void setResources(List<ApiSchedulableResource> resources) {
        this.resources = resources;
    }
    //endregion
}
