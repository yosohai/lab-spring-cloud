package com.lab.nosql.controller;

import com.lab.nosql.annotation.NotControllerResponseAdvice;
import com.lab.nosql.bean.UserForm;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@RestController
public class RestTestController {

    @GetMapping(value = "/api", produces = {"application/json;charset=UTF-8"})
    public String form(@Valid UserForm userForm, BindingResult bindingResult, RedirectAttributes atts) {
        if (bindingResult.hasErrors()) {
            return "form";
        }
        atts.addAttribute("name", userForm.getName());
        atts.addAttribute("email", userForm.getEmail());
        return "";
    }

    @GetMapping(value = "/api2")
    public String form2(@Valid UserForm userForm, BindingResult bindingResult, RedirectAttributes atts) {
        if (bindingResult.hasErrors()) {
            return "form";
        }
        atts.addAttribute("name", userForm.getName());
        atts.addAttribute("email", userForm.getEmail());
        return "";
    }

    @GetMapping(value = "/health", produces = {"application/json;charset=UTF-8"})
    @NotControllerResponseAdvice
    public String health() {
        return "success";
    }
}