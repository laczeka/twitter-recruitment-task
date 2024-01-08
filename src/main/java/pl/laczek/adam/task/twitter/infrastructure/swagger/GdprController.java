package pl.laczek.adam.task.twitter.infrastructure.swagger;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GdprController {
    @GetMapping("/gdpr")
    public String gdpr() {
        return "gdpr";
    }
}
