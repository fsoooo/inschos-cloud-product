package com.inschos.proposal.kit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by IceAnt on 2018/3/24.
 */
public class XmlKit {

    private static XmlMapper xmlMapper = new XmlMapper();
    public static <T> T xml2Bean(String xml, Class<T> clazz) {
        try {
            if (StringKit.isEmpty(xml)) {
                return null;
            }
            xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            T value = xmlMapper.readValue(xml, clazz);
            return value;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static <T> T xmlToBean(InputStream xmlInputStream, Class<T> cls) throws IOException {
        T obj = xmlMapper.readValue(xmlInputStream, cls);
        return obj;
    }

    public static <T> T xmlToBean(File xmlFile, Class<T> cls) throws IOException {
        T obj = xmlMapper.readValue(xmlFile, cls);
        return obj;
    }

    public static String bean2Xml(Object o){

        String string = null;
        try {
            string = xmlMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return string;
    }

}


