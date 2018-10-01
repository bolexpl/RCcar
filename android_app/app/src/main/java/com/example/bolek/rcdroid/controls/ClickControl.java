package com.example.bolek.rcdroid.controls;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.bolek.rcdroid.UDPClient;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

public class ClickControl implements View.OnTouchListener {

    private boolean w, s, a, d;
    private UDPClient udp;
    private Context context;

    public ClickControl(Context context) {
        this.context = context;

        try {
            udp = new UDPClient(context);
        } catch (UnknownHostException e) {
            Toast.makeText(context, "Nieznany host", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (SocketException e) {
            Toast.makeText(context, "Błąd sieci", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public void updateUDP() {
        try {
            udp = new UDPClient(context);
        } catch (UnknownHostException e) {
            Toast.makeText(context, "Nieznany host", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (SocketException e) {
            Toast.makeText(context, "Błąd sieci", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        String tag = view.getTag().toString();

        view.performClick();

        //Warunek czy nie jest wciśnięty przeciwny przycisk
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN &&
                (tag.equals("forward") && s) ||
                (tag.equals("backward") && w) ||
                (tag.equals("left") && d) ||
                (tag.equals("right") && a)) {
            return false;
        }

        //obsługa zdarzeń
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN: //wciśnięcie
                try {
                    udp.sendMessage(tag);
                } catch (IOException e) {
                    Toast.makeText(context, "Błąd wysłania", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                switch (tag) {
                    case "forward":
                        w = true;
                        break;
                    case "backward":
                        s = true;
                        break;
                    case "left":
                        a = true;
                        break;
                    case "right":
                        d = true;
                        break;
                }

                Log.e("click", view.getTag().toString());
                break;
            case MotionEvent.ACTION_UP: //puszczenie
                try {
                    if (tag.equals("forward") && w
                            || (tag.equals("backward") && s)) {
                        udp.sendMessage("stop");
                        Log.e("click", "stop");
                        w = false;
                        s = false;
                    } else if (tag.equals("left") && a
                            || (tag.equals("right") && d)) {
                        udp.sendMessage("straight");
                        Log.e("click", "straight");
                        a = false;
                        d = false;
                    }

                } catch (IOException e) {
                    Toast.makeText(context, "Błąd przy wysłaniu", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

                break;
        }

        return false;
    }
}
