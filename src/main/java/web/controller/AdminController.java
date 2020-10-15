package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.repository.RoleDao;
import web.repository.UserDao;

import java.util.Collections;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public String adminPage(ModelMap modelMap) {
        modelMap.addAttribute("users", userDao.findAll());
        return "admin";
    }

    @GetMapping("/add")
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        return "add";
    }

    @PostMapping("/add")
    public String registerNewUser(@ModelAttribute User user) {
        if (userDao.findByUsername(user.getUsername()) == null) {
            if (user.getPassword().equals(user.getPasswordRepeat())) {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                user.setRoles(Collections.singleton(roleDao.findById(1L).get()));
                userDao.save(user);
            }
        }
        return "redirect:/admin";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, ModelMap modelMap) {
        User user = userDao.findById(id).get();
        modelMap.addAttribute("user", user);
        return "edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@ModelAttribute User user) {
        userDao.save(user);
        return "redirect:/admin";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        userDao.deleteById(id);
        return "redirect:/admin";
    }
}
