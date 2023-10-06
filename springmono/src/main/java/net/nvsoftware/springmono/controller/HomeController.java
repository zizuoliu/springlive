package net.nvsoftware.springmono.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class HomeController {
    @RequestMapping("/")
    public String home() {
        return "NVSoftware Home";
    }
}
