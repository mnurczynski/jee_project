package pl.edu.pg.eti.kask.rpg.logger;


import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import jakarta.security.enterprise.SecurityContext;
import pl.edu.pg.eti.kask.rpg.building.entity.Building;

import java.util.Arrays;
import java.util.UUID;
import java.util.logging.Logger;

@Interceptor
@LogOperation
@Priority(Interceptor.Priority.APPLICATION)
public class LoggingInterceptor {

    private static final Logger LOGGER = Logger.getLogger(LoggingInterceptor.class.getName());

    @Inject
    public LoggingInterceptor(SecurityContext securityContext) {
        this.securityContext = securityContext;
    }

    private SecurityContext securityContext;

    @AroundInvoke
    public Object intercept(InvocationContext ctx) throws Exception {
        String login = securityContext.getCallerPrincipal().getName();
        String task = ctx.getMethod().getName();
        String buildingId = Arrays.stream(ctx.getParameters()).filter(arg -> arg instanceof UUID).map(Object::toString).findFirst().orElse(null);
        if(buildingId == null) {
            Building buillding = (Building) Arrays.stream(ctx.getParameters()).filter(arg -> arg instanceof Building).findFirst().get();
            buildingId = buillding.getId().toString();
        }
        System.out.println(String.format("User %s has invoked task %s on %s", login, task, buildingId ));
        return ctx.proceed();
    }
}
