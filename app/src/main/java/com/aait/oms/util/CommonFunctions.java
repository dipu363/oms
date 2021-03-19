package com.aait.oms.util;

import com.google.gson.JsonParseException;

import java.io.IOException;
public interface CommonFunctions {

   /* default  <T> T objectMapperReadValue(String content, Class<T> valueType) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {

            return objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).readValue(content, valueType);

        } catch (JsonParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
        //  return null;
    }*/
}