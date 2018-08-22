package de.dm.playground.springgateway.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class GwInfoController {

  @GetMapping("/controllers/gwinfo")
  public Mono<String> getHelloWorld() {
    return Mono.just("Gw Info");
  }

}

