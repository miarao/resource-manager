package api.responses;

import java.util.List;

/**
 * author Miara Omer @ 08/08/2022
 */

public class ApiAllCollisionsResponse {
    //region Members
    private String                      status;
    private List<ApiCollidingResources> collisionsList;
    //endregion

    //region Getters & Setters
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ApiCollidingResources> getCollisionsList() {
        return collisionsList;
    }

    public void setCollisionsList(List<ApiCollidingResources> collisionsList) {
        this.collisionsList = collisionsList;
    }
    //endregion
}
