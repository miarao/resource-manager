package api.model;

import com.fasterxml.jackson.annotation.JsonProperty;


public class ApiSchedulableResource {

    //region Members
    @JsonProperty("resourceId")
    private String type;

    @JsonProperty("startTime")
    private String startTime;

    @JsonProperty("endTime")
    private String endTime;
    //endregion

    //region Constructor
    public ApiSchedulableResource() {
        super();
    }
    //endregion

    //region Getters & Setters
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
