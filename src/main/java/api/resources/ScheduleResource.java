package api.resources;

import api.converters.ApiSchedulableResourceConverter;
import api.model.ApiIdentifiableSchedulableResource;
import api.model.ApiSchedulableResource;
import api.requests.ApiAllCollisionsRequest;
import api.requests.ApiAvailabilityStatusRequest;
import api.requests.ApiFirstCollisionRequest;
import api.requests.ApiSchedulableResourceBulkRequest;
import api.requests.helpers.RequestHelper;
import api.responses.*;
import bl.cmds.AllCollisionsCmd;
import bl.cmds.FirstCollisionCmd;
import bl.cmds.ResourceAvailabilityStatusCmd;
import bl.cmds.ScheduleResourcesCmd;
import bl.model.BlSchedulableResource;
import bl.model.CompatibleResourcesGroup;
import com.codahale.metrics.annotation.Timed;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.*;
import java.util.stream.Collectors;

/**
 * author Miara Omer @ 08/08/2022
 */

@Path("/schedule/resource")
public class ScheduleResource {

    private static Logger LOGGER = LoggerFactory.getLogger(ScheduleResource.class);

    //region Constants
    private final String  ERROR                  = "Error";
    private final String  SUCCESS                = "Success";
    private final String  NO_COLLISION           = "No collision";
    private final Integer FIRST_ELEMENT_LOCATION = 0;
    //endregion

    //region Store
    public Map<String, List<CompatibleResourcesGroup>> schedulesByResourcesTypesStore = new HashMap<>();
    public Set<String>                                 resourceTypesStore             = new HashSet<>();
    public List<BlSchedulableResource>                 sortedResourcesStore           = new LinkedList<>();
    //endregion


    @POST
    @Timed
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/bulk")
    public ApiSchedulableResourceResponse insertIntoSchedule(@Valid ApiSchedulableResourceBulkRequest request) {
        LOGGER.info("Start handling resource scheduling");

        ApiSchedulableResourceResponse retVal = new ApiSchedulableResourceResponse();

        String validationResponse = RequestHelper.validateBulkRequest(request);

        if (StringUtils.isBlank(validationResponse)) {
            List<ApiSchedulableResource> apiSchedulableResources = request.getResources();
            List<BlSchedulableResource> schedulableResources = apiSchedulableResources.stream().
                    map(ApiSchedulableResourceConverter::apiToBl).
                    collect(Collectors.toList());

            ScheduleResourcesCmd cmd = new ScheduleResourcesCmd();

            List<BlSchedulableResource> cmdResponse = cmd.execute(schedulableResources, schedulesByResourcesTypesStore);

            //init all sorted resources into store
            sortedResourcesStore = cmdResponse;

            //init types store
            resourceTypesStore =
                    schedulableResources.stream().map(BlSchedulableResource::getType).collect(Collectors.toSet());


            List<ApiSchedulableResource> response = schedulableResources.stream().
                    map(ApiSchedulableResourceConverter::blToApi).
                    collect(Collectors.toList());

            retVal.setResources(response);
            retVal.setResponseMessage(SUCCESS);
        }
        else {
            String errMsg = ERROR + validationResponse;
            retVal.setResponseMessage(errMsg);
            LOGGER.info("Failed to handle resources scheduling");
        }

        return retVal;
    }

    @GET
    @Timed
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/")
    public ApiIdentifiableSchedulableResponse getResources() {
        LOGGER.info("Start getting all scheduled resources");

        ApiIdentifiableSchedulableResponse response = null;

        if (CollectionUtils.isNotEmpty(sortedResourcesStore)) {
            response = new ApiIdentifiableSchedulableResponse();
            List<ApiIdentifiableSchedulableResource> apiResources =
                    sortedResourcesStore.stream().map(ApiSchedulableResourceConverter::identifiableBlToApi).
                            collect(Collectors.toList());

            response.setResources(apiResources);
        }

        LOGGER.info("Finished getting all scheduled resources");

        return response;
    }

    @GET
    @Timed
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/firstCollision")
    public ApiFirstCollisionResponse getFirstCollisionForResourceType(@Valid ApiFirstCollisionRequest request) {
        String type = request.getResourceId();
        LOGGER.info("Getting first collision on type {}", type);

        FirstCollisionCmd           cmd       = new FirstCollisionCmd();
        List<BlSchedulableResource> resources = cmd.execute(schedulesByResourcesTypesStore, resourceTypesStore, type);

        ApiFirstCollisionResponse retVal = new ApiFirstCollisionResponse();
        if (CollectionUtils.isNotEmpty(resources)) {
            List<ApiIdentifiableSchedulableResource> apiSchedulableResources =
                    resources.stream().map(ApiSchedulableResourceConverter::identifiableBlToApi).collect(Collectors.toList());

            retVal.setResponseMessage(SUCCESS);
            retVal.setFirstCollidingResource(apiSchedulableResources);
        }
        else {
            retVal.setResponseMessage(NO_COLLISION);
        }

        return retVal;
    }

    @GET
    @Timed
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/availability")
    public ApiAvailabilityStatusResponse getAvailabilityStatus(@Valid ApiAvailabilityStatusRequest request) {
        ApiAvailabilityStatusResponse retVal = new ApiAvailabilityStatusResponse();

        String validationResponse = RequestHelper.validateTimestamp(request.getTime());
        String availabilityStatus = null;

        if (StringUtils.isBlank(validationResponse)) {
            String type      = request.getResourceId();
            String timestamp = request.getTime();

            LOGGER.info("Getting first collision on type {}", type);

            ResourceAvailabilityStatusCmd cmd = new ResourceAvailabilityStatusCmd();

            availabilityStatus = cmd.execute(schedulesByResourcesTypesStore, resourceTypesStore, type, timestamp);
            retVal.setRequestStatus(SUCCESS);
        }
        else {
            String errMsg = ERROR + ". " + validationResponse;
            retVal.setRequestStatus(errMsg);
        }
        retVal.setAvailabilityStatus(availabilityStatus);

        return retVal;
    }

    @GET
    @Timed
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/allCollisions")
    public ApiAllCollisionsResponse getAllCollisions(@Valid ApiAllCollisionsRequest request) {

        AllCollisionsCmd                  cmd            = new AllCollisionsCmd();
        List<List<BlSchedulableResource>> collisionsList = cmd.execute(sortedResourcesStore);

        ApiAllCollisionsResponse    retVal                = new ApiAllCollisionsResponse();
        List<ApiCollidingResources> apiCollidingResources = new ArrayList<>();
        retVal.setCollisionsList(apiCollidingResources);

        if (CollectionUtils.isNotEmpty(collisionsList)) {
            retVal.setStatus(SUCCESS);
            for (List<BlSchedulableResource> blCollisionsList : collisionsList) {
                ApiCollidingResources collisions = new ApiCollidingResources();

                if (CollectionUtils.isNotEmpty(blCollisionsList)) {
                    BlSchedulableResource blSchedulableResource = blCollisionsList.get(FIRST_ELEMENT_LOCATION);
                    blCollisionsList.remove(blSchedulableResource);

                    ApiIdentifiableSchedulableResource resource =
                            ApiSchedulableResourceConverter
                                    .identifiableBlToApi(blSchedulableResource);

                    collisions.setResource(resource);

                    List<ApiIdentifiableSchedulableResource> identifiableSchedulableResources =
                            blCollisionsList.stream().
                                    map(ApiSchedulableResourceConverter::identifiableBlToApi).
                                    collect(Collectors.toList());


                    collisions.setCollisions(identifiableSchedulableResources);
                }
                retVal.getCollisionsList().add(collisions);
            }
        }
        else {
            String msg;

            if (collisionsList == null) {
                msg = "resources store is empty";
            }
            else {
                msg = "No collisions";
            }

            retVal.setStatus(msg);
        }
        return retVal;
    }


}
