package com.example.bolek.rcdroid.controls;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.bolek.rcdroid.UDPClient;

import java.io.IOException;
import java.io.Serializable;
import java.net.SocketException;
import java.net.UnknownHostException;

public class MoveControl implements View.OnTouchListener {

    private UDPClient udp;
    private Context context;

    private static final double a = 0.45;
    private static final double d = 1 - a;

    private static final double w = 0.40;
    private static final double s = 1 - w;

    private static final double crossA = 0.35;
    private static final double crossD = 1 - a;

    private static final double crossW = 0.35;
    private static final double crossS = 1 - w;


    public MoveControl(Context context) {
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
    public boolean onTouch(View view, MotionEvent event) {
        String tag = view.getTag().toString();

        view.performClick();

        final int X = (int) event.getX();
        final int Y = (int) event.getY();

        double f, b, l, r;

        try {
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_MOVE:

                    switch (tag) {

                        case "ad":
                            l = view.getWidth() * a;
                            r = view.getWidth() * d;
                            if (X < l) {
                                Log.e("UDP", "left");
                                udp.sendMessage("left");
                            } else if (X > r) {
                                Log.e("UDP", "right");
                                udp.sendMessage("right");
                            } else {
                                Log.e("UDP", "straight");
                                udp.sendMessage("straight");
                            }
                            break;
                        case "ws":
                            f = view.getHeight() * w;
                            b = view.getHeight() * s;
                            if (Y < f) {
                                Log.e("UDP", "forward");
                                udp.sendMessage("forward");
                            } else if (Y > b) {
                                Log.e("UDP", "backward");
                                udp.sendMessage("backward");
                            } else {
                                Log.e("UDP", "stop");
                                udp.sendMessage("stop");
                            }
                            break;
                        default:
                            l = view.getWidth() * crossA;
                            r = view.getWidth() * crossD;
                            f = view.getHeight() * crossW;
                            b = view.getHeight() * crossS;
                            if (X < l) {
                                Log.e("UDP", "left");
                                udp.sendMessage("left");
                            } else if (X > r) {
                                Log.e("UDP", "right");
                                udp.sendMessage("right");
                            } else {
                                Log.e("UDP", "straight");
                                udp.sendMessage("straight");
                            }

                            if (Y < f) {
                                Log.e("UDP", "forward");
                                udp.sendMessage("forward");
                            } else if (Y > b) {
                                Log.e("UDP", "backward");
                                udp.sendMessage("backward");
                            } else {
                                Log.e("UDP", "stop");
                                udp.sendMessage("stop");
                            }
                            break;
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    switch (tag) {
                        case "ad":
                            Log.e("UDP", "straight");
                            udp.sendMessage("straight");
                            break;
                        case "ws":
                            Log.e("UDP", "stop");
                            udp.sendMessage("stop");
                            break;
                        default:
                            Log.e("UDP", "straight");
                            udp.sendMessage("straight");
                            Log.e("UDP", "stop");
                            udp.sendMessage("stop");
                            break;
                    }
                    break;
            }
        } catch (IOException e) {
            Toast.makeText(context, "Błąd przy wysyłaniu", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        return false;
    }
}
