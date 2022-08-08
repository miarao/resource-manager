package api.responses;

/**
 * author Miara Omer @ 08/08/2022
 */

public class ApiAvailabilityStatusResponse {

    //region Members
    private String requestStatus;
    private String availabilityStatus;
    //endregion

    //region Getters & Setters
    public String getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
    }

    public String getAvailabilityStatus() {
        return availabilityStatus;
    }

    public void setAvailabilityStatus(String availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }
    //endregion
}
