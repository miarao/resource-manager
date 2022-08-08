package api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * author Miara Omer @ 08/08/2022
 */

public class ApiIdentifiableSchedulableResource {

    //region Members
    @JsonProperty("id")
    private String id;

    @JsonProperty("resourceId")
    private String type;

    @JsonProperty("startTime")
    private String startTime;

    @JsonProperty("endTime")
    private String endTime;
    //endregion

    //region Constructor
    public ApiIdentifiableSchedulableResource() {
        super();
    }
    //endregion

    //region Getters & Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
    //endregion
}
