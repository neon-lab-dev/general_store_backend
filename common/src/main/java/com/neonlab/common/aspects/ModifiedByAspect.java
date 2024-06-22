package com.neonlab.common.aspects;

import com.neonlab.common.entities.AuthUser;
import com.neonlab.common.entities.Generic;
import com.neonlab.common.expectations.InvalidInputException;
import com.neonlab.common.services.UserService;
import com.neonlab.common.utilities.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Aspect
@Slf4j
@Component
@RequiredArgsConstructor
public class ModifiedByAspect {

    private final UserService userService;

    /**
     * Pointcut targeting all the CrudRepository save methods.
     */
    @Pointcut("execution(* org.springframework.data.repository.*.save(..))")
    public void pointcutRepositorySave(){}


    /**
     * The below advice is run before the targeted method and adds the
     * modifiedBy information to the entity object that is passed to the
     * save method.
     *
     * If the security context has the user than it uses that else
     * it passes what ever has been already placed by used in the
     * entity object
     *
     * @param joinPoint the joinPoint information
     */
    @Before("pointcutRepositorySave()")
    public void beforeSave(JoinPoint joinPoint){
        var args = joinPoint.getArgs();
        var entity = (Generic) args[0];
        entity.setModifiedBy(getUsername(entity));
        entity.setModifiedAt(new Date());
    }

    private String getUsername(Generic generic){
        try {
            var userMaybe = Optional.ofNullable(SecurityUtils.getLoggedInUser());
            if (userMaybe.isPresent() && (userMaybe.get() instanceof AuthUser authUser)){
                var user = userService.fetchById(authUser.getUserId());
                return user.getPrimaryPhoneNo();
            }
        } catch (InvalidInputException e) {
            log.warn(e.getMessage());
        }
        return generic.getModifiedBy();
    }

}
