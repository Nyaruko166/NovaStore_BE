package com.sd64.novastore.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Random;

public class FileUtil {

    public static String copyFile(MultipartFile file, String fileName, String uploadPath) {
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        Path path = Paths.get(uploadPath);
        try (InputStream inputStream = file.getInputStream()) {
            Path filePath = path.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            return filePath.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String rename(String oldNamePath, String newName) {
        File file = new File(oldNamePath);
        if (file.exists()) {
            // Lấy phần mở rộng của tệp gốc
            String fileExtension = getFileExtension(file);

            // Xây dựng tên tệp mới bằng cách kết hợp tên mới và phần mở rộng
            String newFileNameWithExtension = newName + "." + fileExtension;

            // Tạo một tệp mới với tên đã đổi và phần mở rộng giữ nguyên
            String newFilePath = file.getParent() + "\\" + newFileNameWithExtension;
            File newFile = new File(newFilePath);
            file.renameTo(newFile);
            return newFileNameWithExtension;
        }
        return null;
    }

    public static String getFileExtension(File file) {
        String fileName = file.getName();
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex != -1 && lastDotIndex < fileName.length() - 1) {
            return fileName.substring(lastDotIndex + 1);
        }
        return "";
    }

    public static String rmExt(String fileName) {
        return fileName.replaceFirst("[.][^.]+$", "");
    }
}
