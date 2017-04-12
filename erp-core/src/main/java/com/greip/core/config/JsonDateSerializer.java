package com.greip.core.config;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by apple on 3/02/17.
 */
@Component
public class JsonDateSerializer extends JsonSerializer<Date> {
    @Override
    public void serialize(Date value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssz");
        jgen.writeString(formatter.format(value));
    }
}
