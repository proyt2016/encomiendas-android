package com.sourcey.materiallogindemo;

/**
 * Created by maxi on 30/08/16.
 */
public class TenantProvider {

    static  String clave = BuildConfig.API_KEY;
    static  String url = BuildConfig.API_URL;


    public static String GetTenant(){

        return clave;


    }

    public static String GetApiUrl(){

        return url;


    }
}
