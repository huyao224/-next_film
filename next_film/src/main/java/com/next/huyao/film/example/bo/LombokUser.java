package com.next.huyao.film.example.bo;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LombokUser {
    private String uuid;
    private String userName;
    private String passWord;
}
