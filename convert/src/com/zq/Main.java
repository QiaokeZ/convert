package com.zq;

import com.zq.core.Converter;
import com.zq.core.Options;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        Entity entity = new Entity();
        entity.username = "java";
        entity.password = "abc";
        entity.a = "a";
        entity.b = "b";
        entity.c = "c";
        entity.d = "d";
        entity.e = "e";
        entity.f = "f";

        //将Entity转换Vo对象
        Vo vo = Converter.convert(entity, Vo.class);
        System.out.println(vo.username);

        //属性名映射
        Map<String, String> mapping = new HashMap<>();
        mapping.put("a", "x");//entity.a -> vo1.x
        mapping.put("b", "y");//entity.b -> vo1.y
        mapping.put("c", "z");//entity.c -> vo1.z

        //白名单输出模式(只输出username, x, y, z元素信息)
        Set<String> whitelist = new HashSet<>();
        whitelist.add("username");
        whitelist.add("x");
        whitelist.add("y");
        whitelist.add("z");

        Options options = Options.builder()
                .outputPattern(Options.OutputPattern.whitelist(whitelist))
                .mapping(mapping)
                .build();
        Vo vo1 = Converter.convert(entity, Vo.class, options);
        System.out.println(vo1.x);
        System.out.println(vo1.y);
        System.out.println(vo1.z);
    }
}
