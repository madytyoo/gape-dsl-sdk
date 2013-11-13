package com.mylaensys.gape.todo;

import java.text.SimpleDateFormat;

import javax.ws.rs.Produces;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

@Provider
@Produces("application/json")
public class JacksonConfigurator implements ContextResolver<ObjectMapper> {

    private ObjectMapper mapper = new ObjectMapper();

    public JacksonConfigurator() {
    	/* Unrecognized field "field" (class), not marked as ignorable */
    	mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    	
        SerializationConfig serConfig = mapper.getSerializationConfig();
        serConfig.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"));
        DeserializationConfig deserializationConfig = mapper.getDeserializationConfig();
        deserializationConfig.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"));
        mapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    @Override
    public ObjectMapper getContext(Class<?> arg0) {
        return mapper;
    }

}