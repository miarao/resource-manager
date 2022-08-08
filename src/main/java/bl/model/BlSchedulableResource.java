package bl.model;

import javax.validation.constraints.NotNull;

/**
 * author Miara Omer @ 08/08/2022
 */

public class BlSchedulableResource extends BlResource implements Comparable<BlSchedulableResource> {

    //region Constants
    private static final Integer GREATER_THAN = 1;
    private static final Integer EQUALS       = 0;
    private static final Integer LESS_THAN    = -1;
    //endregion

    //region Members
    private              String  type;
    private              String  startTime;
    private              String  endTime;
    //endregion

    //region Constructor

    public BlSchedulableResource() {
        super();
    }

    public BlSchedulableResource(BlSchedulableResource that) {
        this.setId(that.getId());
        this.type = that.type;
        this.startTime = that.startTime;
        this.endTime = that.endTime;
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

    //region Overridden Methods


    @Override
    public boolean equals(Object o) {
        if (o instanceof BlSchedulableResource) {
            return this.getId().equals(((BlSchedulableResource) o).getId());
        }

        return false;
    }

    @Override
    public int compareTo(@NotNull BlSchedulableResource o) {
        Integer retVal = 0;

        if (Integer.parseInt(this.startTime) < Integer.parseInt(o.startTime)) {
            retVal = LESS_THAN;
        }
        else if (this.startTime.equals(o.startTime)) {
            retVal = EQUALS;
        }
        else {
            retVal = GREATER_THAN;
        }

        return retVal;
    }

    @Override
    public String toString() {
        return "BlSchedulableResource{" +
                "type='" + type + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                '}';
    }

    //endregion
}
