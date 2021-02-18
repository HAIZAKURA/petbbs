package run.nya.petbbs.controller;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import run.nya.petbbs.model.entity.QiniuConfig;
import run.nya.petbbs.service.QiniuService;
import run.nya.petbbs.util.QiniuUtil;

@RestController
@Api(tags = {"QiNiu"})
public class QiniuController {

    /**
     * code  1 操作成功
     * code  0 操作失败
     * code -1 权限不足
     */

    @Autowired(required = false)
    private QiniuService qiniuService;

    /**
     * @apiNote Get QiNiu Config
     * @return  res  JSONString
     */
    @RequestMapping(value = "/api/qiniu", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "Get QiNiu Config", httpMethod = "GET", notes = "Admin")
    public Object getQiniuConfig() {
        JSONObject res = new JSONObject();
        try {
            QiniuConfig qiniuConfig = qiniuService.getQiniuConfig();
            res.put("data", qiniuConfig);
            res.put("code", 1);
        } catch (Exception e) {
            e.printStackTrace();
            res.put("code", 0);
        }
        return res.toJSONString();
    }

    /**
     * @apiNote Upload Files with Name
     * @param   formFile  Images
     * @param   key       String
     * @return  res       JSONString
     */
    @RequestMapping(value = "/api/qiniu/{name}", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "Upload Files with Name", httpMethod = "POST", notes = "Admin/User")
    public Object uploadFileWithName(@RequestParam("file") MultipartFile formFile, @PathVariable("name") String key) {
        JSONObject res = new JSONObject();
        JSONObject data = new JSONObject();
        if (!formFile.isEmpty()) {
            try {
                byte[] byteFile = formFile.getBytes();
                QiniuUtil qiniuUtil = new QiniuUtil();
                QiniuConfig qiniuConfig = qiniuService.getQiniuConfig();
                data = qiniuUtil.uploadImg(qiniuConfig, byteFile, key);
                if (data.getObject("type", Integer.TYPE) == 0) {
                    res.put("code", 0);
                } else {
                    res.put("code", 1);
                }
            } catch (Exception e) {
                res.put("code", 0);
                e.printStackTrace();
            }
        } else {
            res.put("code", 0);
        }
        res.put("data", data);
        return res.toJSONString();
    }

    /**
     * @apiNote Upload Files
     * @param   formFile  Images
     * @return  res       JSONString
     */
    @RequestMapping(value = "/api/qiniu", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "Upload Files", httpMethod = "POST", notes = "Admin/User")
    public Object uploadFile(@RequestParam("file") MultipartFile formFile) {
        JSONObject res = new JSONObject();
        JSONObject data = new JSONObject();
        if (!formFile.isEmpty()) {
            try {
                byte[] byteFile = formFile.getBytes();
                QiniuUtil qiniuUtil = new QiniuUtil();
                QiniuConfig qiniuConfig = qiniuService.getQiniuConfig();
                data = qiniuUtil.uploadImg(qiniuConfig, byteFile, null);
                if (data.getObject("type", Integer.TYPE) == 0) {
                    res.put("code", 0);
                } else {
                    res.put("code", 1);
                }
            } catch (Exception e) {
                res.put("code", 0);
                e.printStackTrace();
            }
        } else {
            res.put("code", 0);
        }
        res.put("data", data);
        return res.toJSONString();
    }
}
