package com.sd64.novastore.utils;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class TempCodeManager {

    private static final Map<String, TemporaryCode> temporaryCodes = new HashMap<>();
    private static final long EXPIRATION_TIME = 600000; // 10 phút

    private static class TemporaryCode {
        @Getter
        private String code;
        private long expirationTime;

        public TemporaryCode(String code) {
            this.code = code;
            this.expirationTime = System.currentTimeMillis() + EXPIRATION_TIME;
        }

        public boolean isExpired() {
            return System.currentTimeMillis() > expirationTime;
        }

        public void resetExpirationTime() {
            this.expirationTime = System.currentTimeMillis() + EXPIRATION_TIME;
        }
    }

    private String generateTemporaryCode(String id) {
        Random random = new Random();
        int min = 100000;
        int max = 999999;
        int randomNumber = random.nextInt(max - min + 1) + min;
        String code = String.format("%06d", randomNumber);
        temporaryCodes.put(id, new TemporaryCode(code));
        return code;
    }

    public Integer verifyTemporaryCode(String id, String code) {
        TemporaryCode storedCode = temporaryCodes.get(id);
        if (storedCode != null && storedCode.getCode().equals(code)) {
            if (storedCode.isExpired()) {
                temporaryCodes.remove(id); // Xoá mã đã hết hạn
                return 1;
            } else {
                return 0;
            }
        }
        return 2;
    }

    public String createTemporaryCode(String id) {
        if (temporaryCodes.containsKey(id)) {
            TemporaryCode storedCode = temporaryCodes.get(id);
            storedCode.resetExpirationTime(); // Reset thời gian hết hạn
            String newCode = generateTemporaryCode(id); // Tạo mã mới
            System.out.println("Reset thành công. Mã mới: " + newCode);
            return newCode;
        } else {
            return generateTemporaryCode(id);
        }
    }

    public void removeCode(String id) {
        temporaryCodes.remove(id);
    }

}
