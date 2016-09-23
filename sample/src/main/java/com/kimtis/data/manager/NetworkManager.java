package com.kimtis.data.manager;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.kimtis.MyApplication;
import com.kimtis.data.CorrectedDataResult;
import com.kimtis.data.NavigationHeader;
import com.kimtis.data.PRCDataResult;
import com.kimtis.data.constant.NetworkConstant;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.MySSLSocketFactory;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Arrays;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.client.HttpClient;

/**
 * Created by uiseok on 2016-09-06.
 */
public class NetworkManager {

    /* SingleTon Design */

    private static NetworkManager instance;

    public static NetworkManager getInstnace() {
        if (instance == null) {
            instance = new NetworkManager();
        }
        return instance;
    }

    AsyncHttpClient client;

    Gson gson;

    private NetworkManager() {

        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore
                    .getDefaultType());
            trustStore.load(null, null);
            MySSLSocketFactory socketFactory = new MySSLSocketFactory(
                    trustStore);
            socketFactory
                    .setHostnameVerifier(MySSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            client = new AsyncHttpClient();
            client.setSSLSocketFactory(socketFactory);
            client.setCookieStore(new PersistentCookieStore(MyApplication
                    .getContext()));
            client.setTimeout(10000);
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        }

        gson = new Gson();
    }

    public HttpClient getHttpClient() {
        return client.getHttpClient();
    }

    public interface OnResultListener<T> {
        public void onSuccess(T result);

        public void onFail(int code);
    }

    public void cancleAll(Context context) {
        client.cancelRequests(context, true);
    }


    public void getPRCData(Context context, String lat, String lon, Integer[] prn, final OnResultListener<PRCDataResult> listener){

        RequestParams params = new RequestParams();
        params.put("lat", lat);
        params.put("lon", lon);
        params.put("prns", Arrays.toString(prn));




        client.get(context, NetworkConstant.PRCConstant.SEARCH_URL, params,
                new TextHttpResponseHandler() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers,
                                          String responseString) {
                        try {
                            PRCDataResult result = gson.fromJson(responseString,
                                    PRCDataResult.class);
                            Log.d("outputData", result.toString());
                            listener.onSuccess(result);
                        } catch (Exception e) {
                            listener.onFail(444);
                        }

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers,
                                          String responseString, Throwable throwable) {
                        listener.onFail(statusCode);
                    }
                });
    }

    public void getNavigationHeader(Context context, final OnResultListener<NavigationHeader> listener){
        client.get(context, NetworkConstant.NavigationHeaderConstant.SEARCH_URL, null, new TextHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                NavigationHeader result = gson.fromJson(responseString, NavigationHeader.class);

                listener.onSuccess(result);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                listener.onFail(statusCode);
            }
        });
    }

    public void getCorrectData(Context context, final OnResultListener<CorrectedDataResult> listener) {

//        JSONObject jsonParams = new JSONObject();
//
//        try {
//            jsonParams.put(LoginConstant.EMAIL, email);
//            jsonParams.put(LoginConstant.PASSWORD, password);
//            jsonParams.put(LoginConstant.DEVICE, device);
//
//        } catch (JSONException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//        StringEntity entity = null;
//        try {
//            entity = new StringEntity(jsonParams.toString(), "utf-8");
//        } catch (UnsupportedEncodingException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//        Log.d("inputData", entity.toString());
//        client.post(context, LoginConstant.SEARCH_URL, entity,
//                "application/json", new TextHttpResponseHandler() {
//
//                    @Override
//                    public void onSuccess(int statusCode, Header[] headers,
//                                          String responseString) {
//                        try {
//                            CorrectedDataResult result = gson.fromJson(responseString,
//                                    CorrectedDataResult.class);
//                            Log.d("outputData", result.toString());
//                            listener.onSuccess(result);
//                        } catch (Exception e) {
//                            listener.onFail(444);
//                        }
//
//                    }
//
//                    @Override
//                    public void onFailure(int statusCode, Header[] headers,
//                                          String responseString, Throwable throwable) {
//                        listener.onFail(statusCode);
//                    }
//                });

    }


}
