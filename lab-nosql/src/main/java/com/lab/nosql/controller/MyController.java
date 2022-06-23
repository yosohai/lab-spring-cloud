package com.lab.nosql.controller;

import com.lab.nosql.bean.UserForm;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;


@Controller
public class MyController {

    @GetMapping(value = "/")
    public String form(UserForm userForm) {

        return "form";
    }

    @PostMapping("/")
    public String checkForm(@Valid UserForm userForm, BindingResult bindingResult, RedirectAttributes atts) {

        if (bindingResult.hasErrors()) {
            return "form";
        }

        atts.addAttribute("name", userForm.getName());
        atts.addAttribute("email", userForm.getEmail());

        return "redirect:/showInfo";
    }

    @GetMapping("/showInfo")
    public String showInfo(@ModelAttribute("name") String name, @ModelAttribute("email") String email) {

        return "showInfo";
    }
}