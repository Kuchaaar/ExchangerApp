package com.exchanger.ExchangerApp.currency.domain;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.stereotype.Component;

@Component
public class CurrencySchedule {
    public CurrencySchedule() throws SchedulerException {
//        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
//        JobDetail job = JobBuilder.newJob(Schedule.class)
//                .withIdentity("myJob", "group1")
//                .build();
//        Trigger trigger = TriggerBuilder.newTrigger()
//                .withIdentity("myTrigger", "group1")
//                .withSchedule(CronScheduleBuilder.cronSchedule("0 * * ? * MON-FRI"))
//                .build();
//        Trigger trigger = TriggerBuilder.newTrigger()
//                .withIdentity("myTrigger", "group1")
//                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
//                        .withIntervalInSeconds(60)
//                        .repeatForever())
//                .build();
//        scheduler.scheduleJob(job, trigger);
//        scheduler.start();
    }
}

