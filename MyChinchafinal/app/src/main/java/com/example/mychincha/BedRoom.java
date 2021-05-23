package com.example.mychincha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class BedRoom extends AppCompatActivity  {

    ImageButton livingRoom4, kitchen4, bathRoom4, bedRoom4;
    ProgressBar progressGost4, progressFood4, progressWash4, progressSleep4;
    Button sleep;
    ImageView ChinchaDream, Chincha4;

    DatabaseReference myBD; // объект, который хранит ссылку на бд
    String T_KEY = "PROGRESSBAR"; // группа для бд
    List<String> newData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bed_room);
        livingRoom4 = (ImageButton)findViewById(R.id.livingroom4);
        kitchen4 = (ImageButton)findViewById(R.id.kitchen4);
        bathRoom4 = (ImageButton)findViewById(R.id.bathroom4);
        bedRoom4 = (ImageButton)findViewById(R.id.bedroom4);

        //прогресс бары(счётчики состояния)
        progressGost4 = (ProgressBar) findViewById(R.id.progress_gost4);
        progressFood4 = (ProgressBar) findViewById(R.id.progress_food4);
        progressWash4 = (ProgressBar) findViewById(R.id.progress_wash4);
        progressSleep4 = (ProgressBar) findViewById(R.id.progress_sleep4);

        newData = new ArrayList<>(); // для хранения текущих значений прогрессбаров
        myBD = FirebaseDatabase.getInstance().getReference(T_KEY);// получили бд
        // получаем акутальные значения для прогрессбаров из БД
        readDataFromDB();
        // изменяем значения прогрессбаров
        //changeProBar();

        //кнопка контакта  с питомцем
        sleep = (Button) findViewById(R.id.sleeping);

        // изображение обычной шиншиллы
        Chincha4 = (ImageView) findViewById(R.id.chincha4);
        //изображение спящей шиншиллы
        ChinchaDream = (ImageView) findViewById(R.id.chinchilla_sleep);



        livingRoom4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BedRoom.this, LivingRoom.class);
                startActivity(intent);

            }
        });

        kitchen4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BedRoom.this, Kitchen.class);
                startActivity(intent);

            }
        });

        bathRoom4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BedRoom.this, BathRoom.class);
                startActivity(intent);

            }
        });

        sleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //исчезновение обычной шиншиллы
                /*ImageView image = findViewById(R.id.chincha4);
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation_bathroom1);
                image.startAnimation(animation);
                //появление сонной шиншиллы
                ImageView image1 = findViewById(R.id.chinchilla_sleep);
                Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation_bathroom2);
                image1.startAnimation(animation1); */
                // анимация питомца
                Chincha4.animate().alpha(0f).setDuration(3000);
                ChinchaDream.animate().alpha(1f).setDuration(3000);
            }
        });

        // начальное значение счётчиков
        /*progressGost4.setProgress(100);
        progressFood4.setProgress(100);
        progressWash4.setProgress(100);
        progressSleep4.setProgress(100);*/

        //фоновая музыка
        //startService(new Intent(this, MusicBG.class));
        //выключенние фоновой музыки при выходе из игры
        /*private void onBackPressed() {
            stopService(new Intent(this, MusicBG.class));
        }*/
    }

    private void readDataFromDB(){
        ValueEventListener valueEventListener  = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    ProBar user = ds.getValue(ProBar.class);
                    assert user !=null;
                    newData.add(user.proBar1); // получаем значение из
                    newData.add(user.proBar2);
                    newData.add(user.proBar3);
                    newData.add(user.proBar4);
                }
                // устанавливаем значения для прогрессбаров из БД
                int curval1 = Integer.parseInt(newData.get(0));
                progressGost4.setProgress(curval1);

                int curval2 = Integer.parseInt(newData.get(1));
                progressFood4.setProgress(curval2);

                int curval3 = Integer.parseInt(newData.get(2));
                progressWash4.setProgress(curval3);

                int curval4 = Integer.parseInt(newData.get(3));
                progressSleep4.setProgress(curval4);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        myBD.addValueEventListener(valueEventListener);
    }

    // метод для изменения значений прогрессбаров с течением времени
    public void changeProBar(){
        // изменяем значение 1 прогрессбара
        int currentProBar1 = progressGost4.getProgress(); // получаем текущее значение прогрессбара
        ObjectAnimator anim = ObjectAnimator.ofInt(progressGost4, "progress", currentProBar1, 0);
        anim.setDuration(100000); // изменение каждые 100 сек
        anim.start();
        // изменяем значение 2 прогрессбара
        int currentProBar2 = progressFood4.getProgress(); // получаем текущее значение прогрессбара
        ObjectAnimator anim2 = ObjectAnimator.ofInt(progressFood4, "progress", currentProBar2, 0);
        anim2.setDuration(200000); // изменение каждые 200 сек
        anim2.start();
        // изменяем значение 3 прогрессбара
        int currentProBar3 = progressWash4.getProgress(); // получаем текущее значение прогрессбара
        ObjectAnimator anim3 = ObjectAnimator.ofInt(progressWash4, "progress", currentProBar3, 0);
        anim3.setDuration(300000); // изменение каждые 300 сек
        anim3.start();
        // изменяем значение 4 прогрессбара
        int currentProBar4 = progressSleep4.getProgress(); // получаем текущее значение прогрессбара
        ObjectAnimator anim4 = ObjectAnimator.ofInt(progressSleep4, "progress", currentProBar4, 0);
        anim4.setDuration(300500);  // изменение каждые 300,5 сек
        anim4.start();
    }



}