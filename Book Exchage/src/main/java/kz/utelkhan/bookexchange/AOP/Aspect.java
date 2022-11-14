package kz.utelkhan.bookexchange.AOP;



import kz.utelkhan.bookexchange.jpa.userDetails.RoleRepository;
import kz.utelkhan.bookexchange.jpa.userDetails.StatusRepository;
import kz.utelkhan.bookexchange.jpa.userDetails.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@org.aspectj.lang.annotation.Aspect
@Component
@Slf4j
public class Aspect {
    StatusRepository statusRepository;
    RoleRepository roleRepository;
    UserRepository userRepository;

    @Autowired
    public Aspect(StatusRepository statusRepository, RoleRepository roleRepository, UserRepository userRepository) {
        this.statusRepository = statusRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Pointcut("execution(* kz.utelkhan.bookexchange.controller.authentication.AuthenticationController.registration())")
    public void selectMethodsRegistration() {}

    @Before("selectMethodsRegistration()")
    public void beforeAdvice() {
        log.info("Registration Initializing"+new Date().toString());
    }

    @After("selectMethodsRegistration()")
    public void afterAdvice() {
        log.info("Registration Success"+new Date().toString());
    }

    @AfterReturning(pointcut = "selectMethodsRegistration()")
    public void afterReturning(){}

    @Pointcut("execution(* kz.utelkhan.bookexchange.controller.authentication.AuthenticationController.login())")
    public void selectMethodslogin(){}

    @AfterReturning(pointcut = "selectMethodslogin()")
    public void afterReturningAdvice() {
        log.info("Success-kz.utelkhan.bookexchange.controller.authentication.AuthenticationController.login())");
    }

    @AfterThrowing(pointcut = "selectMethodslogin()", throwing = "e")
    public void anException(Exception e) {
        log.error("We have an exception here: " + e.toString());
    }
}
