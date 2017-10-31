package com.example.ranialjondi.certificatepinningtest;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

import javax.net.ssl.SSLPeerUnverifiedException;

import okhttp3.Call;
import okhttp3.CertificatePinner;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {

    final String TAG = "MainActivityTag";


    /**
     * Tests are rudimentary: will reject host path if intermediate CA doesn't contain a pin in the pinstore. Demonstrates
     * how pinning imposes requirements on certificate to whatever client wants.
     *
     *
     * Certificate pinning handled via fallback method: certificate only needs to contain any one of the keys in the truststore to be accepted.
     *
     *
     * To see different scenarios: change certificatePinner in okHttpClient initialization to one of the ceritificate pinners below
     *
     * if a valid intermediate key is used: will open webview with content on publicobject.com
     * otherwise, will return
     */
    String prefix = "http://";
    String hostname = "publicobject.com";
    String intermediateKey1 = "sha256/afwiKY3RxoMmLkuRW1l7QsPZTJPwDS2pdDROQjXw8ig=";
    String absentIntermediateKey1 = "sha256/AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA=";
    String intermediateKey2 = "sha256/klO23nT2ehFDXCfx3eHTDRESMz3asj1muO+4aIdjiuY=";
    String intermediateKey3 = "sha256/grX4Ta9HpZx6tSHkmCrvpApTQGo67CYDnvprLg5yRME=";


    //will get rejected
    CertificatePinner certificatePinner1 = new CertificatePinner.Builder()
            .add(hostname, absentIntermediateKey1)
            .build();

    //will be accepted
    CertificatePinner certificatePinner2 = new CertificatePinner.Builder()
            .add(hostname, intermediateKey1)
            .add(hostname, absentIntermediateKey1)
            .build();


    OkHttpClient okHttpClient = new OkHttpClient.Builder().certificatePinner(certificatePinner2).build();

    Request request = new Request.Builder()
            .url("https://" + hostname)
            .build();

    WebView webView;

    Intent intent;

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intent = new Intent(this, WebViewActivity.class);
        webView = (WebView) findViewById(R.id.import_web_view);

        button = (Button) findViewById(R.id.button_good_keyset);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PinnerTesterTask().execute();
            }
        });

    }

    private class PinnerTesterTask extends AsyncTask<Void, Void, Boolean> {

        String html = "";

        Response run() throws IOException {
            Response response;
            Call call = okHttpClient.newCall(request);
            Log.e(TAG, ""+ call.toString());
            response = call.execute();
            return response;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                Response response = run();
                Log.e(TAG, response.code()+"");
                ResponseBody body = response.body();
                if(body==null) {
                    throw new NullPointerException("Response not received from host");
                }
                else html = body.string();
            }
            catch(SSLPeerUnverifiedException spu) {
                return false;
            }
            catch(IOException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean certificatePinFound) {
            super.onPostExecute(certificatePinFound);
            if(!certificatePinFound) {
                Toast toast = Toast.makeText(getApplicationContext(),"Certificate Pinning Failure! Public key not found!",Toast.LENGTH_SHORT);
                toast.show();
            }
            Bundle bundle = new Bundle();
            Log.e(TAG, html);
            bundle.putString(Keys.htmlKey, html);
            intent.putExtras(bundle);
            startActivity(intent);
            //webView.loadData(html, "text/html; charset=utf-8", "UTF-8");
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }

}
