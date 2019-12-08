package com.AIProject.howstoday;

import android.graphics.Bitmap;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;

public class HttpMethod {

    private static String hUrl = "http://13.209.44.126:30000";
    private String UUID;
    private OutputStream out;

    public HttpMethod(String UUID){
        this.UUID = UUID;
    }

    public String requestToServer(String url, Bitmap userImg){
        String str = hUrl+url+"?fileName="+UUID;
        try {

            URL rUrl = new URL(str);
            HttpURLConnection conn = (HttpURLConnection)rUrl.openConnection();

            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestMethod("GET");
            //conn.setRequestProperty("Accept-Charset", "utf-8");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setConnectTimeout(3000);

            out = new DataOutputStream(conn.getOutputStream());
            out.write(ImgHandler.BitmapToByte(userImg));
            out.flush();
            out.close();

            if(conn.getResponseCode() != HttpURLConnection.HTTP_OK){
               // Log.d("HttpCOnnection-----", "Failed");
                return "서버오류";
            }
               // Log.d("HttpCOnnection-----", "Success");

            BufferedReader br  = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
            String rStr=br.readLine();


            Log.d("HttpResponse-----", rStr);
            conn.disconnect();

            return rStr;

        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch(SocketTimeoutException s){
            return "timeout";
        } catch(IOException i){
            i.printStackTrace();
            return null;
        }
    }
}
