package com.example.nkolezneva;

import androidx.appcompat.app.AppCompatActivity;
//импортируем всякие штуки, с которыми будем работать
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
//также импортируем утилиту рандом, чтобы можно было работать с случайными числами
import java.util.Random;
//в классе MainActivity мы заводим переменные в формате TextView - элемент для отображения текста
// также кнопочку и переменную для генерации рандомных чисел
public class MainActivity extends AppCompatActivity {
    TextView your_points;
    TextView robot_points;
    TextView first_cube_points;
    TextView second_cube_points;
    Button btn;
    Random random = new Random();
//переменная int для общего счета игрока
    int common_your = 0;
    //переменная int для общего счета компьютера
    int common_robots = 0;
    //переменные int для рандомных чисел, т.е. для кубиков
    int first_rand;
    int second_rand;
    //@Override - это переписывание, т.е. у класса AppCompatActivity есть метод onCreate, а мы этот метод переписываем
    @Override
    //передаем в этот метод сборку (Bundle - это тип переменной savedInstanceState, в которую как бы передается сохраненный им вид приложения)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //С помощью функции setContentView задаем лайаут activity_main
        //Когда все отрендерится мы с помощью функции "Найти вью по айди" (findViewById) находим нуную инфу в лейауте и записывает в соответствующие переменные
        //R это штука, которая возвращает циферки, чтобы андроид понимал айди, т.е. для нас это ер поинтс, а для него циферки
        setContentView(R.layout.activity_main);
        your_points = findViewById(R.id.your_points);
        robot_points = findViewById(R.id.robot_points);
        first_cube_points = findViewById(R.id.first_cube_points);
        second_cube_points = findViewById(R.id.second_cube_points);
        btn = findViewById(R.id.button);
    }
    public void onClick(View view) {
        //если не дубль
        if (!addUserPoints()) {
           btn.setText("Соперник ходит");
           //кнопка не доступна для нажатия т.к. ходит соперник
           btn.setEnabled(false);
           //Handler - таймаут
           Handler handler = new Handler();
                 handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        boolean b = true;
                        while (b) {
                            b = addRobotPoints();
                        }
//ход возвращается игроку
                        btn.setText("Кинь кубики");
                        btn.setEnabled(true);
                    }
                 }, 1200);
                 //проверяет выиграл ли робот
                 if (common_robots >= 100) {
                     //Intent - переход на новый лайаут
                     //Переходим на LooseActivity.class т.к. мы проиграли
                     //Обнуляем все очки
                      Intent i2 = new Intent(this, LooseActivity.class);
                      your_points.setText("0");
                      robot_points.setText("0");
                     first_cube_points.setText("0");
                     second_cube_points.setText("0");
                     common_your = 0;
                     common_robots = 0;
                     first_rand = 0;
                     second_rand = 0;
                      startActivity(i2);
                 }
        }
        //проверяем выиграли ли мы
        if (common_your >= 100) {
            //Переходим на WinActivity.class т.к. мы выиграли
            //Обнуляем все очки
            //Intent - абстрактное описание операции, которую необходимо выполнить.
            Intent i = new Intent(this, WinActivity.class);
            your_points.setText("0");
            robot_points.setText("0");
            first_cube_points.setText("0");
            second_cube_points.setText("0");
            common_your = 0;
            common_robots = 0;
            first_rand = 0;
            second_rand = 0;
            //Переходи на интент
            startActivity(i);
        }
    }
    //добавить очки пользователю

    public boolean addUserPoints() {
        //т.к. random.nextInt(6) вернет интервал от 0 до 5 прибавим 1
        first_rand = 1+random.nextInt(6);
        first_cube_points.setText(Integer.toString(first_rand));
        common_your += first_rand;
        second_rand = 1+random.nextInt(6);
        second_cube_points.setText(Integer.toString(second_rand));
        common_your += second_rand;
        your_points.setText(Integer.toString(common_your));
        return first_rand == second_rand;
    }
    //добавить очки роботу
    public boolean addRobotPoints() {
       first_rand = 1+random.nextInt(6);
       first_cube_points.setText(Integer.toString(first_rand));
       common_robots += first_rand;
       second_rand = 1+random.nextInt(6);
       second_cube_points.setText(Integer.toString(second_rand));
       common_robots += second_rand;
       robot_points.setText(Integer.toString(common_robots));
       return first_rand == second_rand;
    }
}