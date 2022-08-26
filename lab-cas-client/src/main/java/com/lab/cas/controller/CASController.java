package com.lab.cas.controller;

import org.jasig.cas.client.authentication.AttributePrincipal;
import org.jasig.cas.client.validation.Assertion;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import static org.jasig.cas.client.util.AbstractCasFilter.CONST_CAS_ASSERTION;

@RestController
public class CASController {
    /**
     * cas 单点登录
     *
     * @param request 请求头(姓名+身份证号)
     * @return
     */
    @GetMapping(value = "/api/loginByNameAndCardNo")
    public String loginByNameAndCardNo(HttpServletRequest request) {
/*        CasUserInfo userInfo = CasUtil.getCasUserInfoFromCas(request);
        log.info("userInfo = " + JSONObject.toJSON(userInfo));
        String url = "main";
        MadStudent student = new MadStudent();
        student.setName(userInfo.getAttributes().get("Name").toString());
        student.setCardNo(userInfo.getAttributes().get("IdCard").toString());
        // 登录用户校验
        // xxxxx
        // 用户数据为 true
        // 跳转页面
        return "url";
    } else {
        return "redirect:" + casUrl;
    }*/
        return "";
    }

    @RequestMapping("/sso-test1")
    public String test1(HttpSession session) {

        Assertion assertion = (Assertion) session.getAttribute(CONST_CAS_ASSERTION);
        AttributePrincipal principal = assertion.getPrincipal();
        String loginName = principal.getName();

        return "sso-test1，当前登录账户" + loginName;
    }
}
