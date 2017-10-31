package com.example.ranialjondi.certificatepinningtest;

import org.junit.Test;

import okhttp3.CertificatePinner;
import okhttp3.OkHttpClient;

import static org.junit.Assert.*;

/**
 * Created by rani.aljondi on 10/31/17.
 */
public class CertTestClassUnitTest {

    private String prefix = "http://";
    private String hostname = "publicobject.com";
    private String intermediateKey1 = "sha256/afwiKY3RxoMmLkuRW1l7QsPZTJPwDS2pdDROQjXw8ig=";
    private String absentIntermediateKey1 = "sha256/AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA=";
    private String intermediateKey2 = "sha256/klO23nT2ehFDXCfx3eHTDRESMz3asj1muO+4aIdjiuY=";
    private String intermediateKey3 = "sha256/grX4Ta9HpZx6tSHkmCrvpApTQGo67CYDnvprLg5yRME=";

    CertificatePinner certificatePinner1 = new CertificatePinner.Builder()
            .add(hostname, absentIntermediateKey1)
            .build();

    private CertificatePinner certificatePinner2 = new CertificatePinner.Builder()
            .add(hostname, intermediateKey1)
            .add(hostname, absentIntermediateKey1)
            .build();

    //will get rejected
    private OkHttpClient validOkHttpClient1 = new OkHttpClient.Builder().certificatePinner(certificatePinner1).build();

    //will be accepted-note how only one key needs to match in certificate for it to pass pinned certificate validation
    private OkHttpClient validOkHttpClient2 = new OkHttpClient.Builder().certificatePinner(certificatePinner2).build();

    CertTestClass certTestClass = new CertTestClass();

    @Test
    public void validCertificateOneKey() throws Exception {
        assertNotNull(CertTestClass.sendRequest(validOkHttpClient2, prefix, hostname));

    }
    //will print Certificate Pinning failure stacktrace.
    @Test
    public void invalidCertificateOneKey() throws Exception {
        assertNull(CertTestClass.sendRequest(validOkHttpClient1, prefix, hostname));

    }

}