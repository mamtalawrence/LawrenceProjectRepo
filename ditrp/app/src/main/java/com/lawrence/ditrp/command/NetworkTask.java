package com.lawrence.ditrp.command;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import com.lawrence.ditrp.Constants.CommandConstant;
import com.lawrence.ditrp.R;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mamta.lawrence on 11/6/2017.
 */
public class NetworkTask extends AsyncTask<Void, Void, Void> {

    private ProgressDialog progressDialog;
    private boolean isConnect = false;
    private APIRequestBuilder mApiRequestBuilder = null;

    public NetworkTask(APIRequestBuilder apiRequestBuilder) {
        this.mApiRequestBuilder = apiRequestBuilder;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(mApiRequestBuilder.mContext, R.style.AppCompatAlertDialogStyle);
        progressDialog.setMessage("Login...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            final String jsonString = connectAPI(getHttpPostParams());
            if (jsonString != null) {
                Log.e("json response :-", jsonString + "");
                isConnect = true;
                mApiRequestBuilder.mResponseListener.onSuccess(jsonString);
            } else {
                isConnect = false;
            }
            // Validate User End
        } catch (Exception e) {
            isConnect = false;
            e.printStackTrace();
        } finally {
            if (!isConnect) {
                Exception e = new RuntimeException("Sorry, could not connect to server.\nPlease try again later!");
                mApiRequestBuilder.mResponseListener.onError(e);
            }
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
                progressDialog = null;
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
    }

    /**
     * Add param in request
     *
     * @return httpPostParams object
     */
    private List<NameValuePair> getHttpPostParams() {
        // Validate User
        List<NameValuePair> httpPostParams = null;
        httpPostParams = new ArrayList<NameValuePair>();
        httpPostParams.add(new BasicNameValuePair("service", mApiRequestBuilder.mCommandName));
        httpPostParams.add(new BasicNameValuePair("uname", mApiRequestBuilder.mUserName));
        httpPostParams.add(new BasicNameValuePair("pword", mApiRequestBuilder.mPassword));
        return httpPostParams;
    }

    /**
     * @param rowJsonString
     * @return
     * @throws JSONException
     */
    @SuppressWarnings("null")
    private String proceedForParsing(String rowJsonString) throws JSONException {

        if (rowJsonString != null || rowJsonString.length() > 1) {
            JSONObject responseObject = new JSONObject(rowJsonString);
            return responseObject.toString();
        }
        return rowJsonString;

    }

    /**
     * Http API call
     *
     * @param httpPostParams
     * @return
     */
    public String connectAPI(List<NameValuePair> httpPostParams) {
        String rowJsonString = null;
        try {
            HttpPost connectionMethod = new HttpPost(CommandConstant.SERVER_URL);
            HttpParams params = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(params, 600000);
            HttpConnectionParams.setSoTimeout(params, 600000);
            DefaultHttpClient httpClient = new DefaultHttpClient(params);

            if (httpClient != null) {
                connectionMethod.setEntity(new UrlEncodedFormEntity(httpPostParams, "UTF-8"));
                HttpResponse httpResponse = httpClient.execute(connectionMethod);

                if (httpResponse != null) {
                    StatusLine statusLine = httpResponse.getStatusLine();
                    switch (statusLine.getStatusCode()) {
                        case HttpStatus.SC_OK: {
                            rowJsonString = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
                            rowJsonString = proceedForParsing(rowJsonString);
                        }
                        break;
                    }
                }
                httpClient.getConnectionManager().shutdown();
                httpClient = null;
            }
        } catch (Exception e) {
            mApiRequestBuilder.mResponseListener.onError(e);
            if (e.getMessage() != null) {
                rowJsonString = "Exception Details:".concat(e.getLocalizedMessage().concat(rowJsonString.substring(0,
                        10)));
            } else {
                rowJsonString = "Exception Details: UnKnown Exception";
            }

            return rowJsonString;
        }
        return rowJsonString;
    }
}

