package run.nya.petbbs.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import run.nya.petbbs.model.entity.QiniuConfig;

import java.io.ByteArrayInputStream;

public class QiniuUtil {

    public JSONObject uploadImg(QiniuConfig qiniuConfig, byte[] uploadBytes, String key) {
        JSONObject res = new JSONObject();
        Configuration cfg = new Configuration(Region.autoRegion());
        UploadManager uploadManager = new UploadManager(cfg);
        String accessKey = qiniuConfig.getAccessKey();
        String secretKey = qiniuConfig.getSecretKey();
        String bucket = qiniuConfig.getBucket();

        try {
            ByteArrayInputStream byteInputStream = new ByteArrayInputStream(uploadBytes);
            Auth auth = Auth.create(accessKey, secretKey);
            StringMap putPolicy = new StringMap();
            putPolicy.put("mimeLimit", "image/*");
            String upToken = auth.uploadToken(bucket, key, 3600, putPolicy);

            try {
                Response response = uploadManager.put(byteInputStream, key, upToken, null, null);
                DefaultPutRet putRet = JSON.parseObject(response.bodyString(), DefaultPutRet.class);
//                System.out.println(putRet.hash);
//                System.out.println(putRet.key);
                res.put("hash", putRet.hash);
                res.put("key", putRet.key);
                res.put("type", 1);
            } catch (QiniuException ex) {
                res.put("type", 0);
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    ex2.printStackTrace();
                }
            }
        } catch (Exception ex) {
            res.put("type", 0);
            ex.printStackTrace();
        }
        return res;
    }

}
