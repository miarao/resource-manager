package api.requests.helpers;

import api.model.ApiSchedulableResource;
import api.requests.ApiSchedulableResourceBulkRequest;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * author Miara Omer @ 08/08/2022
 */

import javax.validation.Valid;

public class RequestHelper {

    public static String validateBulkRequest(@Valid ApiSchedulableResourceBulkRequest request) {
        String retVal = null;

        if (request != null && CollectionUtils.isNotEmpty(request.getResources())) {
            for (ApiSchedulableResource resource : request.getResources()) {
                retVal = validateResource(resource);
            }
        }

        return retVal;
    }

    public static String validateResource(ApiSchedulableResource resource) {
        String retVal = null;

        try {
            Integer.parseInt(resource.getEndTime());
            Integer.parseInt(resource.getStartTime());
            if (StringUtils.isEmpty(resource.getType())) {
                retVal = "Invalid request. Errors: Resource type (id) must not be empty. ";
            }
            if (Integer.parseInt(resource.getEndTime()) < Integer.parseInt(resource.getStartTime())) {
                retVal += "Start time must be strictly smaller than start time.";
            }
        } catch (Exception e) {
            String errMsg = String.format("Invalid request. Errors: %s", e.getMessage());
            retVal = errMsg;
        }

        return retVal;
    }

    public static String validateTimestamp(String timestampAsString) {
        String retVal = null;

        try {
            Integer.parseInt(timestampAsString);
        }
        catch (Exception e) {
            String errMsg = String.format("Invalid string format - not timestamp. Errors: %s", e.getMessage());
            retVal = errMsg;
        }

        return retVal;
    }
}
