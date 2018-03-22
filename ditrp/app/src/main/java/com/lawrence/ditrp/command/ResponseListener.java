package com.lawrence.ditrp.command;

interface ResponseListener {

    /**
     * This method will be invoked if API request is Success.
     *
     * @param response success reponse
     */
    void onSuccess(String response);

    /**
     * This method be invoke when there is an error in API request.
     *
     * @param e Can be either Network or API.
     */
    void onError(Exception e);

    /**
     * Invoked when API request completed after doing all the sever related tasks.
     *
     * @param status completed status
     */
    void onComplete(boolean status);

}
