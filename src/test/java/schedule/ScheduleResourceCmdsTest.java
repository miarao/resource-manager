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
        ApiSchedulableResource schedulableResource1a = new ApiSchedulableResource();
        schedulableResource1a.setType(TypeA);
        schedulableResource1a.setStartTime("0000");
        schedulableResource1a.setEndTime("0100");

        nonCollidingList.add(schedulableResource1a);

        ApiSchedulableResource schedulableResource2a = new ApiSchedulableResource();
        schedulableResource2a.setType(TypeA);
        schedulableResource2a.setStartTime("1000");
        schedulableResource2a.setEndTime("3594");

        nonCollidingList.add(schedulableResource2a);

        ApiSchedulableResource schedulableResource3a = new ApiSchedulableResource();
        schedulableResource3a.setType(TypeA);
        schedulableResource3a.setStartTime("4000");
        schedulableResource3a.setEndTime("4202");

        nonCollidingList.add(schedulableResource3a);

        ApiSchedulableResource schedulableResource4a = new ApiSchedulableResource();
        schedulableResource4a.setType(TypeA);
        schedulableResource4a.setStartTime("4309");
        schedulableResource4a.setEndTime("4995");

        nonCollidingList.add(schedulableResource4a);

        ApiSchedulableResource schedulableResource5a = new ApiSchedulableResource();
        schedulableResource5a.setType(TypeA);
        schedulableResource5a.setStartTime("5036");
        schedulableResource5a.setEndTime("5120");

        nonCollidingList.add(schedulableResource5a);
        //endregion

        //region ###################### Type B init ######################
        ApiSchedulableResource schedulableResource1b = new ApiSchedulableResource();
        schedulableResource1b.setType(TypeB);
        schedulableResource1b.setStartTime("750001");
        schedulableResource1b.setEndTime("771528");

        nonCollidingList.add(schedulableResource1b);

        ApiSchedulableResource schedulableResource2b = new ApiSchedulableResource();
        schedulableResource2b.setType(TypeB);
        schedulableResource2b.setStartTime("15314");
        schedulableResource2b.setEndTime("25111");

        nonCollidingList.add(schedulableResource2b);

        ApiSchedulableResource schedulableResource3b = new ApiSchedulableResource();
        schedulableResource3b.setType(TypeB);
        schedulableResource3b.setStartTime("154377");
        schedulableResource3b.setEndTime("330650");

        nonCollidingList.add(schedulableResource3b);
        //endregion

        //region Illegal Times
        if (injectIllegalTime) {
            ApiSchedulableResource illegalTimeResource = new ApiSchedulableResource();
            illegalTimeResource.setType(TypeB);
            illegalTimeResource.setStartTime("330650");
            illegalTimeResource.setEndTime("154377");

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
        ApiSchedulableResource schedulableResource1a = new ApiSchedulableResource();
        schedulableResource1a.setType(TypeA);
        schedulableResource1a.setStartTime("0000");
        schedulableResource1a.setEndTime("0300");

        collidingList.add(schedulableResource1a);

        ApiSchedulableResource schedulableResource2a = new ApiSchedulableResource();
        schedulableResource2a.setType(TypeA);
        schedulableResource2a.setStartTime("1430");
        schedulableResource2a.setEndTime("1500");

        collidingList.add(schedulableResource2a);

        ApiSchedulableResource schedulableResource3a = new ApiSchedulableResource();
        schedulableResource3a.setType(TypeA);
        schedulableResource3a.setStartTime("0230");
        schedulableResource3a.setEndTime("0300");

        collidingList.add(schedulableResource3a);

        ApiSchedulableResource schedulableResource4a = new ApiSchedulableResource();
        schedulableResource4a.setType(TypeA);
        schedulableResource4a.setStartTime("0000");
        schedulableResource4a.setEndTime("0100");

        collidingList.add(schedulableResource4a);

        ApiSchedulableResource schedulableResource5a = new ApiSchedulableResource();
        schedulableResource5a.setType(TypeA);
        schedulableResource5a.setStartTime("0300");
        schedulableResource5a.setEndTime("0400");

        collidingList.add(schedulableResource5a);

        ApiSchedulableResource schedulableResource6a = new ApiSchedulableResource();
        schedulableResource6a.setType(TypeA);
        schedulableResource6a.setStartTime("0300");
        schedulableResource6a.setEndTime("0400");

        collidingList.add(schedulableResource6a);

        ApiSchedulableResource schedulableResource7a = new ApiSchedulableResource();
        schedulableResource7a.setType(TypeA);
        schedulableResource7a.setStartTime("0000");
        schedulableResource7a.setEndTime("0200");

        collidingList.add(schedulableResource7a);

        ApiSchedulableResource schedulableResource8a = new ApiSchedulableResource();
        schedulableResource8a.setType(TypeA);
        schedulableResource8a.setStartTime("0410");
        schedulableResource8a.setEndTime("0500");

        collidingList.add(schedulableResource8a);

        ApiSchedulableResource schedulableResource9a = new ApiSchedulableResource();
        schedulableResource9a.setType(TypeA);
        schedulableResource9a.setStartTime("0125");
        schedulableResource9a.setEndTime("0300");

        collidingList.add(schedulableResource8a);

        ApiSchedulableResource schedulableResource10a = new ApiSchedulableResource();
        schedulableResource10a.setType(TypeA);
        schedulableResource10a.setStartTime("0317");
        schedulableResource10a.setEndTime("0541");

        collidingList.add(schedulableResource10a);
        //endregion

        //region ###################### Type B init ######################
        ApiSchedulableResource schedulableResource1b = new ApiSchedulableResource();
        schedulableResource1b.setType(TypeB);
        schedulableResource1b.setStartTime("1070");
        schedulableResource1b.setEndTime("2180");

        collidingList.add(schedulableResource1b);

        ApiSchedulableResource schedulableResource2b = new ApiSchedulableResource();
        schedulableResource2b.setType(TypeB);
        schedulableResource2b.setStartTime("3400");
        schedulableResource2b.setEndTime("4220");

        collidingList.add(schedulableResource2b);

        ApiSchedulableResource schedulableResource3b = new ApiSchedulableResource();
        schedulableResource3b.setType(TypeB);
        schedulableResource3b.setStartTime("2030");
        schedulableResource3b.setEndTime("3380");

        collidingList.add(schedulableResource3b);

        ApiSchedulableResource schedulableResource4b = new ApiSchedulableResource();
        schedulableResource4b.setType(TypeB);
        schedulableResource4b.setStartTime("3010");
        schedulableResource4b.setEndTime("3030");

        collidingList.add(schedulableResource4b);

        ApiSchedulableResource schedulableResource5b = new ApiSchedulableResource();
        schedulableResource5b.setType(TypeB);
        schedulableResource5b.setStartTime("3460");
        schedulableResource5b.setEndTime("4323");

        collidingList.add(schedulableResource5b);

        ApiSchedulableResource schedulableResource6b = new ApiSchedulableResource();
        schedulableResource6b.setType(TypeB);
        schedulableResource6b.setStartTime("2440");
        schedulableResource6b.setEndTime("4242");

        collidingList.add(schedulableResource6b);
        //endregion

        //region Defect Resource
        if (injectDefection) {
            ApiSchedulableResource defectSchedulableResource = new ApiSchedulableResource();
            defectSchedulableResource.setType(TypeB);
            defectSchedulableResource.setStartTime("2x40");
            defectSchedulableResource.setEndTime("4242");

            collidingList.add(defectSchedulableResource);
        }
        //endregion

        //region Empty Type And IllegalTime
        if (injectEmptyTypeAndIllegalTime) {
            ApiSchedulableResource emptyTypeIllegalTimeResource = new ApiSchedulableResource();
            emptyTypeIllegalTimeResource.setType("");
            emptyTypeIllegalTimeResource.setStartTime("3210");
            emptyTypeIllegalTimeResource.setEndTime("0123");

            collidingList.add(emptyTypeIllegalTimeResource);
        }
        //endregion

        bulkRequest.setResources(collidingList);
        scheduleResource.insertIntoSchedule(bulkRequest);
    }

    @Test
    public void testInsertValidInputIntoScheduleWithCollisions_thenAllResourcesAreInserted() {
        warmUpSchedulesByResourcesTypesStoreWithCollisions(false, false);

        ApiSchedulableResourceResponse response = scheduleResource.printAndGetStoredResource();

        Assert.assertEquals("Success", response.getResponseMessage());
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
