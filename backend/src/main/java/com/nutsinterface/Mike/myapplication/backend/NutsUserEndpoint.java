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
        name = "nutsUserApi",
        version = "v1",
        resource = "nutsUser",
        namespace = @ApiNamespace(
                ownerDomain = "backend.myapplication.Mike.nutsinterface.com",
                ownerName = "backend.myapplication.Mike.nutsinterface.com",
                packagePath = ""
        )
)
public class NutsUserEndpoint {

    private static final Logger logger = Logger.getLogger(NutsUserEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    static {
        // Typically you would register this inside an OfyServive wrapper.
        // See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(NutsUser.class);
    }

    /**
     * Returns the {@link NutsUser} with the corresponding ID.
     *
     * @param id the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code NutsUser} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "nutsUser/{id}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public NutsUser get(@Named("id") Long id) throws NotFoundException {
        logger.info("Getting NutsUser with ID: " + id);
        NutsUser nutsUser = ofy().load().type(NutsUser.class).id(id).now();
        if (nutsUser == null) {
            throw new NotFoundException("Could not find NutsUser with ID: " + id);
        }
        return nutsUser;
    }

    /**
     * Inserts a new {@code NutsUser}.
     */
    @ApiMethod(
            name = "insert",
            path = "nutsUser",
            httpMethod = ApiMethod.HttpMethod.POST)
    public NutsUser insert(NutsUser nutsUser) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that nutsUser.id has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        ofy().save().entity(nutsUser).now();
        logger.info("Created NutsUser with ID: " + nutsUser.getId());

        return ofy().load().entity(nutsUser).now();
    }

    /**
     * Updates an existing {@code NutsUser}.
     *
     * @param id       the ID of the entity to be updated
     * @param nutsUser the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code NutsUser}
     */
    @ApiMethod(
            name = "update",
            path = "nutsUser/{id}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public NutsUser update(@Named("id") Long id, NutsUser nutsUser) throws NotFoundException {

        checkExists(id);
        ofy().save().entity(nutsUser).now();
        logger.info("Updated NutsUser: " + nutsUser);
        return ofy().load().entity(nutsUser).now();
    }

    /**
     * Deletes the specified {@code NutsUser}.
     *
     * @param id the ID of the entity to delete
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code NutsUser}
     */
    @ApiMethod(
            name = "remove",
            path = "nutsUser/{id}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("id") Long id) throws NotFoundException {
        checkExists(id);
        ofy().delete().type(NutsUser.class).id(id).now();
        logger.info("Deleted NutsUser with ID: " + id);
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
            path = "nutsUser",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<NutsUser> list(
            @Nullable @Named("cursor") String cursor,
            @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<NutsUser> query = ofy().load().type(NutsUser.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<NutsUser> queryIterator = query.iterator();
        List<NutsUser> nutsUserList = new ArrayList<NutsUser>(limit);
        while (queryIterator.hasNext()) {
            nutsUserList.add(queryIterator.next());
        }
        return CollectionResponse.<NutsUser>builder().setItems(nutsUserList)
                .setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }
    @ApiMethod(
            name="login",
            path="nutsUser/login",
            httpMethod = ApiMethod.HttpMethod.GET)
    public NutsUser login(
            @Named("userName") String userName,
            @Named("password") String password) {
        Query<NutsUser> userFound = ofy().load().type(NutsUser.class);
        userFound = userFound.filter("userName =", userName);
        QueryResultIterator<NutsUser> queryIterator = userFound.iterator();
        List<NutsUser> nutsUserList = new ArrayList<NutsUser>(100);
        /*while (queryIterator.hasNext()) {
            nutsUserList.add(queryIterator.next());
        }*/
        if (queryIterator.hasNext()) {
            NutsUser n_user = queryIterator.next();
            if (n_user.getPassword().equals(password)) {
                return n_user;
            }
        }
        return null;
       /* if (userFound.count() == 0) {
            return null;
        }
        else if (userFound.count() > 1) {
            return null;
        }
        else {
            return userFound.first().now();
        }*/
    }
    @ApiMethod(
            name="register",
            path="nutsUser/register",
            httpMethod = ApiMethod.HttpMethod.POST)
    public NutsUser register(NutsUser nutsUser) {
        Query<NutsUser> userFound = ofy().load().type(NutsUser.class);

        userFound = userFound.filter("userName =", nutsUser.getUserName());
        if (userFound.count() == 0) {
            /*
            String userName, String password, String name, String email, String telephone

             */
            //NutsUser nu = new NutsUser();//userName, password, name, email, telephone);
            ofy().save().entity(nutsUser).now();
            return ofy().load().entity(nutsUser).now();
        }
        return null;
    }
    @ApiMethod(
            name="getContactInfo",
            path="nutsUser/getContactInfo",
            httpMethod = ApiMethod.HttpMethod.GET)
    public void getContactInfo(
            @Named("id") Long id,
            @Named("info") List<String> info) throws NotFoundException {
        NutsUser nutsUser = ofy().load().type(NutsUser.class).id(id).now();
        if (nutsUser == null) {
            throw new NotFoundException("Could not find NutsUser with ID: " + id);
        }
        info.add(nutsUser.getTelephone());
        info.add(nutsUser.getName());
        info.add(nutsUser.getEmail());
        return;
    }

    private void checkExists(Long id) throws NotFoundException {
        try {
            ofy().load().type(NutsUser.class).id(id).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find NutsUser with ID: " + id);
        }
    }
}