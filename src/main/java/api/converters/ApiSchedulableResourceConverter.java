package api.converters;

import api.model.ApiIdentifiableSchedulableResource;
import api.model.ApiSchedulableResource;
import bl.model.BlSchedulableResource;

/**
 * author Miara Omer @ 08/08/2022
 */

public class ApiSchedulableResourceConverter {


    public static BlSchedulableResource apiToBl(ApiSchedulableResource schedulableResource) {
        BlSchedulableResource retVal = new BlSchedulableResource();

        retVal.setType(schedulableResource.getType());
        retVal.setStartTime(schedulableResource.getStartTime());
        retVal.setEndTime(schedulableResource.getEndTime());

        return retVal;
    }

    public static ApiSchedulableResource blToApi(BlSchedulableResource schedulableResource) {
        ApiSchedulableResource retVal = new ApiSchedulableResource();

        retVal.setType(schedulableResource.getType());
        retVal.setStartTime(schedulableResource.getStartTime());
        retVal.setEndTime(schedulableResource.getEndTime());

        return retVal;
    }

    public static ApiIdentifiableSchedulableResource identifiableBlToApi(BlSchedulableResource schedulableResource) {
        ApiIdentifiableSchedulableResource retVal = new ApiIdentifiableSchedulableResource();

        retVal.setId(schedulableResource.getId());
        retVal.setType(schedulableResource.getType());
        retVal.setStartTime(schedulableResource.getStartTime());
        retVal.setEndTime(schedulableResource.getEndTime());

        return retVal;
    }
}
