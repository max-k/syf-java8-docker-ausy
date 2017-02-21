package com.ausy.syf.rps.ws;

import com.ausy.syf.rps.ws.model.CardEnum;
import com.ausy.syf.rps.ws.model.ResultEnum;
import com.ausy.syf.rps.ws.model.ResultResponse;
import com.ausy.syf.rps.ws.service.PlayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class HomeController {

    @Autowired
    private PlayService playService;

    @RequestMapping("/")
    public String index() {
        return "Made with <3";
    }

    @RequestMapping("/play/{name}")
    public ResultResponse play(@PathVariable String name, @RequestParam String cardName) {
        return playService.play(name, CardEnum.valueOf(cardName));
    }
}