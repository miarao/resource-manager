package bl.cmds;

import bl.helpers.SchedulingHelper;
import bl.model.BlSchedulableResource;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * author Miara Omer @ 08/08/2022
 */

public class AllCollisionsCmd {

    public List<List<BlSchedulableResource>> execute(List<BlSchedulableResource> sortedResourcesStore) {
        List<List<BlSchedulableResource>> retVal = null;

        if (CollectionUtils.isNotEmpty(sortedResourcesStore)) {
            retVal = new ArrayList<>();
            Integer currentCandidateIndex = 0;

            for (BlSchedulableResource currentResource : sortedResourcesStore) {
                Integer iterator = ++currentCandidateIndex;

                List<BlSchedulableResource> collidingResourcesList = new ArrayList<>();

                //check only consecutive resources, until there are no collisions, lists are sorted
                while (iterator < sortedResourcesStore.size()) {
                    BlSchedulableResource collidingCandidate = sortedResourcesStore.get(iterator);

                    Boolean isColliding = SchedulingHelper.isColliding(currentResource, collidingCandidate);

                    if (isColliding) {
                        if (CollectionUtils.isEmpty(collidingResourcesList)) {
                            collidingResourcesList.add(currentResource);
                        }
                        collidingResourcesList.add(collidingCandidate);

                    }
                    else {
                        //sorted by start time. if current resource is not in collision,
                        //no need to check next ones.
                        break;

                    }
                    iterator++;
                }
                if (CollectionUtils.isNotEmpty(collidingResourcesList)) {
                    retVal.add(collidingResourcesList);
                }
            }
        }

        return retVal;
    }
}
