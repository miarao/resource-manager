package api.requests;

import org.hibernate.validator.constraints.NotBlank;

/**
 * author Miara Omer @ 08/08/2022
 */

public class ApiAvailabilityStatusRequest {

    //region Members
    @NotBlank
    private String resourceId;
    @NotBlank
    private String time;
    //endregion

    //region Getters & Setters
    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    //endregion
}
