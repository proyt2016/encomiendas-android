package com.sourcey.materiallogindemo;

/**
 * Created by maxi on 30/08/16.
 */
public class TenantProvider {

    static  String clave = BuildConfig.API_KEY;

    public static String GetTenant(){

        return clave;

    }
}
