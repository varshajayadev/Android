/*
package com.example.varsha.gcm;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

*/
/**
 * Created by Varsha on 5/3/2016.
 *//*

public class ShareExternalServer {
    public String shareRegIdWithAppServer (final Context context, final String regId)   {
        String url = "";
        String result = "";
        Map paramsMap = new HashMap();
        paramsMap.put("regId", regId);
        try{
            URL serverUrl = null;
            try {
                serverUrl = new URL(url);
            } catch (MalformedURLException e) {
                e.printStackTrace();
                Log.e("AppUtil", "URL Connection Error: "
                        + url, e);
                result = "Invalid URL: " +url ;
            }
            StringBuilder postBody = new StringBuilder();
            Iterator iterator = paramsMap.entrySet().iterator();
            while(iterator.hasNext())   {
                Map.Entry param = (Map.Entry) iterator.next();
                postBody.append(param.getKey()).append('=').append(param.getValue());
                if (iterator.hasNext()) {
                    postBody.append('&');
                }
            }
            String body = postBody.toString();
            byte[] bytes = body.getBytes();
            HttpURLConnection httpCon = null;
            try {
                httpCon = (HttpURLConnection) serverUrl.openConnection();
                httpCon.setDoOutput(true);
                httpCon.setUseCaches(false);
                httpCon.setFixedLengthStreamingMode(bytes.length);
                httpCon.setRequestMethod("POST");
                httpCon.setRequestProperty("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
                OutputStream out = httpCon.getOutputStream();
                out.write(bytes);
                out.close();

                int status = httpCon.getResponseCode();
                if (status == 200)  {
                    result = "RegId shared with Application Server. RegId: "
                            + regId;
                }else {
                    result = "Post Failure." + " Status: " + status;
                }
            } finally {
                if (httpCon != null) {
                    httpCon.disconnect();
                }
            }
            } catch (IOException e) {
                e.printStackTrace();
            result = "Post Failure. Error in sharing with App Server.";
            Log.e("AppUtil", "Error in sharing with App Server: " + e);
            }
            return result;

    }
}
*/
