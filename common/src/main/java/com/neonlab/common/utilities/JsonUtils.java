package com.neonlab.common.utilities;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

import static com.neonlab.common.constants.GlobalConstants.EMPTY_STRING;

@Slf4j
@UtilityClass
public class JsonUtils {

    private static final ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
        mapper.registerModule(new Jdk8Module());
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    private static final String PARSING_EXCEPTION_MESSAGE = "Exception occurred while parsing from object: {} to json";
    private static final String PARSING_EXCEPTION_MESSAGE_TO_OBJECT = "Exception occurred while parsing from json : %s to object";


    public static String jsonOf(final Object value){
        try{
            if (Objects.nonNull(value)) {
                return mapper.writeValueAsString(value);
            } else {
                return EMPTY_STRING;
            }
        } catch (Exception e) {
            log.warn(PARSING_EXCEPTION_MESSAGE, value, e);
        }
        return EMPTY_STRING;
    }

    public static <T> T readObjectFromJson(String jsonString,Class<T>valueType) throws JsonParseException {
        try{
            return mapper.readValue(jsonString, valueType);
        }catch (Exception e) {
            log.error(String.format(PARSING_EXCEPTION_MESSAGE_TO_OBJECT,jsonString), e);
            throw new JsonParseException(String.format(PARSING_EXCEPTION_MESSAGE_TO_OBJECT,jsonString));
        }
    }

    public static <T> T readObjectFromJson(String jsonString, TypeReference<T> reference) throws JsonParseException {
        try{
            return mapper.readValue(jsonString, reference);
        }catch (Exception e) {
            log.error(String.format(PARSING_EXCEPTION_MESSAGE_TO_OBJECT,jsonString), e);
            throw new JsonParseException(String.format(PARSING_EXCEPTION_MESSAGE_TO_OBJECT,jsonString));
        }
    }

}
