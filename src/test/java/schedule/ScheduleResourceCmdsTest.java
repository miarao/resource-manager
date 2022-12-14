package schedule;

import api.model.ApiSchedulableResource;
import api.requests.ApiAllCollisionsRequest;
import api.requests.ApiAvailabilityStatusRequest;
import api.requests.ApiFirstCollisionRequest;
import api.requests.ApiSchedulableResourceBulkRequest;
import api.resources.ScheduleResource;
import api.responses.*;
import bl.model.CompatibleResourcesGroup;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class ScheduleResourceCmdsTest {

    //region Constants
    private final Integer EMPTY_LIST = 0;
    private final String  TypeA      = "a";
    private final String  TypeB      = "b";
    Logger LOGGER = LoggerFactory.getLogger(ScheduleResourceCmdsTest.class);
    //endregion
    //region Members
    private ScheduleResource scheduleResource;
    //endregion

    //region setup
    @Before
    public void setUp() {
        scheduleResource = new ScheduleResource();
    }
    //endregion

    private void warmUpSchedulesByResourcesTypesStoreWithoutCollisions(Boolean injectIllegalTime) {
        ApiSchedulableResourceBulkRequest bulkRequest      = new ApiSchedulableResourceBulkRequest();
        List<ApiSchedulableResource>      nonCollidingList = new ArrayList<>();

        //region ###################### Type A init ######################
        ApiSchedulableResource schedulableResource1a = getApiSchedulableResource(TypeA, "0000", "0100");

        nonCollidingList.add(schedulableResource1a);

        ApiSchedulableResource schedulableResource2a = getApiSchedulableResource(TypeA, "1000", "3594");

        nonCollidingList.add(schedulableResource2a);

        ApiSchedulableResource schedulableResource3a = getApiSchedulableResource(TypeA, "4000", "4202");

        nonCollidingList.add(schedulableResource3a);

        ApiSchedulableResource schedulableResource4a = getApiSchedulableResource(TypeA, "4309", "4995");

        nonCollidingList.add(schedulableResource4a);

        ApiSchedulableResource schedulableResource5a = getApiSchedulableResource(TypeA, "5036", "5120");

        nonCollidingList.add(schedulableResource5a);
        //endregion

        //region ###################### Type B init ######################
        ApiSchedulableResource schedulableResource1b = getApiSchedulableResource(TypeB, "750001", "771528");

        nonCollidingList.add(schedulableResource1b);

        ApiSchedulableResource schedulableResource2b = getApiSchedulableResource(TypeB, "15314", "25111");

        nonCollidingList.add(schedulableResource2b);

        ApiSchedulableResource schedulableResource3b = getApiSchedulableResource(TypeB, "154377", "330650");

        nonCollidingList.add(schedulableResource3b);
        //endregion

        //region Illegal Times
        if (injectIllegalTime) {
            ApiSchedulableResource illegalTimeResource = getApiSchedulableResource(TypeB, "330650", "154377");

            nonCollidingList.add(illegalTimeResource);
        }
        //endregion

        bulkRequest.setResources(nonCollidingList);
        scheduleResource.insertIntoSchedule(bulkRequest);
    }

    private void warmUpSchedulesByResourcesTypesStoreWithCollisions(Boolean injectDefection,
                                                                    Boolean injectEmptyTypeAndIllegalTime) {
        ApiSchedulableResourceBulkRequest bulkRequest   = new ApiSchedulableResourceBulkRequest();
        List<ApiSchedulableResource>      collidingList = new ArrayList<>();

        //region ###################### Type A init ######################
        ApiSchedulableResource schedulableResource1a = getApiSchedulableResource(TypeA, "0000", "0300");

        collidingList.add(schedulableResource1a);

        ApiSchedulableResource schedulableResource2a = getApiSchedulableResource(TypeA, "1430", "1500");

        collidingList.add(schedulableResource2a);

        ApiSchedulableResource schedulableResource3a = getApiSchedulableResource(TypeA, "0230", "0300");

        collidingList.add(schedulableResource3a);

        ApiSchedulableResource schedulableResource4a = getApiSchedulableResource(TypeA, "0000", "0100");

        collidingList.add(schedulableResource4a);

        ApiSchedulableResource schedulableResource5a = getApiSchedulableResource(TypeA, "0300", "0400");

        collidingList.add(schedulableResource5a);

        ApiSchedulableResource schedulableResource6a = getApiSchedulableResource(TypeA, "0300", "0400");

        collidingList.add(schedulableResource6a);

        ApiSchedulableResource schedulableResource7a = getApiSchedulableResource(TypeA, "0000", "0200");

        collidingList.add(schedulableResource7a);

        ApiSchedulableResource schedulableResource8a = getApiSchedulableResource(TypeA, "0410", "0500");

        collidingList.add(schedulableResource8a);

        ApiSchedulableResource schedulableResource9a = getApiSchedulableResource(TypeA, "0125", "0300");

        collidingList.add(schedulableResource8a);

        ApiSchedulableResource schedulableResource10a = getApiSchedulableResource(TypeA, "0317", "0541");

        collidingList.add(schedulableResource10a);
        //endregion

        //region ###################### Type B init ######################
        ApiSchedulableResource schedulableResource1b = getApiSchedulableResource(TypeB, "1070", "2180");

        collidingList.add(schedulableResource1b);

        ApiSchedulableResource schedulableResource2b = getApiSchedulableResource(TypeB, "3400", "4220");

        collidingList.add(schedulableResource2b);

        ApiSchedulableResource schedulableResource3b = getApiSchedulableResource(TypeB, "2030", "3380");

        collidingList.add(schedulableResource3b);

        ApiSchedulableResource schedulableResource4b = getApiSchedulableResource(TypeB, "3010", "3030");

        collidingList.add(schedulableResource4b);

        ApiSchedulableResource schedulableResource5b = getApiSchedulableResource(TypeB, "3460", "4323");

        collidingList.add(schedulableResource5b);

        ApiSchedulableResource schedulableResource6b = getApiSchedulableResource(TypeB, "2440", "4242");

        collidingList.add(schedulableResource6b);
        //endregion

        //region Defect Resource
        if (injectDefection) {
            ApiSchedulableResource defectSchedulableResource = getApiSchedulableResource(TypeB, "2x40", "4242");

            collidingList.add(defectSchedulableResource);
        }
        //endregion

        //region Empty Type And IllegalTime
        if (injectEmptyTypeAndIllegalTime) {
            ApiSchedulableResource emptyTypeIllegalTimeResource = getApiSchedulableResource("", "3210", "0123");

            collidingList.add(emptyTypeIllegalTimeResource);
        }
        //endregion

        bulkRequest.setResources(collidingList);
        scheduleResource.insertIntoSchedule(bulkRequest);
    }

    private ApiSchedulableResource getApiSchedulableResource(String resourceType, String startTime, String endTime) {
        ApiSchedulableResource retVal = new ApiSchedulableResource();

        retVal.setType(resourceType);
        retVal.setStartTime(startTime);
        retVal.setEndTime(endTime);

        return retVal;
    }

    @Test
    public void testInsertValidInputIntoScheduleWithCollisions_thenAllResourcesAreInserted() {
        warmUpSchedulesByResourcesTypesStoreWithCollisions(false, false);

        ApiIdentifiableSchedulableResponse response = scheduleResource.getResources();

        Assert.assertEquals(16, response.getResources().size());
    }

    @Test
    public void testInsertValidInputIntoScheduleWithCollisions_thenGroupA4ResourceGroupsGroupB3ResourceGroups() {
        warmUpSchedulesByResourcesTypesStoreWithCollisions(false, false);

        Map<String, List<CompatibleResourcesGroup>> response = scheduleResource.schedulesByResourcesTypesStore;

        Set<String> typesFromStore = response.keySet();
        Set<String> typesCollected = scheduleResource.resourceTypesStore;

        Assert.assertEquals(typesFromStore, typesCollected);

        String defaultValue = "Not default";

        for (String type : typesCollected) {
            List<CompatibleResourcesGroup> compatibleResourcesGroups = response.get(type);

            switch (type) {
                case TypeA: {
                    Assert.assertEquals(4, compatibleResourcesGroups.size());

                    Integer[] groupSizes = new Integer[]{3, 3, 3, 1};

                    for (int i = 0; i < compatibleResourcesGroups.size(); i++) {
                        Integer actualSize = compatibleResourcesGroups.get(i).getCompatibleResources().size();
                        Assert.assertEquals(actualSize, groupSizes[i]);
                    }
                    break;
                }

                case TypeB: {
                    Assert.assertEquals(3, compatibleResourcesGroups.size());

                    Integer[] groupSizes = new Integer[]{2, 2, 2};

                    for (int i = 0; i < compatibleResourcesGroups.size(); i++) {
                        Integer actualSize = compatibleResourcesGroups.get(i).getCompatibleResources().size();
                        Assert.assertEquals(actualSize, groupSizes[i]);
                    }
                    break;
                }
                default: {
                    defaultValue = "default";
                }
            }
        }

        Assert.assertEquals("Not default", defaultValue);
    }

    @Test
    public void testInsertValidWithCollisionAndFindFirstCollision_thenShouldReturnCollision() {
        warmUpSchedulesByResourcesTypesStoreWithCollisions(false, false);

        ApiFirstCollisionRequest request = new ApiFirstCollisionRequest();
        request.setResourceId(TypeA);

        ApiFirstCollisionResponse response = scheduleResource.getFirstCollisionForResourceType(request);

        Assert.assertEquals("Success", response.getResponseMessage());
        Assert.assertEquals("0000", response.getFirstCollidingResource().get(0).getStartTime());
        Assert.assertEquals("0100", response.getFirstCollidingResource().get(0).getEndTime());
        Assert.assertEquals("0000", response.getFirstCollidingResource().get(1).getStartTime());
        Assert.assertEquals("0300", response.getFirstCollidingResource().get(1).getEndTime());
    }

    @Test
    public void testInsertValidWithoutCollisionAndFindFirstCollision_thenShouldReturnNoCollision() {
        warmUpSchedulesByResourcesTypesStoreWithoutCollisions(false);

        ApiFirstCollisionRequest request = new ApiFirstCollisionRequest();
        request.setResourceId("a");

        ApiFirstCollisionResponse response = scheduleResource.getFirstCollisionForResourceType(request);
        Assert.assertEquals("No collision", response.getResponseMessage());
    }

    @Test
    public void testInsertValidWithoutCollisionAndWithIllegalTimeAndFindFirstCollision_thenShouldReturnNoCollision() {
        warmUpSchedulesByResourcesTypesStoreWithoutCollisions(true);

        ApiFirstCollisionRequest requestA = new ApiFirstCollisionRequest();
        requestA.setResourceId(TypeA);

        ApiFirstCollisionRequest requestB = new ApiFirstCollisionRequest();
        requestB.setResourceId(TypeB);

        ApiFirstCollisionResponse responseA = scheduleResource.getFirstCollisionForResourceType(requestA);
        Assert.assertEquals(EMPTY_LIST , new Integer(scheduleResource.sortedResourcesStore.size()));
        Assert.assertEquals("No collision", responseA.getResponseMessage());

        ApiFirstCollisionResponse responseB = scheduleResource.getFirstCollisionForResourceType(requestB);
        Assert.assertEquals(EMPTY_LIST , new Integer(scheduleResource.sortedResourcesStore.size()));
        Assert.assertEquals("No collision", responseB.getResponseMessage());
    }

    @Test
    public void testInsertValidWithCollisionAndCheckAvailabilityWhenUnavailable_thenShouldReturnLocked() {
        warmUpSchedulesByResourcesTypesStoreWithCollisions(false, false);

        ApiAvailabilityStatusRequest request = new ApiAvailabilityStatusRequest();
        request.setResourceId(TypeA);
        request.setTime("0540");

        ApiAvailabilityStatusResponse response = scheduleResource.getAvailabilityStatus(request);
        Assert.assertEquals("Success" , response.getRequestStatus());
        Assert.assertEquals("locked", response.getAvailabilityStatus());
    }

    @Test
    public void testInsertValidWithCollisionAndCheckAvailabilityWhenAvailable_thenShouldReturnFree() {
        warmUpSchedulesByResourcesTypesStoreWithCollisions(false, false);

        ApiAvailabilityStatusRequest request = new ApiAvailabilityStatusRequest();
        request.setResourceId(TypeA);
        request.setTime("0541");

        ApiAvailabilityStatusResponse response = scheduleResource.getAvailabilityStatus(request);
        Assert.assertEquals("Success" , response.getRequestStatus());
        Assert.assertEquals("free", response.getAvailabilityStatus());
    }

    @Test
    public void testInsertValidWithCollisionAndGetAllCollisions_thenShouldReturnAllCollisions() {
        warmUpSchedulesByResourcesTypesStoreWithCollisions(false, false);

        ApiAllCollisionsRequest request = new ApiAllCollisionsRequest();
        request.setResourceId(TypeA);

        ApiAllCollisionsResponse response = scheduleResource.getAllCollisions(request);

        Assert.assertEquals("Success", response.getStatus());

        Integer numberOfResourcesWithCollisions = response.getCollisionsList().size();

        List<ApiCollidingResources> collidingResourcesList = response.getCollisionsList();
        int[] numberOfCollisionsByResource = new int[]{5,1,2,2,1,2,1,2,2,3,1};

        for (int i = 0; i < numberOfResourcesWithCollisions; i++) {
            ApiCollidingResources collidingResources = collidingResourcesList.get(i);
            Assert.assertNotNull(collidingResources.getResource());

            Assert.assertTrue(CollectionUtils.isNotEmpty(collidingResources.getCollisions()));
            Assert.assertEquals(numberOfCollisionsByResource[i], collidingResources.getCollisions().size());

        }
    }

    @Test
    public void testInsertValidWithoutCollisionAndGetAllCollisions_thenShouldReturn() {
        warmUpSchedulesByResourcesTypesStoreWithoutCollisions( false);

        ApiAllCollisionsRequest requestA = new ApiAllCollisionsRequest();
        requestA.setResourceId(TypeA);

        ApiAllCollisionsRequest requestB = new ApiAllCollisionsRequest();
        requestB.setResourceId(TypeB);

        ApiAllCollisionsResponse responseA = scheduleResource.getAllCollisions(requestA);
        ApiAllCollisionsResponse responseB = scheduleResource.getAllCollisions(requestB);

        Assert.assertEquals("No collisions", responseA.getStatus());
        Assert.assertEquals("No collisions", responseB.getStatus());

        Assert.assertNotNull(responseA.getCollisionsList());
        Assert.assertNotNull(responseB.getCollisionsList());

        Assert.assertEquals(Collections.EMPTY_LIST, responseA.getCollisionsList());
        Assert.assertEquals(Collections.EMPTY_LIST, responseB.getCollisionsList());
    }

    @Test
    public void testInsertInvalidInputIntoScheduleWithCollisions_thenResponseSizeIs0() {
        warmUpSchedulesByResourcesTypesStoreWithCollisions(true, false);

        ApiIdentifiableSchedulableResponse response = scheduleResource.getResources();


        Assert.assertNull(response);
    }

    @Test
    public void testInsertValidInputIntoScheduleWithCollisions_thenValidNoCollisionsSchedule() {
        warmUpSchedulesByResourcesTypesStoreWithCollisions(false, true);

        ApiIdentifiableSchedulableResponse response = scheduleResource.getResources();

        Assert.assertNull(response);
    }

}
