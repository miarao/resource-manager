package api.requests;

import org.hibernate.validator.constraints.NotBlank;

/**
 * author Miara Omer @ 08/08/2022
 */

public class ApiFirstCollisionRequest {
    //region Members
    @NotBlank
    private String resourceId;
    //endregion

    //region Getters & Setters
    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }
    //endregion
}
