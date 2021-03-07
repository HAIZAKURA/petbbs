package run.nya.petbbs.util;

import com.alibaba.fastjson.JSON;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.qiniu.util.UrlSafeBase64;
import run.nya.petbbs.common.exception.ApiAsserts;
import run.nya.petbbs.model.entity.QiniuConfig;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 七牛工具类
 *
 * 2021/02/22
 */
public class QiniuUtil {

    /**
     * 上传图片
     *
     * @param  qiniuConfig
     * @param  uploadBytes
     * @return Map
     */
    public Map<String, String> uploadImg(QiniuConfig qiniuConfig, byte[] uploadBytes, String type) {
        Map<String, String> res = new HashMap<>(16);
        Configuration cfg = new Configuration(Region.autoRegion());
        UploadManager uploadManager = new UploadManager(cfg);
        String accessKey = qiniuConfig.getAccesskey();
        String secretKey = qiniuConfig.getSecretkey();
        String bucket = qiniuConfig.getBucket();
        String host = qiniuConfig.getHost();
        Boolean ssl = qiniuConfig.getUsessl();

        try {
            ByteArrayInputStream byteInputStream = new ByteArrayInputStream(uploadBytes);
            Auth auth = Auth.create(accessKey, secretKey);
            StringMap putPolicy = new StringMap();
            putPolicy.put("mimeLimit", "image/*");
            putPolicy.put("fsizeLimit", 20971520);
            putPolicy.put("saveKey", "$(etag)${ext}");
            String upToken = auth.uploadToken(bucket, null, 3600, putPolicy);

            try {
                Response response = uploadManager.put(byteInputStream, null, upToken, null, null);
                DefaultPutRet putRet = JSON.parseObject(response.bodyString(), DefaultPutRet.class);
//                System.out.println(putRet.hash);
//                System.out.println(putRet.key);
                res.put("hash", putRet.hash);
                res.put("key", putRet.key);
                if (ssl) {
                    res.put("url", "https://" + host + "/" + putRet.key);
                } else {
                    res.put("url", "http://" + host + "/" + putRet.key);
                }
            } catch (QiniuException ex) {
                ApiAsserts.fail("上传失败");
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    ex2.printStackTrace();
                }
            }
        } catch (Exception e) {
            ApiAsserts.fail("上传失败");
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 上传视频
     *
     * @param  qiniuConfig
     * @param  uploadBytes
     * @return Map
     */
    public Map<String, String> uploadVideo(QiniuConfig qiniuConfig, byte[] uploadBytes) {
        Map<String, String> res = new HashMap<>(16);
        Configuration cfg = new Configuration(Region.autoRegion());
        UploadManager uploadManager = new UploadManager(cfg);
        String accessKey = qiniuConfig.getAccesskey();
        String secretKey = qiniuConfig.getSecretkey();
        String bucket = qiniuConfig.getBucket();
        String host = qiniuConfig.getHost();
        Boolean ssl = qiniuConfig.getUsessl();

        try {
            ByteArrayInputStream byteInputStream = new ByteArrayInputStream(uploadBytes);
            Auth auth = Auth.create(accessKey, secretKey);
            String fops = "avthumb/mp4/ab/160k/ar/44100/acodec/libfaac/r/30/vb/1000k/vcodec/libx264/s/1280x720/autoscale/1/stripmeta/0";
            String save = UrlSafeBase64.encodeToString("petbbs:${etag}${ext}");
            StringMap putPolicy = new StringMap();
            putPolicy.put("mimeLimit", "video/*");
            putPolicy.put("fsizeLimit", 104857600);
            putPolicy.put("persistentOps", fops + "|saveas/" + save);
            putPolicy.put("persistentPipeline", "short");
            putPolicy.put("saveKey", "$(etag)${ext}");
            String upToken = auth.uploadToken(bucket, null, 3600, putPolicy);

            try {
                Response response = uploadManager.put(byteInputStream, null, upToken, null, null);
                DefaultPutRet putRet = JSON.parseObject(response.bodyString(), DefaultPutRet.class);
                res.put("hash", putRet.hash);
                res.put("key", putRet.key);
                if (ssl) {
                    res.put("url", "https://" + host + "/" + putRet.key);
                } else {
                    res.put("url", "http://" + host + "/" + putRet.key);
                }

            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    ex2.printStackTrace();
                }
                ApiAsserts.fail("上传失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            ApiAsserts.fail("上传失败");
        }
        return res;
    }

}
