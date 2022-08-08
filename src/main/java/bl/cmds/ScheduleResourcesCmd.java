package bl.cmds;

import bl.helpers.SchedulingHelper;
import bl.model.BlSchedulableResource;
import bl.model.CompatibleResourcesGroup;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * author Miara Omer @ 08/08/2022
 */

public class ScheduleResourcesCmd {

    private static Logger                      LOGGER              = LoggerFactory.getLogger(ScheduleResourcesCmd.class);
    private final  Integer                     FIRST_ITEM_POSITION = 0;

    //region Public Methods
    public List<BlSchedulableResource> execute(List<BlSchedulableResource> resources, Map<String,
            List<CompatibleResourcesGroup>> schedulesByResourcesTypesStore) {
        List<BlSchedulableResource> retVal = new ArrayList<>();

        LOGGER.info("Start adding resources to scheduled resources store.");
        List<BlSchedulableResource> resourcesList = addToStore(resources, schedulesByResourcesTypesStore);
        LOGGER.info("Finished adding resources to scheduled resources store.");

        retVal = resourcesList;

        return retVal;
    }
    //endregion


    //region Private Methods
    private List<BlSchedulableResource> addToStore(List<BlSchedulableResource> resources, Map<String,
            List<CompatibleResourcesGroup>> schedulesByResourcesTypesStore) {
        List<BlSchedulableResource> retVal = new ArrayList<>();

        //copy to enable safe deletion, sort by start time for interval partitioning
        List<BlSchedulableResource> sortedResourceListCopy =
                resources.stream().map(BlSchedulableResource::new).sorted().collect(Collectors.toList());

        while (CollectionUtils.isNotEmpty(sortedResourceListCopy)) {
            String type = sortedResourceListCopy.get(FIRST_ITEM_POSITION).getType();

            List<BlSchedulableResource> sortedResourcesByType =
                    sortedResourceListCopy.stream().filter(sr -> type.equals(sr.getType())).collect(Collectors.toList());

            List<CompatibleResourcesGroup> compatibleResourcesListByType =
                    buildCompatibleGroupsByType(sortedResourcesByType);

            schedulesByResourcesTypesStore.put(type, compatibleResourcesListByType);

            sortedResourceListCopy =
                    sortedResourceListCopy.stream().filter(sr -> type.equals(sr.getType()) == false).
                            collect(Collectors.toList());
        }

        retVal = resources.stream().sorted().collect(Collectors.toList());

        return retVal;
    }

    private List<CompatibleResourcesGroup> buildCompatibleGroupsByType(List<BlSchedulableResource> resourcesByType) {
        List<CompatibleResourcesGroup> retVal;

        List<BlSchedulableResource> resourcesByTypeCopy =
                resourcesByType.stream().map(BlSchedulableResource::new).collect(Collectors.toList());

        Integer numberOfResourcesFromType = resourcesByTypeCopy.size();
        Integer depth                     = 0;

        BlSchedulableResource    firstResource   = resourcesByTypeCopy.get(0);
        CompatibleResourcesGroup compatibleGroup = buildNewCompatibleGroup(firstResource, depth);

        List<CompatibleResourcesGroup> compatibleResourcesGroups = new ArrayList<>();
        compatibleResourcesGroups.add(compatibleGroup);

        for (int resourceByPosition = 1; resourceByPosition < numberOfResourcesFromType; resourceByPosition++) {

            BlSchedulableResource currentResource = resourcesByType.get(resourceByPosition);
            Boolean isInsertionSucceeded = SchedulingHelper.insertIntoExistingGroup(currentResource,
                    compatibleResourcesGroups);

            Boolean shouldCreateNewGroup = isInsertionSucceeded == false;

            //need to create another compatibility group and assign the resource to it.
            if (shouldCreateNewGroup) {
                CompatibleResourcesGroup newCompatibleGroup = buildNewCompatibleGroup(currentResource, ++depth);

                compatibleResourcesGroups.add(newCompatibleGroup);
            }
        }
        retVal = compatibleResourcesGroups;

        return retVal;
    }

    private CompatibleResourcesGroup buildNewCompatibleGroup(BlSchedulableResource resource, Integer depth) {
        CompatibleResourcesGroup retVal = new CompatibleResourcesGroup();

        retVal.setCompatibilityGroup(depth);
        List<BlSchedulableResource> newCompatibleResourcesList = new ArrayList<>();
        newCompatibleResourcesList.add(resource);
        retVal.setCompatibleResources(newCompatibleResourcesList);
        retVal.setAvailableFrom(Integer.parseInt(resource.getEndTime()));

        return retVal;
    }


    //endregion

}
