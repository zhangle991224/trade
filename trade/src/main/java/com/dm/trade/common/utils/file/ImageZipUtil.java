package com.dm.trade.common.utils.file;

import net.coobird.thumbnailator.Thumbnails;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 图片压缩工具
 *
 * @author zhongchao
 * @Date 2017-12-21
 * @since v1.0.1
 */
public class ImageZipUtil {
    public static void String(String formPic, String toPic1) throws IOException {
        Thumbnails.of(formPic).outputQuality(0.25f).toFile(toPic1);
    }

    public static void outputQuality(InputStream formPic, String toPic1) throws IOException {
        Thumbnails.of(formPic).scale(0.4f).outputQuality(0.4f).toFile(toPic1);
    }

    public static void outputQuality(InputStream formPic,OutputStream os) throws IOException {
        Thumbnails.of(formPic).scale(0.4f).outputQuality(0.4f).toOutputStream(os);
    }
}