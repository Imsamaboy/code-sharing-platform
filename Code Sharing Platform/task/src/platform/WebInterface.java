package platform;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class WebInterface {
    @GetMapping(value = "/code", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView postResponseTextHtml() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("code");
        return modelAndView;
    }
}
