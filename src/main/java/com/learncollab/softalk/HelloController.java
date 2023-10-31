package com.learncollab.softalk;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * React <-> Spring 연동 테스트 컨트롤러
 */
@RestController
public class HelloController {

    @GetMapping("/api/hello")
    public String test() {
        return "hello test success!";
    }

}
