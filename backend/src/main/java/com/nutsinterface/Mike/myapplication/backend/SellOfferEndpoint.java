package com.nutsinterface.Mike.myapplication.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.users.User;
import com.googlecode.objectify.cmd.Query;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Nullable;
import javax.inject.Named;

import static com.nutsinterface.Mike.myapplication.backend.OfyService.ofy;

/**
 * WARNING: This generated code is intended as a sample or starting point for using a
 * Google Cloud Endpoints RESTful API with an Objectify entity. It provides no data access
 * restrictions and no data validation.
 * <p/>
 * DO NOT deploy this code unchanged as part of a real application to real users.
 */
@Api(
        name = "sellOfferEndpoint",
        version = "v1",
        //scopes = {Constants.EMAIL_SCOPE},
        //clientIds = {Constants.WEB_CLIENT_ID},
        //audiences = {Constants.ANDROID_AUDIENCE}
        namespace = @ApiNamespace(
                ownerDomain = "backend.myapplication.Mike.nutsinterface.com",
                ownerName = "backend.myapplication.Mike.nutsinterface.com",
                packagePath = ""
        )
)
public class SellOfferEndpoint {

    private static final Logger logger = Logger.getLogger(SellOfferEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;
    public SellOfferEndpoint() { }
    /*static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(SellOffer.class);
    }*/

    /**
     * Returns the {@link SellOffer} with the corresponding ID.
     *
     * @param id the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code SellOffer} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "sellOffer/{id}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public SellOffer get(@Named("id") Long id) throws NotFoundException {
        logger.info("Getting SellOffer with ID: " + id);
        SellOffer sellOffer = ofy().load().type(SellOffer.class).id(id).now();
        if (sellOffer == null) {
            throw new NotFoundException("Could not find SellOffer with ID: " + id);
        }
        return sellOffer;
    }

    /**
     * Inserts a new {@code SellOffer}.
     */
    @ApiMethod(name = "insert")
    public SellOffer insert(User user, SellOffer sellOffer) throws OAuthRequestException  {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that sellOffer.id has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        String sellerId;
        if (user != null) {
            sellerId = user.getUserId();
        }
        else {
            throw new OAuthRequestException("Invalid user.");
        }
        Calendar today = Calendar.getInstance();
        sellOffer.setOfferBirthday(today.toString());
        sellOffer.setSeller_id(sellerId);


        ofy().save().entity(sellOffer).now();
        logger.info("Created SellOffer with ID: " + sellOffer.getId() + "from user " + user.getEmail());

        return sellOffer;
    }
    @ApiMethod (
            name = "listSellerCommodities",
            path = "sellOffer/sellers/{seller_id}/offers",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<SellOffer> listSellerCommodities(
            @Named("seller_id") String seller_id,
            @Nullable @Named("cursor") String cursor,
            @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<SellOffer> query = ofy().load().type(SellOffer.class).limit(limit).filter("seller_id =",seller_id);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<SellOffer> queryIterator = query.iterator();
        List<SellOffer> sellOfferList = new ArrayList<SellOffer>(limit);
        while (queryIterator.hasNext()) {
            sellOfferList.add(queryIterator.next());
        }
        return CollectionResponse.<SellOffer>builder().setItems(sellOfferList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }
    @ApiMethod (
            name = "listCommodityOffers",
            path = "sellOffer/commodity/{commodity}/offers",
            httpMethod = ApiMethod.HttpMethod.GET
    )
    public CollectionResponse<SellOffer> listCommodityOffers(
            @Named("commodity") String commodity,
            @Nullable @Named("cursor") String cursor,
            @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<SellOffer> query = ofy().load().type(SellOffer.class).limit(limit).filter("commodity =",commodity).order("price_per_unit");
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<SellOffer> queryIterator = query.iterator();
        List<SellOffer> sellOfferList = new ArrayList<SellOffer>(limit);
        while (queryIterator.hasNext()) {
            sellOfferList.add(queryIterator.next());
        }
        return CollectionResponse.<SellOffer>builder().setItems(sellOfferList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }
    @ApiMethod(
            name = "fullQueryOffers",
            path = "sellOffer/fullQuery",
            httpMethod = ApiMethod.HttpMethod.GET
    )
    public CollectionResponse<SellOffer> fullQueryOffers(
            @Nullable @Named("commodity") String commodity,
            @Nullable @Named("seller_id") String seller_id,
            @Nullable @Named("min_weight") Double min_weight,
            @Nullable @Named("max_weight") Double max_weight,
            @Nullable @Named("cursor") String cursor,
            @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;

        Query<SellOffer> query = ofy().load().type(SellOffer.class).limit(limit);

        if (commodity != null) {
            query = query.filter("commodity =",commodity);
        }
      /*  if(max_price != null && min_price != null) {
            query = query.filter("price_per_unit <=", max_price).filter("price_per_unit >=",min_price);
        }
        else if (min_price != null) {
            query = query.filter("price_per_unit >=", min_price);
        }
        else if (max_price != null) {
            query = query.filter("price_per_unit <=", max_price);
        }*/
        if (seller_id != null) {
            query = query.filter("seller_id =",seller_id);
    }
        if (min_weight != null && max_weight != null) {
            query = query.filter("min_weight >=", min_weight).filter("min_weight <=", max_weight);
        }
        else if (min_weight != null) {
            query = query.filter(" min_weight>=", min_weight);
        }

        else if (max_weight != null) {
            query = query.filter("min_weight <=", max_weight);
        }

        query.order("price_per_unit");
        QueryResultIterator<SellOffer> queryIterator = query.iterator();
        List<SellOffer> sellOfferList = new ArrayList<SellOffer>(limit);
        while (queryIterator.hasNext()) {
            sellOfferList.add(queryIterator.next());
        }
        return CollectionResponse.<SellOffer>builder().setItems(sellOfferList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();

    }

    /**
     * Updates an existing {@code SellOffer}.
     *
     * @param id        the ID of the entity to be updated
     * @param sellOffer the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code SellOffer}
     */
    @ApiMethod(
            name = "update",
            path = "sellOffer/{id}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public SellOffer update(@Named("id") Long id, SellOffer sellOffer) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(id);
        ofy().save().entity(sellOffer).now();
        logger.info("Updated SellOffer: " + sellOffer);
        return ofy().load().entity(sellOffer).now();
    }

    /**
     * Deletes the specified {@code SellOffer}.
     *
     * @param id the ID of the entity to delete
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code SellOffer}
     */
    @ApiMethod(
            name = "remove",
            path = "sellOffer/{id}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(User user, @Named("id") Long id) throws NotFoundException {
        //if (user==null) {
         //   throw new OAuthRequestException("Invalid User");
       // }
        checkExists(id);
        ofy().delete().type(SellOffer.class).id(id).now();
        logger.info("Deleted SellOffer with ID: " + id);
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
            path = "sellOffer",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<SellOffer> list(
            @Nullable @Named("cursor") String cursor,
            @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<SellOffer> query = ofy().load().type(SellOffer.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<SellOffer> queryIterator = query.iterator();
        List<SellOffer> sellOfferList = new ArrayList<SellOffer>(limit);
        while (queryIterator.hasNext()) {
            sellOfferList.add(queryIterator.next());
        }
        return CollectionResponse.<SellOffer>builder().setItems(sellOfferList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }
    @ApiMethod(

    )
    private void checkExists(Long id) throws NotFoundException {
        try {
            ofy().load().type(SellOffer.class).id(id).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find SellOffer with ID: " + id);
        }
    }
}