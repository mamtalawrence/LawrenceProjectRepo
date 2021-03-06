package com.lawrence.ditrp.command;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import com.lawrence.ditrp.Enums.CommandType;
import com.lawrence.ditrp.Enums.NetworkRequestType;
import com.lawrence.ditrp.R;
import org.json.JSONException;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

/**
 * Class to perform network related task.
 */
class NetworkTask extends AsyncTask<String, Void, Boolean> {

    private static final String TAG = NetworkTask.class.getSimpleName();
    private ProgressDialog progressDialog;
    private APIRequestBuilder mApiRequestBuilder = null;
    private CommandType mCommandType;
    private Context mContext;
    private boolean isSuccess = false;

    NetworkTask(APIRequestBuilder apiRequestBuilder, CommandType requestType) {
        mApiRequestBuilder = apiRequestBuilder;
        mCommandType = requestType;
        mContext = mApiRequestBuilder.mContext;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(mContext, R.style.AppCompatAlertDialogStyle);
        if (mCommandType == CommandType.LOGIN) {
            progressDialog.setMessage("Login...");
        } else if (mCommandType == CommandType.VALIDATION) {
            progressDialog.setMessage("Checking Validation...");
        } else {
            progressDialog.setMessage("Fetching Data...");
        }
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected Boolean doInBackground(String... url) {
        Log.d("URL", url[0]);
        final String jsonString = sendPostRequestToConnectAPI(url[0]);
        Log.d("jsonString", jsonString);
        if (!TextUtils.isEmpty(jsonString) && mCommandType == CommandType.LOGIN) {
            try {
                JSONObject responseObject = new JSONObject(jsonString);
                String status = responseObject.get("success").toString();
                if (!TextUtils.isEmpty(status)) {
                    isSuccess = status.equalsIgnoreCase("true");
                }
            } catch (JSONException e) {
                Log.e(TAG, Arrays.toString(e.getStackTrace()));
            }
        }

        if (!TextUtils.isEmpty(jsonString)) {
            Log.v("json response :-", jsonString + "");
            mApiRequestBuilder.mResponseListener.onSuccess(jsonString);
        }
        return isSuccess;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
            mApiRequestBuilder.mResponseListener.onComplete(result);
        }
    }

    /**
     * Send post request to getting the response from the server.
     *
     * @return response
     */
    private String sendPostRequestToConnectAPI(String serverUrl) {
        String responseJsonData = null;
        HttpURLConnection urlConnection = null;
        try {

            URL url = new URL(serverUrl);

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(15000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            switch (mCommandType) {
                case LOGIN:
                    urlConnection.setRequestMethod(NetworkRequestType.POST.toString());
                    urlConnection.setRequestProperty("Accept", "application/json");
                    break;
                case VALIDATION:
                    urlConnection.setRequestMethod(NetworkRequestType.GET.toString());
                    break;
                case DATA_REQUEST:
                    urlConnection.setRequestMethod(NetworkRequestType.GET.toString());
                    urlConnection.setRequestProperty("Content-length", "0");
                    break;
            }
            urlConnection.setUseCaches(false);
            urlConnection.setAllowUserInteraction(false);
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.connect();// setting the connection
            //Writing data (bytes) to the data output stream
            DataOutputStream dos = new DataOutputStream(urlConnection.getOutputStream());
            switch (mCommandType) {
                case LOGIN:
                    // writing your data which you post for login
                    dos.writeBytes(getPostRequestQuery());
                    break;
                case VALIDATION:
                    // writing your data which you post for validation
                    dos.writeBytes(getValidationPostRequestQuery());
                    break;
            }
            //flushes data output stream.
            dos.flush();
            dos.close();
            int responseCode = urlConnection.getResponseCode();
            Log.d(TAG, "Connection Response Code: " + responseCode);
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                // Getting input stream from the connection to reader the response from the buffer.
                InputStreamReader inputStreamReader = new InputStreamReader(urlConnection.getInputStream());
                BufferedReader inputBufferedReader = new BufferedReader(inputStreamReader);
                String inputLine;
                StringBuilder response = new StringBuilder("");
                // Read the response and append to string builder
                while ((inputLine = inputBufferedReader.readLine()) != null) {
                    response.append(inputLine);
                }
                inputBufferedReader.close();// close the buffer reader stream
                if (mCommandType != CommandType.VALIDATION) {
                    responseJsonData = proceedForParsing(response.toString());
                } else {
                    responseJsonData = response.toString();
                }
                Log.d(TAG, "ResponseJsonData: " + responseJsonData);
            } else {
                showExceptionMessage();
            }
        } catch (Exception exception) {
            Log.e(TAG, "Exception Stack Trace: " + Arrays.toString(exception.getStackTrace()));
            showExceptionMessage();
        } finally {
            // close your connection
            if (urlConnection != null) {
                urlConnection.disconnect();
                Log.d(TAG, "Connection Disconnected");
            }
        }
        return responseJsonData;
    }

    /**
     * To show exception error message to user.
     */
    private void showExceptionMessage() {
        Exception exception = new RuntimeException("Sorry, could not connect to server.\nPlease try again later!");
        mApiRequestBuilder.mResponseListener.onError(exception);
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
                .appendQueryParameter("pword", mApiRequestBuilder.mPassword)
                .appendQueryParameter("deviceid", Settings.Secure.getString(mContext.getContentResolver(), Settings
                        .Secure.ANDROID_ID))
                .appendQueryParameter("service", "login");
        return builder.build().getEncodedQuery();
    }

    /**
     * Getting parameters data string to POST request
     *
     * @return request parameter as string
     */
    private String getValidationPostRequestQuery() {

        Uri.Builder builder = new Uri.Builder()
                .appendQueryParameter("service", mApiRequestBuilder.mCommandName)
                .appendQueryParameter("student_id", mApiRequestBuilder.mStudentId)
                .appendQueryParameter("institute_id", mApiRequestBuilder.mInstituteId);
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
            Log.d(TAG, "Json Response: " + responseData);
            JSONObject responseObject = new JSONObject(responseData);
            return responseObject.toString();
        }
        return responseData;
    }
}

