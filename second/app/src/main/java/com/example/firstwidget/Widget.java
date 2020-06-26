package com.example.firstwidget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RemoteViews;
//Это сам виджет
//наследуем в класс Widget класс AppWidgetProvider
public class Widget extends AppWidgetProvider {
    //context - это то, где виджет находится в системе
    //супер - это вызов суперконструктора т.е. мы у метода, который наследуем возвращаем так, как и должно быть
    //onEnabled становится доступным, когда все отрисовывается на экране
    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        Log.d("first", "onEnabled()");
    }

    //получам виджеты здесь
    //SharedPreferences - общие настнойки, те настройки, которые можно конектить
    @Override
    public void onUpdate(Context context, AppWidgetManager manager, int[] ids) {
        super.onUpdate(context, manager, ids);
        Log.e("first", "onUpdate()");
        SharedPreferences pref = context.getSharedPreferences(Config.WIDGET_PREF, Context.MODE_PRIVATE);
        //для всех виджетов мы вызываем функцию updateWidget, т.е. обновляем виджеты
        //потому что обязательно должны сказать всем виджетам, что все обновилось, чтобы ничего не поехало
        //в свою очередь потому, что SharedPreferences это такое постоянное хранилише данных, которое есть в андроид, и оно ипользуется приложениями для хранения настроек следующщим образом:
        //У нас есть настройки, которые сохраняются только при активном использование телефона, есть те, что используются только при его угасании
        //SharedPreferences позволяет сохранять эти настройки не только при фоновом режиме, но и при перезагрузки телефона
        //т.е. наш виджет останется даже при перезагрузке телефона - ЭТО ВАЖНО!
        for (int id: ids) {
            updateWidget(context, manager, pref, id);
        }
    }
//удаление
    @Override
    public void onDeleted(Context context, int[] ids) {
        super.onDeleted(context, ids);
        Log.i("first", "onDeleted()");
        //Editor - штука, с помощью которой мы изменяем SharedPreferences
        SharedPreferences.Editor edit = context.getSharedPreferences(Config.WIDGET_PREF, Context.MODE_PRIVATE).edit();
        //к нашим префиксам присоединяем конкретный айдишник, чтобы удалить из SharedPreferences нужное
        for (int id: ids) {
            edit.remove(Config.WIDGET_TEXT + id);
            edit.remove(Config.WIDGET_COLOR + id);
        }
        //edit.apply() - более современная вещь, появилась более в в современной апишки, выполняется асинхронно
        //edit.commit() - был с нами всегда, он выполняется синхронно
        //но эплайн не позволяет выводить логи после него, т.е. мы не можем, например, вывести лог о том, что у нас там что-то добавмлось успешно
        //а вот после комита можно :)
        edit.commit();
    }


    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        Log.v("first", "onDisabled()");
    }
//обновляем виджет, а точнее возможно на базе этого работает создание виджета, т.е. когда мы дефолтно создаем его на рабочем столе он появляется с настройками ниже

    static void updateWidget(Context context, AppWidgetManager manager, SharedPreferences pref, int widgetID) {
        String text = pref.getString(Config.WIDGET_TEXT + widgetID, null);
        if (text == null) return;
        int color = pref.getInt(Config.WIDGET_COLOR + widgetID, 0);

        RemoteViews view = new RemoteViews(context.getPackageName(), R.layout.widget);
        view.setTextViewText(R.id.textView, text);
        view.setInt(R.id.textView, "setBackgroundColor", color);
        manager.updateAppWidget(widgetID, view);
    }
}
