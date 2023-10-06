package net.nvsoftware.springmono.controller;

import net.nvsoftware.springmono.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
public class HomeController {
    @RequestMapping("/")
    public String home() {
        return "NVSoftware Home";
    }

    @RequestMapping(value="/user", method= RequestMethod.POST)
    public User user() {
        User user = new User();
        user.setId("3100");
        user.setName("nvsoftware");
        user.setEmail("info@nvsoftware.net");
        return user;
    }

    @GetMapping("/user/{id}/{username}")
    public User userByPathVariable(@PathVariable String id, @PathVariable("username") String name) {
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setEmail("info@nvsoftware.net");
        return user;
    }
}
