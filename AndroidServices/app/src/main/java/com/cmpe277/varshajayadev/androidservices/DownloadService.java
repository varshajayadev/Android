package com.cmpe277.varshajayadev.androidservices;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.os.Environment;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Varsha on 3/11/2016.
 */
public class DownloadService extends IntentService{
    private int result = Activity.RESULT_CANCELED;
    public static final String URL = "urlpath";
    public static final String FILENAME = "filename";
    public static final String FILEPATH = "filepath";
    public static final String RESULT = "result";
    public static final String NOTIFICATION = "com.cmpe277.varshajayadev.androidservices";

    public DownloadService() {
        super("DownlaodService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        int len = 0;
        int count;
        String urlPath = intent.getStringExtra(URL);
        String fileName = intent.getStringExtra(FILENAME);
        File output = new File(Environment.getExternalStorageDirectory(), fileName);
        if (output.exists())    {
            output.delete();
        }

        InputStream stream = null;
        FileOutputStream fos = null;

        try {

            URL url = new URL(urlPath);
            URLConnection con = url.openConnection();
            con.connect();
            len = con.getContentLength();
            stream = new BufferedInputStream(url.openStream());
            InputStreamReader reader = new InputStreamReader(stream);
            fos = new FileOutputStream(output.getPath());
            byte data[] = new byte[1024];
            //int next = -1;

            while ((count = stream.read(data)) != -1) {
                fos.write(data, 0, count);
            }
            fos.flush();
            fos.close();
            stream.close();
            // successfully finished
            result = Activity.RESULT_OK;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        publishResults(output.getAbsolutePath(), result);
    }

    private void publishResults(String outputPath, int result)  {
        Intent intent = new Intent(NOTIFICATION);
        intent.putExtra(FILEPATH,outputPath);
        intent.putExtra(RESULT, result);
        sendBroadcast(intent);
    }
}
