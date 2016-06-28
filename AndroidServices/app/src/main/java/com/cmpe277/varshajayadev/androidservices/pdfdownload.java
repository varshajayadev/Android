package com.cmpe277.varshajayadev.androidservices;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class pdfdownload extends AppCompatActivity {
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent)   {

            Bundle bundle = intent.getExtras();
            if(bundle != null)  {
                String string = bundle.getString(DownloadService.FILEPATH);
                int resultCode = bundle.getInt(DownloadService.RESULT);
                if (resultCode == RESULT_OK)    {
                    Toast.makeText(pdfdownload.this, "Download Complete. Donwload URI"+string, Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(pdfdownload.this, "Download Failed", Toast.LENGTH_SHORT).show();
                }

            }
        }
    };
    public void download(View view) {

        EditText filepath1 = (EditText)findViewById(R.id.location1);
        String fp1 = filepath1.getText().toString();
        Intent intent1 = new Intent(this, DownloadService.class);
        intent1.putExtra(DownloadService.FILENAME,"pdf_1.pdf");
        intent1.putExtra(DownloadService.URL, fp1);
        startService(intent1);
        Toast.makeText(pdfdownload.this, "Service started for file_1", Toast.LENGTH_SHORT).show();

        EditText filepath2 = (EditText)findViewById(R.id.location2);
        String fp2 = filepath2.getText().toString();
        Intent intent2 = new Intent(this, DownloadService.class);
        intent2.putExtra(DownloadService.FILENAME,"pdf_2.pdf");
        intent2.putExtra(DownloadService.URL,fp2);
        startService(intent2);
        Toast.makeText(pdfdownload.this, "Service started for file_2", Toast.LENGTH_SHORT).show();

        EditText filepath3 = (EditText)findViewById(R.id.location3);
        String fp3 = filepath3.getText().toString();
        Intent intent3 = new Intent(this, DownloadService.class);
        intent3.putExtra(DownloadService.FILENAME,"pdf_3.pdf");
        intent3.putExtra(DownloadService.URL,fp3);
        startService(intent3);
        Toast.makeText(pdfdownload.this, "Service started for file_3", Toast.LENGTH_SHORT).show();

        EditText filepath4 = (EditText)findViewById(R.id.location4);
        String fp4 = filepath4.getText().toString();
        Intent intent4 = new Intent(this, DownloadService.class);
        intent4.putExtra(DownloadService.FILENAME,"pdf_4.pdf");
        intent4.putExtra(DownloadService.URL,fp4);
        startService(intent4);
        Toast.makeText(pdfdownload.this, "Service started for file_4", Toast.LENGTH_SHORT).show();

        EditText filepath5 = (EditText)findViewById(R.id.location5);
        String fp5 = filepath5.getText().toString();
        Intent intent5 = new Intent(this, DownloadService.class);
        intent5.putExtra(DownloadService.FILENAME,"pdf_5.pdf");
        intent5.putExtra(DownloadService.URL,fp5);
        startService(intent5);
        Toast.makeText(pdfdownload.this, "Service started for file_5", Toast.LENGTH_SHORT).show();
    }

    public void close(View view)    {
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfdownload);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, new IntentFilter(DownloadService.NOTIFICATION));
    }
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

}
