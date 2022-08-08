package bl.helpers;

import bl.model.BlSchedulableResource;
import bl.model.CompatibleResourcesGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * author Miara Omer @ 08/08/2022
 */

public class SchedulingHelper {

    Logger LOGGER = LoggerFactory.getLogger(SchedulingHelper.class);

    public static Boolean insertIntoExistingGroup(BlSchedulableResource currentResource,
                                                  List<CompatibleResourcesGroup> compatibleResourcesGroups) {
        Boolean retVal = false;

        for (CompatibleResourcesGroup compatibleResourcesGroup : compatibleResourcesGroups) {
            Boolean isAvailable = SchedulingHelper.isVacantForResource(currentResource, compatibleResourcesGroup);

            if (isAvailable) {
                compatibleResourcesGroup.getCompatibleResources().add(currentResource);
                compatibleResourcesGroup.setAvailableFrom(Integer.parseInt(currentResource.getEndTime()));
                retVal = true;
                break;
            }
        }

        return retVal;
    }

    public static Boolean isVacantForResource(BlSchedulableResource currentResource,
                                              CompatibleResourcesGroup compatibleResourcesGroup) {
        Boolean retVal = false;

        if (compatibleResourcesGroup != null) {
            if (Integer.parseInt(currentResource.getStartTime()) > compatibleResourcesGroup.getAvailableFrom()) {
                retVal = true;
            }
        }

        return retVal;
    }

    public static BlSchedulableResource getCollidingResource(BlSchedulableResource firstCollidingResource,
                                                             List<BlSchedulableResource> schedule) {
        BlSchedulableResource retVal = null;

        for (BlSchedulableResource potentiallyCollidingResource : schedule) {
            Boolean isColliding = isColliding(firstCollidingResource, potentiallyCollidingResource);

            if (isColliding) {
                retVal = potentiallyCollidingResource;
                break;
            }
        }

        return retVal;
    }

    public static Boolean isColliding(BlSchedulableResource currentResource, BlSchedulableResource collidingCandidate) {
        Boolean retVal = false;

        Integer potentialCollisionStartTime = Integer.parseInt(currentResource.getStartTime());
        Integer potentialCollisionEndTime   = Integer.parseInt(currentResource.getEndTime());
        Integer firstCollisionStartTime = Integer.parseInt(collidingCandidate.getStartTime());

        if (potentialCollisionStartTime <= firstCollisionStartTime &&
                potentialCollisionEndTime >= firstCollisionStartTime) {
            retVal = true;
        }

        return retVal;
    }
}
