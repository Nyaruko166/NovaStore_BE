package com.sd64.novastore.config;

import lombok.Getter;

import java.util.Random;

public class MailConfig {

    public String company = "Nova Store";

    public String contact = "novastorecskh@gmail.com";

    public Integer randomCode() {
        Random random = new Random();
        int min = 100000; // Số nhỏ nhất có thể tạo
        int max = 999999; // Số lớn nhất có thể tạo
        return random.nextInt(max - min) + min;
    }

}
