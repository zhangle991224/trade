package com.dm.trade.common.utils.file;

import com.dm.trade.common.utils.io.ByteOutputStream;
import org.springframework.util.StreamUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class FileUtil {

    public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception {
        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath + fileName);
        out.write(file);
        out.flush();
        out.close();
    }

    public static void uploadQiNiu(InputStream file, int fileType, String fileName) throws IOException {
        if (fileType == 0) {
            int available = file.available();
            // 小于  1M 直接上传
            if (available < 1048576) {
                QiNiuUploadUtil.upLoad(StreamUtils.copyToByteArray(file), fileName);
            } else {
                ByteOutputStream out = new ByteOutputStream();
                ImageZipUtil.outputQuality(file, out);
                QiNiuUploadUtil.upLoad(out.getBytes(), fileName);
            }
        } else {
            // 其它文件 直接上传
            QiNiuUploadUtil.upLoad(StreamUtils.copyToByteArray(file), fileName);
        }
    }


    public static void uploadFile(InputStream file, int fileType, String filePath, String fileName) throws Exception {
        if (fileType == 0) {
            int available = file.available();
            if (available < 1048576) {
                uploadFile(StreamUtils.copyToByteArray(file), filePath, fileName);
                return;
            }
            File targetFile = new File(filePath);
            if (!targetFile.exists()) {
                targetFile.mkdirs();
            }
            ImageZipUtil.outputQuality(file, filePath + fileName);
        } else {
            uploadFile(StreamUtils.copyToByteArray(file), filePath, fileName);
        }
    }

    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static String renameToUUID(String fileName) {
        return UUID.randomUUID() + "." + fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}
