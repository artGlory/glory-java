package com.glory.gloryControllerEntrance.main;


import com.glory.gloryControllerEntrance.constants.Route;
import com.glory.gloryStorageMysql.dao.BisCustomerOptMapper;
import com.glory.gloryStorageMysql.entity.BisCustomerOpt;
import com.glory.gloryUtils.anno.NoNeedLogin;
import com.glory.gloryUtils.utils.JacksonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping(Route.path + Route.Test.path)
public class TestController {

    @Autowired
    private BisCustomerOptMapper bisCustomerOptMapper;

    @GetMapping(Route.Test.test)
    @NoNeedLogin
    public String star(HttpServletRequest request) {
        String result = "";
        BisCustomerOpt bisCustomerOpt = bisCustomerOptMapper.selectByPrimaryKey(320);
        result = JacksonUtil.objectToJsonStr(bisCustomerOpt);
        return result;
    }
}
