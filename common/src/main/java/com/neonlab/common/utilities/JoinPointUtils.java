package com.neonlab.common.utilities;

import lombok.experimental.UtilityClass;
import org.aspectj.lang.JoinPoint;

import java.util.Arrays;
import java.util.Objects;

import static com.neonlab.common.constants.GlobalConstants.EMPTY_STRING;
import static com.neonlab.common.constants.GlobalConstants.Symbols.COMMA;
import static com.neonlab.common.constants.GlobalConstants.Symbols.DOUBLE_COLON;

@UtilityClass
public class JoinPointUtils {

    /**
     * Gets method name.
     *
     * @param joinPoint the join point
     * @return the method name
     */
    public static String getMethodName(final JoinPoint joinPoint) {
        return joinPoint.getSignature().getDeclaringType().getSimpleName() +
                DOUBLE_COLON + joinPoint.getSignature().getName();
    }

    /**
     * Gets params.
     *
     * @param joinPoint the join point
     * @return the params
     */
    public static String getParams(final JoinPoint joinPoint) {
        return Arrays.stream(joinPoint.getArgs()).filter(Objects::nonNull).map(Object::toString)
                .reduce((s, s2) -> s + COMMA + s2).orElse(EMPTY_STRING);
    }

}
