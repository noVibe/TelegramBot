package com.telegram.telegrambot.aspects;

import com.telegram.telegrambot.service.GameService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.logging.Logger;

@Component
@Aspect
public class GameServiceAspect {
    Logger logger = Logger.getLogger(GameService.class.getName());

    @Pointcut("execution(public String com.telegram.telegrambot.service.GameService.makeAnswer(..))")
    public void handleMessagePoint() {
    }

    @Around("handleMessagePoint()")
    public Object handleMessageAspect(ProceedingJoinPoint joinPoint) {
        Update update;
        String result;
        update = (Update) joinPoint.getArgs()[0];
        String message = update.getMessage().getText();
        String username = update.getMessage().getChat().getUserName();
        String firstName = update.getMessage().getChat().getFirstName();
        String lastName = update.getMessage().getChat().getLastName();
        logger.info(String.format("%s (%s %s) sends: %s", username, firstName, lastName, message));
        try {
            result = (String) joinPoint.proceed();
        } catch (Throwable e) {
            result = "Invalid input! Try /help if you stuck";
            logger.info(e.getMessage());
        }
        logger.info(String.format("Answer for %s:\n %s", username, result));
        return result;
    }

}
