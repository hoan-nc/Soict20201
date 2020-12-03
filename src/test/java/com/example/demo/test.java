package com.example.demo;

import java.math.BigDecimal;

public class test {

    public static void main(String[] args) {
        Long a = 5L;
        BigDecimal b = new BigDecimal("10");
        BigDecimal c = new BigDecimal(0);
        c.add(b.multiply(new BigDecimal(a)));
        System.out.println(c);
    }
}
