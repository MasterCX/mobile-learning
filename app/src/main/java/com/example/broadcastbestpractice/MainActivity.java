package com.example.broadcastbestpractice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button forceOffline = (Button) findViewById(R.id.force_offline);
        forceOffline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkOverlayPermission();
            }
        });
    }

    public  void  startReceiver(){
        Intent intent = new Intent("com.example.broadcastbestpractice.FORCE_OFFLINE ");
        intent.setComponent(new ComponentName("com.example.broadcastbestpractice","com.example.broadcastbestpractice.ForceOfflineReceiver"));
        sendBroadcast(intent);
    }
    public void checkOverlayPermission() {

        if(Build.VERSION.SDK_INT >= 23){
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, 0);
            }else{
                startReceiver();}
        }else{
            startReceiver();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if(Build.VERSION.SDK_INT >= 23){
                if (!Settings.canDrawOverlays(this)) {
                    // SYSTEM_ALERT_WINDOW permission not granted...
//                    Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
//                            Uri.parse("package:" + getPackageName()));
//                    startActivityForResult(intent, 0);
                    Toast.makeText(this,"didn't get the permission",Toast.LENGTH_LONG).show();

                }else{startReceiver();}
            }

        }
    }
}