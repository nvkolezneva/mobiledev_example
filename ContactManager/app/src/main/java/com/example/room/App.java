package com.example.room;

import android.app.Application;
//для подключения рум нужно описать зависимости в файле "бил грандел модуль апп"
import androidx.room.Room;
//класс самого приложения, класс, где "строится" бд и приложение, на основе файла DB.
public class App extends Application {

    public static App app;
    public static DB db;

    @Override
    public void onCreate() {
        super.onCreate();
        app=this;
        db = Room.databaseBuilder(this, DB.class, "contacts").build();
    }
}
