package com.example.bolek.rcdroid;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import java.io.IOException;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UDPClient implements Serializable {

    private InetAddress ip;
    private int port;
    private DatagramSocket ds;

    public UDPClient(Context context) throws UnknownHostException, SocketException {
        ds = new DatagramSocket();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        ip = InetAddress.getByName(preferences.getString("ip", "192.168.4.1"));
        port = Integer.valueOf(preferences.getString("port", "3000"));
        Log.e("AAAAA", String.valueOf(port));
    }

    public void sendMessage(String msg) throws IOException {
        new Task().execute(msg);
    }

    private class Task extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... args) {
            DatagramPacket dp = new DatagramPacket(args[0].getBytes(), args[0].length(), ip, port);
            try {
                ds.send(dp);
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }
    }

    @Override
    protected void finalize() throws Throwable {
        ds.close();
        super.finalize();
    }
}
