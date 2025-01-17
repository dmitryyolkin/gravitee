package com.gravitee.example.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Аспект логирования фактов входа в метод и выхода из метода (web-метод) REST-контроллера.
 * Если логи настоящего аспекта связывать напрямую по TraceID, а не по временным отметкам -
 *  то такие логи представляют путь, которым проходит сигнал (сообщение, Заявка) через Сервисы.
  */
@Aspect
@Component
public class EntryExitLoggingAspect {

    /** Логгер аспекта.
     *      Так.как аспект в run-time явзяется демоном - то, на этот логгер не влияют исключительные ситуации в
     *      приложении (основной процесс).
     */
    private static final Logger logger = LoggerFactory.getLogger(EntryExitLoggingAspect.class);

    /**
     * Непосредственно Асект, "обрамляющий" (контроль до вызова метода, и выход из метода) каждый метод контроллера.
     * В логах остаются отметки:
     *  - факт входа в метод
     *  - факт выхода из метода
     *  - время, потраченное на выполнение метода
     */
    @Around("execution(* com.gravitee.example.controller.*.*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();                    // фиксируем время начала
        // фиксируем вход в метод
        logger.info("Entering method {}", joinPoint.getSignature().getName());
        // выполнение целевого метода
        Object proceed = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - start;    // фиксируем время выполнения
        // фиксируем время выполнения целевого метода
        logger.info("Method {} executed in {} ms", joinPoint.getSignature().getName(), executionTime);
        return proceed;
    }
}
