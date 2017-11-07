package com.lawrence.ditrp.command;

/**
 * Created by mamta.lawrence on 11/6/2017.
 */

public interface ResponseListener {

    /**
     * This method will be invoked if API request is Success.
     *
     * @param response
     */
    void onSuccess(String response);

    /**
     * This method be invoke when there is an error in API request.
     *
     * @param e Can be either Network or API.
     */
    void onError(Exception e);

}
