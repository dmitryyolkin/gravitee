package com.gravitee.example.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/** Аспект логирования фактов входа в метод и выхода из метода (web-метод) REST-контроллера,
 *      а так же значения входных аргументов, результат, отдаваемый методом, и исключительные ситуации.
 *  Если логи настоящего аспекта связывать напрямую по TraceID, а не по временным отметкам -
 *      то такие логи представляют путь, которым проходит сигнал (сообщение, Заявка) через Сервисы. */
@Aspect
@Component
public class DetailsLoggingAspect {

    /** Логгер аспекта.
     *      Так.как аспект в run-time явзяется демоном - то, на этот логгер не влияют исключительные ситуации в
     *      приложении (основной процесс). */
    private static final Logger logger = LoggerFactory.getLogger(EntryExitLoggingAspect.class);

    /**
     * Фиксация факта входа в метод, дополнительно сохраняются в логе значения входящих аргументов.
      * @param joinPoint
     */
    @Before("execution(* com.gravitee.example.controller.*.*(..))")
    public void logMethodEntry(JoinPoint joinPoint) {
        logger.info("Entering method {} with arguments {}",
                joinPoint.getSignature().getName(), joinPoint.getArgs().toString());
    }

    /**
     * Фиксация выхода из метода (нормальный выход с результатом), дополнительно сохраняется в логе результат
     * выполнения целевого метода.
      * @param joinPoint
     * @param result
     */
    @AfterReturning(pointcut = "execution(* com.gravitee.example.controller.*.*(..))", returning = "result")
    public void logMethodDetailsExit(JoinPoint joinPoint, Object result) {
        logger.info("Exiting method {} with result {}", joinPoint.getSignature().getName(), result);
    }

    /**
     * Фиксация выхода из метода с ошибкой (исключительная ситуация), дополнительно сохраняется в логе сообщение об
     * ошибке.
      * @param joinPoint
     * @param exception
     */
    @AfterThrowing(pointcut = "execution(* com.gravitee.example.controller.*.*(..))", throwing = "exception")
    public void logMethodException(JoinPoint joinPoint, Throwable exception) {
        logger.error("Exception in method {} with message {}",
                joinPoint.getSignature().getName(), exception.getMessage());
    }
}
