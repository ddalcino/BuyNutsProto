package com.nutsinterface.Mike.myapplication.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Nullable;
import javax.inject.Named;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * WARNING: This generated code is intended as a sample or starting point for using a
 * Google Cloud Endpoints RESTful API with an Objectify entity. It provides no data access
 * restrictions and no data validation.
 * <p/>
 * DO NOT deploy this code unchanged as part of a real application to real users.
 */
@Api(
        name = "offerFilterEndpoint",
        version = "v1",
        resource = "offerFilter",
        namespace = @ApiNamespace(
                ownerDomain = "backend.myapplication.Mike.nutsinterface.com",
                ownerName = "backend.myapplication.Mike.nutsinterface.com",
                packagePath = ""
        )
)
public class OfferFilterEndpoint {

    private static final Logger logger = Logger.getLogger(OfferFilterEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(OfferFilter.class);
    }

    /**
     * Returns the {@link OfferFilter} with the corresponding ID.
     *
     * @param Id the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code OfferFilter} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "offerFilter/{Id}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public OfferFilter get(@Named("Id") Long Id) throws NotFoundException {
        logger.info("Getting OfferFilter with ID: " + Id);
        OfferFilter offerFilter = ofy().load().type(OfferFilter.class).id(Id).now();
        if (offerFilter == null) {
            throw new NotFoundException("Could not find OfferFilter with ID: " + Id);
        }
        return offerFilter;
    }

    /**
     * Inserts a new {@code OfferFilter}.
     */
    @ApiMethod(
            name = "insert",
            path = "offerFilter",
            httpMethod = ApiMethod.HttpMethod.POST)
    public OfferFilter insert(OfferFilter offerFilter) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that offerFilter.Id has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        ofy().save().entity(offerFilter).now();
        logger.info("Created OfferFilter.");

        return ofy().load().entity(offerFilter).now();
    }

    /**
     * Updates an existing {@code OfferFilter}.
     *
     * @param Id          the ID of the entity to be updated
     * @param offerFilter the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code Id} does not correspond to an existing
     *                           {@code OfferFilter}
     */
    @ApiMethod(
            name = "update",
            path = "offerFilter/{Id}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public OfferFilter update(@Named("Id") Long Id, OfferFilter offerFilter) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(Id);
        ofy().save().entity(offerFilter).now();
        logger.info("Updated OfferFilter: " + offerFilter);
        return ofy().load().entity(offerFilter).now();
    }

    /**
     * Deletes the specified {@code OfferFilter}.
     *
     * @param Id the ID of the entity to delete
     * @throws NotFoundException if the {@code Id} does not correspond to an existing
     *                           {@code OfferFilter}
     */
    @ApiMethod(
            name = "remove",
            path = "offerFilter/{Id}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("Id") Long Id) throws NotFoundException {
        checkExists(Id);
        ofy().delete().type(OfferFilter.class).id(Id).now();
        logger.info("Deleted OfferFilter with ID: " + Id);
    }

    /**
     * List all entities.
     *
     * @param cursor used for pagination to determine which page to return
     * @param limit  the maximum number of entries to return
     * @return a response that encapsulates the result list and the next page token/cursor
     */
    @ApiMethod(
            name = "list",
            path = "offerFilter",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<OfferFilter> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<OfferFilter> query = ofy().load().type(OfferFilter.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<OfferFilter> queryIterator = query.iterator();
        List<OfferFilter> offerFilterList = new ArrayList<OfferFilter>(limit);
        while (queryIterator.hasNext()) {
            offerFilterList.add(queryIterator.next());
        }
        return CollectionResponse.<OfferFilter>builder().setItems(offerFilterList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }
    @ApiMethod(
            name="submitFilter",
            path="offerFilter/submit",
            httpMethod = ApiMethod.HttpMethod.POST
    )
    public OfferFilter submitFilter(OfferFilter offerFilter) {
        Query<OfferFilter> offerFound = ofy().load().type(OfferFilter.class);

        offerFound = offerFound.filter("associatedUserID =", offerFilter.getAssociatedUserID());
        QueryResultIterator<OfferFilter> queryIterator = offerFound.iterator();

        if (!queryIterator.hasNext()) {
            /*
            String userName, String password, String name, String email, String telephone

             */
            //NutsUser nu = new NutsUser();//userName, password, name, email, telephone);
            ofy().save().entity(offerFilter).now();
            return ofy().load().entity(offerFilter).now();
        }
        else {
            OfferFilter oldOfferFilter = queryIterator.next();
            try {
                checkExists(oldOfferFilter.getId());
                update(oldOfferFilter.getId(),offerFilter);
            }
            catch(NotFoundException ne) {
                return null;
            }
            /*
            while (queryIterator.hasNext()) {

                OfferFilter q = queryIterator.next();
                queryIterator.remove();
                try {
                    checkExists(q.getId());
                    remove(q.getId());
                }
                catch (NotFoundException e) {
                    return null;
                }
                */

                //queryIterator.remove();
                //ofy().delete().entity(q).now();
                //queryIterator.remove();
            }
/*
            //OfferFilter of = offerFound.first().safe();
            //ofy().load().entity(of).now();
            //ofy().delete().entity(of).now();
            ofy().save().entity(offerFilter).now();
            return ofy().load().entity(offerFilter).now();
        */return offerFilter;
        }


        //return null;

    @ApiMethod(
            name="retrieveFilter",
            path="offerFilter/retrieve",
            httpMethod = ApiMethod.HttpMethod.GET)
    public OfferFilter retrieveFilter(@Named("associatedUserID") Long associatedUserID) {
        Query<OfferFilter> offerFound = ofy().load().type(OfferFilter.class);
        offerFound = offerFound.filter("associatedUserID =", associatedUserID);
        QueryResultIterator<OfferFilter> queryIterator = offerFound.iterator();
        if (queryIterator.hasNext()) {
            OfferFilter retrieved = queryIterator.next();
            return retrieved;
            }

        else {
            return null;
        }



        //return null;
    }


    private void checkExists(Long Id) throws NotFoundException {
        try {
            ofy().load().type(OfferFilter.class).id(Id).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find OfferFilter with ID: " + Id);
        }
    }
}