package com.example.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;
//Абстрактный класс БД, содержащий версию и сущности БД
@Database(entities = {User.class}, version = 1)
public abstract class DB extends RoomDatabase {
    public abstract UserDao userDao();
}
