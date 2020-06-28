package w3Campus.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Aspect
@Configuration
public class AllAspect {
    /*
       //What kind of method calls I would intercept
       //execution(* PACKAGE.*.*(..))
       //Weaving & Weaver
       @Before( "execution(* com.aop.example.aspect.service.HelloService.getMessage(..))" )
       public void before() {
           System.out.println(" Before");
       }
       @After( "execution(* com.aop.example.aspect.service.HelloService.getMessage(..))" )
       public void after() {
           System.out.println(" After");
       }
       @AfterReturning( "execution(* com.aop.example.aspect.service.HelloService.getMessage(..))" )
       public void afterReturning() {
           //when method execute successfully this method would execute
           System.out.println(" After Returning");
       }
       @AfterThrowing( "execution(* com.aop.example.aspect.service.HelloService.getMessage(..))" )
       public void afterThrowing() {
           //without error this method would not be executed
           System.out.println(" After Throwing");
       }
   */


/*    //first example
    @Around( "execution(* com.aop.example.aspect.service.HelloService.getMessage(..))" )
    public Object around(ProceedingJoinPoint joinPoint) {
        System.out.println("Something else !!");
        return "Something else";
    }*/

    //secound example
    @Around( "execution(* w3Campus.asset.student.service.StudentService.persist(..))" )
    public Object around(ProceedingJoinPoint joinPoint) {
        System.out.println("Before");
        Object result = null;
        try {
            System.out.println("parameter "+ Arrays.toString(joinPoint.getArgs()));
            result = joinPoint.proceed();
            System.out.println("After");
        } catch ( Throwable throwable ) {
            throwable.printStackTrace();
        }
        return result;
    }

}
