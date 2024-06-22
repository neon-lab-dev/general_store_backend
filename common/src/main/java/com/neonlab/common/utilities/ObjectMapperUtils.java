package com.neonlab.common.utilities;
import com.neonlab.common.expectations.ServerException;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;

import static com.neonlab.common.constants.GlobalConstants.ERROR_OCCURRED;

@Slf4j
@UtilityClass
public class ObjectMapperUtils {

    private static final ModelMapper mapper = new ModelMapper();

    static {
        mapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
    }

    public static <S, D> D map(S source, Class<D> destinationClass) throws ServerException {
       try {
           return mapper.map(source, destinationClass);
       }catch (Exception e){
           log.warn(e.getMessage(),e);
           throw new ServerException(String.format(ERROR_OCCURRED,e.getMessage()));
       }
    }

    public static void map(Object source, Object destination) throws ServerException {
       try {
           mapper.map(source, destination);
       }catch (Exception e){
           log.warn(e.getMessage(),e);
           throw new ServerException(String.format(ERROR_OCCURRED,e.getMessage()));
       }
    }

}
