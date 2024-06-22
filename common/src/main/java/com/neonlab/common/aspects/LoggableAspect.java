package com.neonlab.common.aspects;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import static com.neonlab.common.utilities.JoinPointUtils.getMethodName;
import static com.neonlab.common.utilities.JoinPointUtils.getParams;
import static com.neonlab.common.utilities.JsonUtils.jsonOf;

@Slf4j
@Aspect
@Component
@Order(1)
public class LoggableAspect {

    private static final String PROCESS_START_LOG = "{}::Start | Params: {}";
    private static final String PROCESS_STOP_LOG = "{}::Stop | Result: {} | TimeLapsed: {} ms";
    private static final String PROCESS_STOP_LOG_WITHOUT_RESULT = "{}::Stop | TimeLapsed: {} ms";
    private static final String PROCESS_ERROR_LOG = "{}::Error | Error Message: {} | TimeLapsed: {} ms";


    /**
     * Pointcut having loggable annotation.
     */
    @Pointcut("@within(com.neonlab.common.annotations.Loggable)")
    public void pointcutHavingLoggableAnnotation() {}

    /**
     * Around object.
     *
     * @param joinPoint the join point
     * @throws Throwable the throwable
     */
    @Around("pointcutHavingLoggableAnnotation()")
    public Object around(final ProceedingJoinPoint joinPoint) throws Throwable {
        final var methodName = getMethodName(joinPoint);
        final var params = getParams(joinPoint);
        log.info(PROCESS_START_LOG, methodName, params);
        var stopWatch = new StopWatch();
        try{
            stopWatch.start();
            var result = joinPoint.proceed();
            stopWatch.stop();
            if (canPrintResult(result)){
                log.info(PROCESS_STOP_LOG,
                        methodName, jsonOf(result), stopWatch.getTime());
                return result;
            } else {
                log.info(PROCESS_STOP_LOG_WITHOUT_RESULT, methodName, stopWatch.getTime());
                return result;
            }
        } catch (Exception ex){
            handleException(methodName, ex, stopWatch);
            throw ex;
        }
    }

    private void handleException(
            final String methodName,
            final Exception ex,
            final StopWatch stopWatch){
        stopWatch.stop();
        log.error(PROCESS_ERROR_LOG, methodName, ex.getMessage(), stopWatch.getTime());
    }

    private boolean canPrintResult(Object result){
        return !(result instanceof ResponseEntity && ((ResponseEntity) result).getBody() instanceof Resource);
    }


}
