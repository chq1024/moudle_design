package org.beikei.design.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;

public class JsonHelper {
    private final static JsonMapper jsonMapper;

    static {
        jsonMapper = new JsonMapper();
        // 允许转义字符
        jsonMapper.configure(JsonReadFeature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER.mappedFeature(),false);
        // 反序列化时忽略未知属性
        jsonMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        // 允许空属性对象
        jsonMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);
    }

    public static String toJson(Object params) {
        try {
            return jsonMapper.writeValueAsString(params);
        } catch (JsonProcessingException parseException) {
            throw new RuntimeException("解析异常");
        }
    }
}
