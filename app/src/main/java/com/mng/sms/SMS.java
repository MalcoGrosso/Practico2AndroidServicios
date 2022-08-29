package com.mng.sms;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import java.util.TimerTask;

public class SMS extends Service {
    public SMS() {
    }

    Handler handler = new Handler();
    Boolean DDS = false;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        handler.postDelayed(new Runnable() {
            public void run() {
                if(DDS.equals(true)){
                    handler.removeCallbacks(this);
                } else {
                    Cursor cursor = getContentResolver().query(Uri.parse("content://sms//inbox"), null, null,null,null);
                    if (cursor.moveToFirst()) {
                        int cont = 0;
                        do {
                            String Data = "";
                            Data +=  cursor.getString(12);
                            Log.d("salida", Data.toString());
                            cursor.moveToNext();
                            cont++;

                        } while (cont<5);
                        Log.d("salida", "------------------");
                    } else {
                        Log.d("salida", "No hay mensajes");
                    }
                    handler.postDelayed(this, 9000);
                }}
        }, 0);
        return START_STICKY;
    }

    public boolean stopService(Intent name) {
        super.stopService(name);
        DDS = true;
        return DDS;
    }

    public void onDestroy() {
        super.onDestroy();
        DDS = true;
        Log.d("salida","Servicio Finalizado");
    }

}