package bl.cmds;

import bl.helpers.SchedulingHelper;
import bl.model.BlSchedulableResource;
import bl.model.CompatibleResourcesGroup;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * author Miara Omer @ 08/08/2022
 */

public class FirstCollisionCmd {

    private static Logger LOGGER = LoggerFactory.getLogger(ScheduleResourcesCmd.class);

    //region Constants
    private final Integer FIRST_ITEM_POSITION            = 0;
    private final Integer FIRST_ITEMS_POSITION           = 0;
    private final Integer SINGLE_COMPATIBILITY_GROUP     = 1;
    private final Integer FIRST_COLLISION_RESOURCE_GROUP = 1;
    //endregion


    //region Public Methods
    public List<BlSchedulableResource> execute(Map<String, List<CompatibleResourcesGroup>> schedulesByResourcesTypesStore,
                                         Set<String> resourceTypesStore, String type) {
        List<BlSchedulableResource> retVal = new LinkedList<>();

        if (resourceTypesStore.contains(type)) {
            List<CompatibleResourcesGroup> resourcesGroupsByType = schedulesByResourcesTypesStore.get(type);

            //the second resource group was initially created due to a collision
            if (CollectionUtils.isNotEmpty(resourcesGroupsByType) && resourcesGroupsByType.size() > SINGLE_COMPATIBILITY_GROUP) {
                BlSchedulableResource firstCollidingResource =
                        resourcesGroupsByType.get(FIRST_COLLISION_RESOURCE_GROUP)
                                .getCompatibleResources().get(FIRST_ITEM_POSITION);

                retVal.add(firstCollidingResource);

                List<BlSchedulableResource> firstSchedule =
                        resourcesGroupsByType.get(FIRST_ITEMS_POSITION).getCompatibleResources();

                BlSchedulableResource collisionResource = SchedulingHelper.getCollidingResource(firstCollidingResource, firstSchedule);
                retVal.add(collisionResource);
            }
            else {
                LOGGER.error("Type {} but compatible resources groups are empty. Should bu investigated.", type);
            }
        }
        else {
            LOGGER.info("No schedules for type {}", type);
        }
        return retVal;
    }
    //endregion

}
