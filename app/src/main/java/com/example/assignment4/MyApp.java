package com.example.assignment4;

import android.app.Application;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyApp extends Application {

    // Single instance of RoomDBManager
    RoomDBManager roomDBManager = new RoomDBManager();

    // Static Threads
    static ExecutorService executorService = Executors.newFixedThreadPool(4);

}
