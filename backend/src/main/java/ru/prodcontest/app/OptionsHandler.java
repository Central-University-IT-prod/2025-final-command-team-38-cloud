package ru.prodcontest.app;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Hidden
public class OptionsHandler {
    @RequestMapping(method = RequestMethod.OPTIONS)
    public String options() {
        return """
                Access-Control-Request-Headers: *
                Access-Control-Request-Method: *""";
    }
}
