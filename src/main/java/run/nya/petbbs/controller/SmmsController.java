package run.nya.petbbs.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import run.nya.petbbs.entity.SmmsConfig;
import run.nya.petbbs.service.SmmsService;

@RestController
@Api(tags = {"SM.MS"})
public class SmmsController {

    /**
     * code 1 操作成功
     * code 0 操作失败
     */

    @Autowired(required = false)
    private SmmsService smmsService;

    /**
     * @apiNote 获取 SM.MS Token
     * @return  res  JSONString
     */
    @RequestMapping(value = "/api/smms", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "Get SM.MS Token", httpMethod = "GET", notes = "Admin")
    public Object getSmmsToken() {
        JSONObject res = new JSONObject();
        JSONObject data = new JSONObject();
        try {
            String token = smmsService.getSmmsToken();
            if (token == null || token.isEmpty()) {
                token = "";
            }
            data.put("token", token);
            res.put("code", 1);
        } catch (Exception e) {
            e.printStackTrace();
            res.put("code", 0);
        }
        res.put("data", data);
        return res.toJSONString();
    }

    /**
     * @apiNote 设置SM.MS Token
     * @param   body JSONObject
     * @return  res  JSONString
     */
    @RequestMapping(value = "/api/smms", method = RequestMethod.PUT, produces = "application/json;charset=UTF-8")
    public Object setSmmsToken(@RequestBody JSONObject body) {
        JSONObject res = new JSONObject();
        JSONObject data = new JSONObject();
        SmmsConfig reqSmms = JSON.parseObject(body.toJSONString(), SmmsConfig.class);
        try {
            Integer back = smmsService.setSmmsToken(reqSmms.getSmmsValue());
            if (back > 0) {
                data.put("token", reqSmms.getSmmsValue());
                res.put("code", 1);
            } else {
                data.put("token", "");
                res.put("code", 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            res.put("code", 0);
        }
        res.put("data", data);
        return res.toJSONString();
    }
}
