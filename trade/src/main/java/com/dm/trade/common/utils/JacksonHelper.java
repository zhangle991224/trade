package com.dm.trade.common.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;

/**
 * Author: Roy.Lust@gmail.com
 * 6/30/16
 * 11:40 PM
 */
public class JacksonHelper {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final ObjectMapper MAPPER2 = new ObjectMapper();

    /**
     * Jackson config method
     *
     * @return ObjectMapper
     */
    public static ObjectMapper getMapper() {
        MAPPER.configure(JsonGenerator.Feature.QUOTE_FIELD_NAMES, false);
        MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return MAPPER;
    }

    /**
     * normal Jackson
     *
     * @return ObjectMapper
     */
    public static ObjectMapper getMapperNomal() {
        MAPPER2.configure(JsonGenerator.Feature.QUOTE_FIELD_NAMES, true);
        MAPPER2.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        MAPPER2.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return MAPPER2;
    }

    public static void main(String[] args) throws IOException {
        String s = "{\n" +
                "   \"access_token\":\"ACCESS_TOKEN\",\n" +
                "   \"expires_in\":7200,\n" +
                "   \"refresh_token\":\"REFRESH_TOKEN\",\n" +
                "   \"openid\":\"OPENID\",\n" +
                "   \"scope\":\"SCOPE\"\n" +
                "}";
        System.out.println(JacksonHelper.getMapper().readTree(s).get("openid").asText());

        ObjectNode attNode = JsonNodeFactory.instance.objectNode();
        attNode.put("Patient_name", "yxd");
        attNode.put("Patient_cardno", "4023");
        attNode.put("Hospital_userid", "HID");
        String redisValue = attNode.toString();

        System.out.println(redisValue);
        ObjectNode objectNode = JacksonHelper.getMapperNomal().readValue(redisValue, ObjectNode.class);
        System.out.println(objectNode.get("Patient_name").asText());
        System.out.println(objectNode.get("Patient_cardno").asText());
        System.out.println(objectNode.get("Hospital_userid").asText());
    }
}