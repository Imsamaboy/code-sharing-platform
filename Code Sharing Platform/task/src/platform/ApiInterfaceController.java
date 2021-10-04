package platform;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
public class ApiInterfaceController {
    @GetMapping(value = "/api/code", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> postResponseApplicationJson() {
        return Collections.singletonMap("code", "public static void main(String[] args) {\n    SpringApplication.run(CodeSharingPlatform.class, args);\n}");
    }
}
