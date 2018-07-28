package com.dm.trade.common.utils;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;

import java.io.File;
import java.net.URL;
import java.util.UUID;

/**
 * @author zhongchao
 * @title QiNiuUploadUtil.java
 * @Date 2018-01-02
 * @since v1.0.0
 */
public class QiNiuUploadUtil {
    private static final String ACCESS_KEY = "mZ7Y2-EZyFfpqGlyQPBIP5F4DSCGZmalBYV__vuf";
    private static final String SECRET_KEY = "vT56BjaDPLkG2DYOxY0_yJLdr6HwjMBez1MkMoLT";
    private static final String BUCKET = "tt32";
    public static final Zone ZONE = Zone.zone2();

    /**
     * 上传文件到七牛云
     * @param datas byte
     * @param fileName 文件名称
     * @return
     * @throws QiniuException
     */
    public static String upLoad(byte[] datas, String fileName) throws QiniuException {
        Configuration cfg = new Configuration(ZONE);
        UploadManager uploadManager = new UploadManager(cfg);
        Response response = uploadManager.put(datas, fileName, getToken());
        //解析上传成功的结果
        DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
        return putRet.key;
    }

    /**
     * 上传文件到七牛云  默认文件名为uuid
     * @param datas
     * @return
     * @throws QiniuException
     */
    public static String upLoad(byte[] datas) throws QiniuException {
        Configuration cfg = new Configuration(ZONE);
        UploadManager uploadManager = new UploadManager(cfg);
        Response response = uploadManager.put(datas, UUID.randomUUID().toString(), getToken());
        //解析上传成功的结果
        DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
        return putRet.key;
    }

    public static String upLoad(File file, String fileName) throws QiniuException {
        Configuration cfg = new Configuration(ZONE);
        UploadManager uploadManager = new UploadManager(cfg);
        Response response = uploadManager.put(file, fileName, getToken());
        //解析上传成功的结果
        DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
        return putRet.key;
    }

    /**
     * @return
     */
    private static String getToken() {
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        return auth.uploadToken(BUCKET);
    }

    /**
     * 要覆盖的文件名称
     *
     * @param fileName
     * @return
     */
    private static String getToken(String fileName) {
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        return auth.uploadToken(BUCKET, fileName);
    }

    public static void main(String[] args) throws QiniuException {
        QiNiuUploadUtil.class.getClassLoader().getResource("dc9c52182c701babc2ef42.jpg");
        URL resource = ClassLoader.getSystemResource("dc9c52182c701babc2ef42.jpg");
        File file = new File(resource.getFile());
        String s = QiNiuUploadUtil.upLoad(file, UUID.randomUUID().toString());
    }
}
