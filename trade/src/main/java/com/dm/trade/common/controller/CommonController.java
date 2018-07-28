package com.dm.trade.common.controller;

import com.dm.trade.common.config.Constant;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author zhongchao
 * @title CommonController.java
 * @Date 2018-01-24
 * @since v1.0.0
 */
@Controller
public class CommonController {

    /**
     * 请求重定向
     *
     * @return
     */
    @RequestMapping("/files/{file:.+}")
    public String fileRedirect(@PathVariable("file") String fileName) {
        return "redirect:" + Constant.QINIU_HOST + fileName;
    }
}
