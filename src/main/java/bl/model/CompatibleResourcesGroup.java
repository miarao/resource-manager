package bl.model;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * author Miara Omer @ 08/08/2022
 */

public class CompatibleResourcesGroup implements Comparable<CompatibleResourcesGroup> {
    //region Constants
    private static final Integer GREATER_THAN = 1;
    private static final Integer EQUALS       = 0;
    private static final Integer LESS_THAN    = -1;
    //endregion

    //region Members
    private Integer                     compatibilityGroup;
    private List<BlSchedulableResource> compatibleResources;
    private Integer                     availableFrom;
    //endregion

    //region Getters & Setters
    public Integer getCompatibilityGroup() {
        return compatibilityGroup;
    }

    public void setCompatibilityGroup(Integer compatibilityGroup) {
        this.compatibilityGroup = compatibilityGroup;
    }

    public List<BlSchedulableResource> getCompatibleResources() {
        return compatibleResources;
    }

    public void setCompatibleResources(List<BlSchedulableResource> compatibleResources) {
        this.compatibleResources = compatibleResources;
    }

    public Integer getAvailableFrom() {
        return availableFrom;
    }

    public void setAvailableFrom(Integer availableFrom) {
        this.availableFrom = availableFrom;
    }
    //endregion

    //region Overridden Methods
    @Override
    public int compareTo(@NotNull CompatibleResourcesGroup o) {
        Integer retVal;

        if (this.compatibilityGroup < o.compatibilityGroup) {
            retVal = LESS_THAN;
        }
        else if (this.compatibilityGroup.equals(o.compatibilityGroup)) {
            retVal = EQUALS;
        }
        else {
            retVal = GREATER_THAN;
        }

        return retVal;
    }
    //endregion
}
