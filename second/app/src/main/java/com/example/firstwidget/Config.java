package com.example.firstwidget;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
//Это все настройка виджета
public class Config extends Activity {

    int widgetID = AppWidgetManager.INVALID_APPWIDGET_ID;
    Intent resultValue;

    public final static String WIDGET_PREF = "WIDGET_PREF";
    public final static String WIDGET_TEXT = "WIDGET_TEXT_";
    public final static String WIDGET_COLOR = "WIDGET_COLOR_";
//
    @Override
    public void onCreate(Bundle params) {
        super.onCreate(params);

        Intent intent = getIntent();
        //Bundle это такой массив данных, куда система передает предыдущее состояние, перед которым был вызван тот или иной метод
        //т.е. например каким было состояние перед онкриейт, те данные и передаем
        Bundle extras = intent.getExtras();
        //получаем последний айдишник, который доступен и в если не получается мы все равно засуним туда инвалид, т.е. неправильный айдишник

        if (extras != null) {
            widgetID = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }
        //и вот если айдишник неправильный, то мы завершаем работу, т.е. если произошла какая - то ошибка, то мы завершаем этот активити
        if (widgetID == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
        }
//передаем в resultValue новый Intent, засовываем туда инфу про следующий айдишник и тот, который мы вабрали
        resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetID);
        //достаем все предыдущие параметры при создании виджета
        //получается, что ты получаем результат дедуктивно
        setResult(RESULT_CANCELED, resultValue);
        setContentView(R.layout.config);
    }
//когда мы нажимаем кнопку сохранить вызывается функция онклик, где мы забираем значение заполненных полей,
//мы получаем цвета, расшифровываем их, т.к. они хранятся в переменных,

    public void onClick(View view) {
        EditText editText = findViewById(R.id.editText);
        RadioGroup radio = findViewById(R.id.radio);
        int color = 0;
        switch (radio.getCheckedRadioButtonId()) {
            case R.id.radioRed:
                color = R.color.red;
                break;
            case R.id.radioOrange:
                color = R.color.orange;
                break;
            case R.id.radioYellow:
                color = R.color.yellow;
                break;
            case R.id.radioGreen:
                color = R.color.green;
                break;
            case R.id.radioLightBlue:
                color = R.color.light_blue;
                break;
            case R.id.radioDarkBlue:
                color = R.color.dark_blue;
                break;
            case R.id.radioViolet:
                color = R.color.violet;
                break;
        }
//затем с помощью едитора мы добовляем в SharedPreferences строчку с префиксами+айди
        SharedPreferences pref = getSharedPreferences(WIDGET_PREF, MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        edit.putString(WIDGET_TEXT + widgetID, editText.getText().toString());
        edit.putInt(WIDGET_COLOR + widgetID, getResources().getColor(color));
        //подтвердаем, что мы сделали изменения
        edit.apply();
//тут получаем менеджера, чтобы с помощью него передать в метод updateWidget, чтобы "обновить" пустой виджет
        //менеджер встроенная штука андроида, которая помогает работать с виджетами
        AppWidgetManager manager = AppWidgetManager.getInstance(this);
        Widget.updateWidget(this, manager, pref, widgetID);

        setResult(RESULT_OK, resultValue);
        finish();

    }
}
