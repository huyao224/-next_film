package com.next.huyao.film.test;

import com.next.huyao.film.example.bo.LombokUser;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class lombokTest {
    @Test
    public void lomboktest(){
        LombokUser u=LombokUser.builder()
                .passWord("aa")
                .build();
        log.info(String.valueOf(u));
    }
}
