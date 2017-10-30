package com.example.ranialjondi.certificatepinningtest;

import android.os.AsyncTask;
import android.util.Log;
import android.webkit.WebView;

import java.io.IOException;
import java.util.ArrayList;

import javax.net.ssl.SSLPeerUnverifiedException;

import okhttp3.CertificatePinner;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by rani.aljondi on 10/29/17.
 */

public class CertTestClass {


    /**
     *
     * @param okHttpClient
     * @param prefix
     * @param hostName
     * @return String htmlString or null if response.body().string()
     */
    public static String sendRequest(OkHttpClient okHttpClient, String prefix, String hostName) throws IOException {

        String htmlString;

        Request request = new Request.Builder()
                .url(prefix + hostName)
                .build();

        Response response = okHttpClient.newCall(request).execute();
        htmlString = response.body().string();

        return htmlString;
    }

    class PinnerTesterTask extends AsyncTask {

        String html = "";


        @Override
        protected Object doInBackground(Object[] params) {
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
        }

        @Override
        protected void onProgressUpdate(Object[] values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onCancelled(Object o) {
            super.onCancelled(o);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }

}
