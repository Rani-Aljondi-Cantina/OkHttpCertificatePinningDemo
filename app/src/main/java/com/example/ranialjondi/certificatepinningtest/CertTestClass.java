package com.example.ranialjondi.certificatepinningtest;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Callable;

import javax.net.ssl.SSLPeerUnverifiedException;

import okhttp3.Call;
import okhttp3.CertificatePinner;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by rani.aljondi on 10/29/17.
 */

public class CertTestClass {
    final String TAG = "CertTestClass";

    /**
     *
     * @param okHttpClient
     * @param prefix
     * @param hostName
     * @return String htmlString or null if response.body().string()
     */
    public static Response sendRequest(final OkHttpClient okHttpClient, String prefix, String hostName) throws Exception {

        String htmlString = null;

        final Request request = new Request.Builder()
                .url(prefix + hostName)
                .build();

        Callable<Response> okHttpCall = new Callable<Response>() {
            @Override
            public Response call() {
                try {
                    Call call = okHttpClient.newCall(request);
                    Response response = call.execute();
                    ResponseBody body = response.body();
                    return response;
                } catch(SSLPeerUnverifiedException spue) {
                    spue.printStackTrace();
                    return null;
                } catch(IOException ioe) {
                    ioe.printStackTrace();
                    return null;
                }
            }
        };

        return okHttpCall.call();
    }


}
