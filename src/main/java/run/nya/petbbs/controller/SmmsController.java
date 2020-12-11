package run.nya.petbbs.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import run.nya.petbbs.entity.SmmsConfig;
import run.nya.petbbs.service.SmmsService;

@RestController
@Api(tags = {"SM.MS"})
public class SmmsController {

    /**
     * code  1 操作成功
     * code  0 操作失败
     * code -1 权限不足
     */

    @Autowired(required = false)
    private SmmsService smmsService;

    /**
     * @apiNote Get SM.MS Config Item Value
     * @return  res JSON String
     */
    @RequestMapping(value = "/api/smms/{item}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "Get SM.MS Config Item Value", httpMethod = "GET", notes = "Admin")
    public Object getSmmsConfig(@PathVariable("item") String item) {
        JSONObject res = new JSONObject();
        JSONObject data = new JSONObject();
        try {
            String value = smmsService.getSmmsConfig(item).getSmmsValue();
            if (value == null || value.isEmpty()) {
                value = "";
            }
            data.put(item, value);
            res.put("code", 1);
        } catch (Exception e) {
            e.printStackTrace();
            res.put("code", 0);
        }
        res.put("data", data);
        return res.toJSONString();
    }

    /**
     * @apiNote Set SM.MS Item Value
     * @param   body JSON Object
     * @return  res  JSON String
     */
    @RequestMapping(value = "/api/smms/{item}", method = RequestMethod.PUT, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "Set SM.MS Config Item Value", httpMethod = "PUT", notes = "Admin")
    public Object setSmmsConfig(@PathVariable("item") String item, @RequestBody JSONObject body) {
        JSONObject res = new JSONObject();
        JSONObject data = new JSONObject();
        SmmsConfig reqSmms = JSON.parseObject(body.toJSONString(), SmmsConfig.class);
        try {
            Integer back = smmsService.setSmmsConfig(item, reqSmms.getSmmsValue());
            if (back > 0) {
                data.put(item, reqSmms.getSmmsValue());
                res.put("code", 1);
            } else {
                data.put(item, "");
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
