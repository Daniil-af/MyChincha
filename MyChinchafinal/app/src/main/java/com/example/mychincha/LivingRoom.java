package com.example.mychincha;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class LivingRoom extends AppCompatActivity  {
    Button play;
    ImageButton livingRoom1, kitchen1, bathRoom1, bedRoom1;
    ProgressBar progressGost1, progressFood1, progressWash1, progressSleep1;
    int progGost1end, progFood1end, progWash1end, progSleep1end, progressplay;
    ImageView ChinchaCry;
    ImageView Chincha1;
    ImageView Chincha12;


    DatabaseReference myBD; // объект, который хранит ссылку на бд
    String T_KEY = "PROGRESSBAR"; // группа для бд
    int start = 100;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.living_room);

        //запуск музыки сразу при входе в игру
        startService(new Intent(this, MyMusicService.class));

      /*  //выключение музыки при выходе из игры
        public void TurnOffMusic() {
            stopService(new Intent(this, MyMusicService.class));
        } */

        //грустная шиншилла и обыкновенная шиншилла
        Chincha1 = (ImageView) findViewById(R.id.chincha1);
        ChinchaCry = (ImageView) findViewById(R.id.chinchacry1);
        Chincha12 = (ImageView) findViewById(R.id.chincha12);

        //кнопки для перемещения между экранами
        livingRoom1 = (ImageButton)findViewById(R.id.livingroom1);
        kitchen1 = (ImageButton)findViewById(R.id.kitchen1);
        bathRoom1 = (ImageButton)findViewById(R.id.bathroom1);
        bedRoom1 = (ImageButton)findViewById(R.id.bedroom1);

        //прогресс бары(счётчики состояния)
        progressGost1 = (ProgressBar) findViewById(R.id.progress_gost1);
        progressFood1 = (ProgressBar) findViewById(R.id.progress_food1);
        progressWash1 = (ProgressBar) findViewById(R.id.progress_wash1);
        progressSleep1 = (ProgressBar) findViewById(R.id.progress_sleep1);


        // задаем начальное значение счётчиков
        progressGost1.setProgress(start);
        progressFood1.setProgress(start);
        progressWash1.setProgress(start);
        progressSleep1.setProgress(start);
        // изменяем значения прогрессбаров
        changeProBar();

        //кнопка контакта с питомцем
        play = (Button)findViewById(R.id.playing);

        kitchen1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeBd();// записываем актуальные значения прогрессбаров в бд
                Intent intent = new Intent(LivingRoom.this, Kitchen.class);
                startActivity(intent);
            }
        });
        bathRoom1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeBd();// записываем актуальные значения прогрессбаров в бд
                Intent intent = new Intent(LivingRoom.this, BathRoom.class);
                startActivity(intent);
            }
        });
        bedRoom1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeBd();// записываем актуальные значения прогрессбаров в бд
                Intent intent = new Intent(LivingRoom.this, BedRoom.class);
                startActivity(intent);
            }
        });
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeBd();// записываем актуальные значения прогрессбаров в бд
                //анимация шиншиллы
                ImageView image = findViewById(R.id.chincha1);
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation_lroom);
                image.startAnimation(animation);

                //увеличиваем значение ProgressBar(Уровень счастья) при нажатии на кнопку
                progressGost1.setProgress(progressGost1.getProgress() +10);

               /* // считываем значение с ProgressBar "Уровень счастья" и прибавляем к нему 10
                progressplay = progressGost1.getProgress() + 10;
                //заносим значение progressplay в ProgressGost1
                progressGost1.setProgress(+progressplay); */
            }
        });

    }

    // метод для изменения значений прогрессбаров с течением времени
    public void changeProBar(){
        // изменяем значение 1 прогрессбара
        int currentProBar1 = progressGost1.getProgress(); // получаем текущее значение прогрессбара
        ObjectAnimator anim = ObjectAnimator.ofInt(progressGost1, "progress", currentProBar1, 0);
        anim.setDuration(100000); // изменение каждые 100 сек
        anim.start();
        // изменяем значение 2 прогрессбара
        int currentProBar2 = progressFood1.getProgress(); // получаем текущее значение прогрессбара
        ObjectAnimator anim2 = ObjectAnimator.ofInt(progressFood1, "progress", currentProBar2, 0);
        anim2.setDuration(100000); // изменение каждые 100 сек
        anim2.start();
        // изменяем значение 3 прогрессбара
        int currentProBar3 = progressWash1.getProgress(); // получаем текущее значение прогрессбара
        ObjectAnimator anim3 = ObjectAnimator.ofInt(progressWash1, "progress", currentProBar3, 0);
        anim3.setDuration(200000); // изменение каждые 200 сек
        anim3.start();
        // изменяем значение 4 прогрессбара
        int currentProBar4 = progressSleep1.getProgress(); // получаем текущее значение прогрессбара
        ObjectAnimator anim4 = ObjectAnimator.ofInt(progressSleep1, "progress", currentProBar4, 0);
        anim4.setDuration(200000);  // изменение каждые 200 сек
        anim4.start();
    }


    // метод для записи данных в бд
    public void writeBd(){
        myBD = FirebaseDatabase.getInstance().getReference(T_KEY);// получили бд
        String id0 = myBD.getKey();// получаем id пользователя из бд (сами не создаем)
        // получаем текущее значение прогрессбаров
        String currProBar1 = Integer.toString(progressGost1.getProgress());
        String currProBar2 = Integer.toString(progressFood1.getProgress());
        String currProBar3 = Integer.toString(progressWash1.getProgress());
        String currProBar4 = Integer.toString(progressSleep1.getProgress());
        // собираем все данные в объект
        ProBar pro = new ProBar(id0, "0", currProBar1,currProBar2,currProBar3, currProBar4);
        myBD.push().setValue(pro); // записываем данные в бд

       /* //создаём и отображаем текстовое уведомление
        Toast toast = Toast.makeText(getApplicationContext(),
                "Данные сохранены!",
                Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show(); */

        // считываем значения ProgressBar
        progGost1end = progressGost1.getProgress();
        progFood1end = progressFood1.getProgress();
        progWash1end = progressWash1.getProgress();
        progSleep1end = progressSleep1.getProgress();

       /* //изменение модели шиншиллы при достижении на ProgressBar критических значений
        if ((progGost1end <=50  && progFood1end <= 50) || (progWash1end <= 50 && progSleep1end <= 50)) {
            //исчезновение обычной шиншиллы
            ImageView image = findViewById(R.id.chincha1);
            Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation_lroom2);
            image.startAnimation(animation);
            //появление грустной шиншиллы
            ImageView image1 = findViewById(R.id.chinchacry1);
            Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation_lroom3);
            image1.startAnimation(animation1);
        } */

    }

}









        



