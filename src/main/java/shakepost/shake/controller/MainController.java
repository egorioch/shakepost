package shakepost.shake.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import shakepost.shake.domain.User;
import shakepost.shake.repo.MessageRepo;

import java.util.HashMap;

@Controller
public class MainController {
    public final MessageRepo messageRepo;

    @Value("${spring.profiles.active}")
    private String profile;

    @Autowired
    public MainController(MessageRepo messageRepo) {
        this.messageRepo = messageRepo;
    }

    @GetMapping("/")
    public String startPage(Model model, @AuthenticationPrincipal User user) {
        HashMap<String, Object> frontendData = new HashMap<>();

        frontendData.put("profile", user);
        frontendData.put("messages", messageRepo.findAll());

        model.addAttribute("frontendData", frontendData);
        model.addAttribute("isDevMode", "dev".equals(profile));

        return "index";
    }
}
