package com.next.huyao.film.example.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/vl/example/")
@Api("example swagger Controller")
public class ExampleController {

    @ApiOperation(value = "测试SwaggerValue", notes = "测试SwaggerNotes")
    @ApiImplicitParam(name = "str",
            value = "入参str", paramType = "query", required = true, dataType = "string")
    @RequestMapping(value="test")
    public String test(String str){
        System.out.println("str="+str);
        return "str="+str;
    }
}
