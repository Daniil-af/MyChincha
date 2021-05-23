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


public class BathRoom extends AppCompatActivity  {
   ImageButton livingRoom3, kitchen3, bathRoom3, bedRoom3;
    ProgressBar progressGost3, progressFood3, progressWash3, progressSleep3;
    Button wash;
    ImageView ChinchaMud, Chincha3;

    DatabaseReference myBD; // объект, который хранит ссылку на бд
    String T_KEY = "PROGRESSBAR"; // группа для бд
    List<String> newData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bath_room);
        livingRoom3 = (ImageButton)findViewById(R.id.livingroom3);
        kitchen3 = (ImageButton)findViewById(R.id.kitchen3);
        bathRoom3 = (ImageButton)findViewById(R.id.bathroom3);
        bedRoom3 = (ImageButton)findViewById(R.id.bedroom3);

        //кнопка контакта с питомцем
        wash = (Button)findViewById(R.id.washing);

        //прогресс бары(счётчики состояния)
        progressGost3 = (ProgressBar) findViewById(R.id.progress_gost3);
        progressFood3 = (ProgressBar) findViewById(R.id.progress_food3);
        progressWash3 = (ProgressBar) findViewById(R.id.progress_wash3);
        progressSleep3 = (ProgressBar) findViewById(R.id.progress_sleep3);

        newData = new ArrayList<>(); // для хранения текущих значений прогрессбаров
        myBD = FirebaseDatabase.getInstance().getReference(T_KEY);// получили бд
        // получаем акутальные значения для прогрессбаров из БД
        readDataFromDB();
        // изменяем значения прогрессбаров
        //changeProBar();

        //изображение грязной шиншиллы
        ChinchaMud = (ImageView) findViewById(R.id.chincha_mud);
        //изображение чистой шиншиллы
        Chincha3 = (ImageView) findViewById(R.id.chincha3);

        livingRoom3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BathRoom.this, LivingRoom.class);
                startActivity(intent);


            }
        });

        kitchen3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BathRoom.this, Kitchen.class);
                startActivity(intent);

            }
        });

        bedRoom3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BathRoom.this, BedRoom.class);
                startActivity(intent);

            }
        });

        wash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* //исчезновение грязной шиншиллы
                ImageView image = findViewById(R.id.chincha_mud);
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation_bathroom1);
                image.startAnimation(animation);
                //появление чистой шиншиллы
                ImageView image1 = findViewById(R.id.chincha_hungry);
                Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation_bathroom2);
                image1.startAnimation(animation1); */
                //анимация питомца
                ChinchaMud.animate().alpha(0f).setDuration(5000);
                Chincha3.animate().alpha(1f).setDuration(5000);

            }
        });

        // начальное значение счётчиков
        /*progressGost3.setProgress(100);
        progressFood3.setProgress(100);
        progressWash3.setProgress(100);
        progressSleep3.setProgress(100);*/

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
                progressGost3.setProgress(curval1);

                int curval2 = Integer.parseInt(newData.get(1));
                progressFood3.setProgress(curval2);

                int curval3 = Integer.parseInt(newData.get(2));
                progressWash3.setProgress(curval3);

                int curval4 = Integer.parseInt(newData.get(3));
                progressSleep3.setProgress(curval4);
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
        int currentProBar1 = progressGost3.getProgress(); // получаем текущее значение прогрессбара
        ObjectAnimator anim = ObjectAnimator.ofInt(progressGost3, "progress", currentProBar1, 0);
        anim.setDuration(100000); // изменение каждые 100 сек
        anim.start();
        // изменяем значение 2 прогрессбара
        int currentProBar2 = progressFood3.getProgress(); // получаем текущее значение прогрессбара
        ObjectAnimator anim2 = ObjectAnimator.ofInt(progressFood3, "progress", currentProBar2, 0);
        anim2.setDuration(200000); // изменение каждые 200 сек
        anim2.start();
        // изменяем значение 3 прогрессбара
        int currentProBar3 = progressWash3.getProgress(); // получаем текущее значение прогрессбара
        ObjectAnimator anim3 = ObjectAnimator.ofInt(progressWash3, "progress", currentProBar3, 0);
        anim3.setDuration(300000); // изменение каждые 300 сек
        anim3.start();
        // изменяем значение 4 прогрессбара
        int currentProBar4 = progressSleep3.getProgress(); // получаем текущее значение прогрессбара
        ObjectAnimator anim4 = ObjectAnimator.ofInt(progressSleep3, "progress", currentProBar4, 0);
        anim4.setDuration(300500);  // изменение каждые 300,5 сек
        anim4.start();
    }




}