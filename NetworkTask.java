package com.lawrence.ditrp.command;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import com.lawrence.ditrp.Constants.CommandConstant;
import com.lawrence.ditrp.R;
import org.json.JSONException;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by mamta.lawrence on 11/6/2017.
 */
public class NetworkTask extends AsyncTask<Void, Void, Void> {

    private ProgressDialog progressDialog;
    private APIRequestBuilder mApiRequestBuilder = null;
    private Context mContext;

    NetworkTask(APIRequestBuilder apiRequestBuilder) {
        this.mApiRequestBuilder = apiRequestBuilder;
        mContext = mApiRequestBuilder.mContext;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(mContext, R.style.AppCompatAlertDialogStyle);
        progressDialog.setMessage("Login...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected Void doInBackground(Void... params) {
        final String jsonString = sendPostRequestToConnectAPI();
        if (!TextUtils.isEmpty(jsonString)) {
            Log.v("json response :-", jsonString + "");
            mApiRequestBuilder.mResponseListener.onSuccess(jsonString);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    /**
     * Send post request to getting the response from the server.
     *
     * @return response
     */
    private String sendPostRequestToConnectAPI() {
        String responseJsonData = null;
        HttpURLConnection urlConnection = null;
        try {

            URL url = new URL(CommandConstant.SERVER_URL);

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(15000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.connect();// setting the connection

            //Writing data (bytes) to the data output stream
            DataOutputStream dos = new DataOutputStream(urlConnection.getOutputStream());
            // writing your data which you post
            dos.writeBytes(getPostRequestQuery());
            //flushes data output stream.
            dos.flush();
            dos.close();

            int responseCode = urlConnection.getResponseCode();
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                // Getting input stream from the connection to reader the response from the buffer.
                InputStreamReader inputStreamReader = new InputStreamReader(urlConnection.getInputStream());
                BufferedReader inputBufferedReader = new BufferedReader(inputStreamReader);
                String inputLine = "";
                StringBuilder response = new StringBuilder("");
                // Read the response and append to string builder
                while ((inputLine = inputBufferedReader.readLine()) != null) {
                    response.append(inputLine);
                }
                inputBufferedReader.close();// close the buffer reader stream
                responseJsonData = proceedForParsing(response.toString());
            } else {
                Exception exception = new RuntimeException("Sorry, could not connect to server.\nPlease try again " +
                        "later!");
                mApiRequestBuilder.mResponseListener.onError(exception);
            }
        } catch (Exception e) {
            //mApiRequestBuilder.mResponseListener.onError(e);
            Exception exception = new RuntimeException("Sorry, could not connect to server.\nPlease try again later!");
            mApiRequestBuilder.mResponseListener.onError(exception);
            if (e.getMessage() != null) {
                responseJsonData = "Exception Details:".concat(e.getLocalizedMessage().concat(responseJsonData
                        .substring(0, 10)));
            } else {
                responseJsonData = "Exception Details: UnKnown Exception";
            }
            return responseJsonData;
        } finally {
            // close your connection
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return responseJsonData;
    }

    /**
     * Getting parameters data string to POST request
     *
     * @return request parameter as string
     */
    private String getPostRequestQuery() {
        Uri.Builder builder = new Uri.Builder()
                .appendQueryParameter("service", mApiRequestBuilder.mCommandName)
                .appendQueryParameter("uname", mApiRequestBuilder.mUserName)
                .appendQueryParameter("pword", mApiRequestBuilder.mPassword);
        return builder.build().getEncodedQuery();
    }

    /**
     * Getting response in the form string by convert the JsonObject
     *
     * @param responseData response data
     * @return json object as string
     * @throws JSONException thrown Json exception
     */
    private String proceedForParsing(String responseData) throws JSONException {
        if (!TextUtils.isEmpty(responseData)) {
            JSONObject responseObject = new JSONObject(responseData);
            return responseObject.toString();
        }
        return responseData;
    }
}

