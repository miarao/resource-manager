package api.responses;

import api.model.ApiIdentifiableSchedulableResource;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * author Miara Omer @ 08/08/2022
 */

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ApiFirstCollisionResponse {
    //region Members
    private String                                   responseMessage;
    private List<ApiIdentifiableSchedulableResource> firstCollidingResource;
    //endregion

    //region Getters & Setters
    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public List<ApiIdentifiableSchedulableResource> getFirstCollidingResource() {
        return firstCollidingResource;
    }

    public void setFirstCollidingResource(List<ApiIdentifiableSchedulableResource> firstCollidingResource) {
        this.firstCollidingResource = firstCollidingResource;
    }
    //endregion
}
