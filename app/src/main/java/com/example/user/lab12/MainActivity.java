package com.example.user.lab12;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import android.view.View;
import android.net.Uri;
import android.content.Intent;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.app.AlertDialog;


public class MainActivity extends AppCompatActivity {

    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void scanBar(View v) {
        try {//start the scanning activity from // com.google.zxing.client.android.SCANintent
            Intent intent = new Intent(ACTION_SCAN);
            intent.putExtra("SCAN_FORMATS", "CODE_39,CODE_93,CODE_128," +
                    "DATA_MATRIX,ITF, CODABAR,EAN_13,EAN_8,UPC_A,QR_CODE");
            startActivityForResult(intent, 0);
        } catch (ActivityNotFoundException anfe) {
            //on catch, show the download dialog
            showDialog(MainActivity.this, "No Scanner Found",
                    "Download a scanner code activity?", "Yes", "No") .show(); } }

    public void scanQR(View v) {
        try { //start the scanning activity from
            // the com.google.zxing.client.android.SCANintent
            Intent intent = new Intent(ACTION_SCAN);
            intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
            startActivityForResult(intent, 0);
        } catch (ActivityNotFoundException anfe) {
            //on catch, show the download dialog
            showDialog(MainActivity.this, "No Scanner Found",
                    "Download a scanner code activity?", "Yes", "No")
                    .show(); }
    }

    private static AlertDialog showDialog(
            final AppCompatActivity act,
            CharSequence title,
            CharSequence message,
            CharSequence buttonYes,
            CharSequence buttonNo) {
        AlertDialog.Builder downloadDialog = new AlertDialog.Builder(act);

        downloadDialog.setTitle(title);
        downloadDialog.setMessage(message);
        downloadDialog.setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Uri uri = Uri.parse("market://search?q=pname:" +
                        "com.google.zxing.client.android");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    act.startActivity(intent);
                } catch (ActivityNotFoundException anfe) { } }
        }); downloadDialog.setNegativeButton(buttonNo, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) { }
        });
        return downloadDialog.show(); }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                //get the extras that are returned from the intent
                String contents = intent.getStringExtra("SCAN_RESULT");
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
                Toast toast = Toast
                        .makeText(this, "Content:" + contents + " Format:" + format, Toast.LENGTH_LONG);
                toast.show();
    }
    }
    }
}
