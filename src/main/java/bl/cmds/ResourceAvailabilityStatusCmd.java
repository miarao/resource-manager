package bl.cmds;

import bl.model.BlSchedulableResource;
import bl.model.CompatibleResourcesGroup;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * author Miara Omer @ 08/08/2022
 */

public class ResourceAvailabilityStatusCmd {

    public String execute(Map<String, List<CompatibleResourcesGroup>> schedulesByResourcesTypesStore,
                          Set<String> resourceTypesStore, String type, String timestamp) {
        String retVal = "free";

        if (resourceTypesStore.contains(type)) {
            List<CompatibleResourcesGroup> resourcesGroupsByType = schedulesByResourcesTypesStore.get(type);

            if (CollectionUtils.isNotEmpty(resourcesGroupsByType)) {
                for (CompatibleResourcesGroup resourcesGroup : resourcesGroupsByType) {

                    List<BlSchedulableResource> overlapWithTimeStamp =
                            resourcesGroup.getCompatibleResources().stream().
                                    filter(sr -> Integer.parseInt(sr.getStartTime()) <= Integer.parseInt(timestamp) &&
                                            Integer.parseInt(sr.getEndTime()) > Integer.parseInt(timestamp)).
                                    collect(Collectors.toList());

                    if (CollectionUtils.isNotEmpty(overlapWithTimeStamp)) {
                        retVal = "locked";
                        break;
                    }
                }
            }
        }

        return retVal;
    }
}
