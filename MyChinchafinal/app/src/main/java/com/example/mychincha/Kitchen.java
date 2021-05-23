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


public class Kitchen extends AppCompatActivity  {

    ImageButton livingRoom2, kitchen2, bathRoom2, bedRoom2;
    ProgressBar progressGost2, progressFood2, progressWash2, progressSleep2;
    Button eat;
    ImageView ChinchaHungry;
    ImageView Chincha2;

    DatabaseReference myBD; // объект, который хранит ссылку на бд
    String T_KEY = "PROGRESSBAR"; // группа для бд
    List<String> newData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kitchen);
        //кнопки
        livingRoom2 = (ImageButton)findViewById(R.id.livingroom2);
        kitchen2 = (ImageButton)findViewById(R.id.kitchen2);
        bathRoom2 = (ImageButton)findViewById(R.id.bathroom2);
        bedRoom2 = (ImageButton)findViewById(R.id.bedroom2);

        //livingRoom2.getY();

        //прогресс бары(счётчики состояния)
        progressGost2 = (ProgressBar) findViewById(R.id.progress_gost2);
        progressFood2 = (ProgressBar) findViewById(R.id.progress_food2);
        progressWash2 = (ProgressBar) findViewById(R.id.progress_wash2);
        progressSleep2 = (ProgressBar) findViewById(R.id.progress_sleep2);

        newData = new ArrayList<>(); // для хранения текущих значений прогрессбаров
        myBD = FirebaseDatabase.getInstance().getReference(T_KEY);// получили бд
        // получаем акутальные значения для прогрессбаров из БД
        readDataFromDB();

        // изменяем значения прогрессбаров
       // changeProBar();

        //изображение обычной шиншиллы
        Chincha2 = (ImageView) findViewById(R.id.chincha2);

        //изображение голодной шиншиллы
        ChinchaHungry = (ImageView) findViewById(R.id.chincha_hungry);
        //кнопка контакта с питомцем
        eat = (Button) findViewById(R.id.eating);


        livingRoom2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Kitchen.this, LivingRoom.class);
                startActivity(intent);
            }
        });

        bathRoom2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Kitchen.this, BathRoom.class);
                startActivity(intent);
            }
        });

        bedRoom2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Kitchen.this, BedRoom.class);
                startActivity(intent);
            }
        });

        eat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //исчезновение обычной шиншиллы
                /*ImageView image = findViewById(R.id.chincha2);
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation_kitchen1);
                image.startAnimation(animation);
                //появление голодной шиншиллы
                ImageView image1 = findViewById(R.id.chincha_hungry);
                Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation_kitchen2);
                image1.startAnimation(animation1); */
                //анимация шиншиллы
                Chincha2.animate().alpha(0f).setDuration(2000);
                ChinchaHungry.animate().alpha(1f).setDuration(2000);

            }
        });

        // начальное значение счётчиков
        /*progressGost2.setProgress(100);
        progressFood2.setProgress(100);
        progressWash2.setProgress(100);
        progressSleep2.setProgress(100);*/



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
                progressGost2.setProgress(curval1);

                int curval2 = Integer.parseInt(newData.get(1));
                progressFood2.setProgress(curval2);

                int curval3 = Integer.parseInt(newData.get(2));
                progressWash2.setProgress(curval3);

                int curval4 = Integer.parseInt(newData.get(3));
                progressSleep2.setProgress(curval4);
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
        int currentProBar1 = progressGost2.getProgress(); // получаем текущее значение прогрессбара
        ObjectAnimator anim = ObjectAnimator.ofInt(progressGost2, "progress", currentProBar1, 0);
        anim.setDuration(100000); // изменение каждые 100 сек
        anim.start();
        // изменяем значение 2 прогрессбара
        int currentProBar2 = progressFood2.getProgress(); // получаем текущее значение прогрессбара
        ObjectAnimator anim2 = ObjectAnimator.ofInt(progressFood2, "progress", currentProBar2, 0);
        anim2.setDuration(200000); // изменение каждые 200 сек
        anim2.start();
        // изменяем значение 3 прогрессбара
        int currentProBar3 = progressWash2.getProgress(); // получаем текущее значение прогрессбара
        ObjectAnimator anim3 = ObjectAnimator.ofInt(progressWash2, "progress", currentProBar3, 0);
        anim3.setDuration(300000); // изменение каждые 300 сек
        anim3.start();
        // изменяем значение 4 прогрессбара
        int currentProBar4 = progressSleep2.getProgress(); // получаем текущее значение прогрессбара
        ObjectAnimator anim4 = ObjectAnimator.ofInt(progressSleep2, "progress", currentProBar4, 0);
        anim4.setDuration(300500);  // изменение каждые 300,5 сек
        anim4.start();
    }



}