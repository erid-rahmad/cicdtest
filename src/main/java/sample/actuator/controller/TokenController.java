package sample.actuator.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sample.actuator.model.Request;
import sample.actuator.model.Response;
import sample.actuator.model.Token;
import sample.actuator.service.GeneralService;
import sample.actuator.service.TokenService;
import sample.actuator.utils.MapperJSONUtil;

@Controller
@RequestMapping("api/v1")
@Slf4j
public class TokenController {

    @Autowired
    TokenService tokenService;

    @PostMapping(value = "/token")
    @ResponseBody
    public Token gettoken(@RequestBody Token token1) {
        log.info(token1.getNorek());
        Token token = tokenService.generatetoken(token1.getNorek());
        return token;
    }
}
