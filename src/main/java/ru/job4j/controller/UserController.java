package ru.job4j.controller;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.model.User;
import ru.job4j.service.UserService;
import ru.job4j.utility.Utility;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@AllArgsConstructor
@ThreadSafe
public class UserController {
    private UserService service;

    @GetMapping("/registration")
    public String addUserGet(Model model, HttpSession session) {
        Utility.userGet(model, session);
        model.addAttribute("user1", new User(0, " ", " ", " "));
        return "user/addUser";
    }

    @PostMapping("/registration")
    public String addUserPost(@ModelAttribute User user) {
        Optional<User> regUser = service.add(user);
        if (regUser.isEmpty()) {
            return "redirect:/fail";
        }
        return "redirect:/success";
    }

    @GetMapping("/success")
    public String success(Model model, HttpSession session) {
        Utility.userGet(model, session);
        return "user/success";
    }

    @GetMapping("/fail")
    public String fail(Model model, HttpSession session) {
        Utility.userGet(model, session);
        return "user/fail";
    }

    @GetMapping("/loginPage")
    public String loginGet(Model model, @RequestParam(name = "fail",
            required = false) Boolean fail, HttpSession session) {
        Utility.userGet(model, session);
        model.addAttribute("fail", fail != null);
        return "user/login";
    }

    @PostMapping("/login")
    public String loginPost(@ModelAttribute User user, HttpServletRequest req) {
        Optional<User> userDb = service.findUserByLogin(user.getLogin());
        if (userDb.isEmpty()) {
            return "redirect:/loginPage?fail=true";
        }
        HttpSession session = req.getSession();
        session.setAttribute("user", userDb.get());
        return "redirect:/tasks/list";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/loginPage";
    }
}