package com.telegram.telegrambot.aspects;

import com.telegram.telegrambot.service.GameService;
import com.telegram.telegrambot.service.Messages;
import com.telegram.telegrambot.service.UpdateService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;


@Component
@Aspect
public class GameServiceAspect {
    private final Logger logger = LoggerFactory.getLogger(GameService.class.getName());
    private final UpdateService updateService;

    public GameServiceAspect(UpdateService updateService) {
        this.updateService = updateService;
    }

    @Pointcut("execution(public String com.telegram.telegrambot.service.GameService.makeAnswer(..))")
    public void handleMessagePoint() {
    }

    @Around("handleMessagePoint()")
    public Object handleMessageAspect(ProceedingJoinPoint joinPoint) {
        String result;
        Update update = (Update) joinPoint.getArgs()[0];
        String message = updateService.getTextMessage(update);
        String username = updateService.getUsername(update);
        String firstName = updateService.getFirstname(update);
        String lastName = updateService.getLastname(update);
        logger.info("{} ({} {}) sends: {}", username, firstName, lastName, message);
        try {
            result = (String) joinPoint.proceed();
        } catch (Throwable e) {
            result = Messages.INVALID_INPUT.message;
            logger.info(e.getMessage());
        }
        logger.info("Answer for {}:\n {}", username, result);
        return result;
    }

}
