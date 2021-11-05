package com.glory.gloryControllerEntrance.main;


import com.glory.gloryUtils.anno.NoNeedLogin;
import com.glory.gloryUtils.constants.Route;
import com.glory.gloryUtils.utils.HttpUtil;
import com.glory.gloryUtils.utils.MonipdbUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
public class MainController {

    @GetMapping(Route.main)
    @NoNeedLogin
    public String star(HttpServletRequest request) {
        String ip = HttpUtil.getRemoteAddr(request);
        String ipAddress = MonipdbUtils.ip2Address(ip);
        return "project successful startup；your ip is " + ip + " " + ipAddress;
    }
}
