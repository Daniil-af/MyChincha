package com.example.mychincha;

// класс для описания задачи
public class ProBar {
    // поля класса
    public String id, n, proBar1, proBar2,proBar3,proBar4;

    // конструктор без параметров
    public ProBar() {
    }

    // конструктор с параметрами

    public ProBar(String id, String n, String proBar1, String proBar2, String proBar3, String proBar4) {
        this.id = id;
        this.n = n;
        this.proBar1 = proBar1;
        this.proBar2 = proBar2;
        this.proBar3 = proBar3;
        this.proBar4 = proBar4;
    }
}
