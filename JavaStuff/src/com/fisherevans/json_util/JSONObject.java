package com.fisherevans.webservutil.json;

import java.lang.reflect.Method;

/**
 * Author: Fisher Evans
 * Date: 7/21/14
 */
public class JSONObject {

    public String getJSON(int recursiveDepth) {
        String json = "";
        try {
            int fieldNumber = 0;
            for(Method method : this.getClass().getMethods()) {
                if(method.isAnnotationPresent(JSONField.class)) {
                    JSONField jsonField = method.getAnnotation(JSONField.class);
                    Object value = method.invoke(this);
                    if(fieldNumber++ > 0)
                        json += ",";
                    json += "\"" + jsonField.name() + "\":";
                    if(value.getClass().isAssignableFrom(JSONObject.class)) {
                        json = ((JSONObject)value).getJSON(recursiveDepth-1);
                    } else if(value.getClass().isAssignableFrom(Number.class)) {
                        json += value.toString();
                    } else {
                        json += "\"" + value.toString() + "\"";
                    }
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
            json = "null";
        }
        return "{" + json + "}";
    }
}
