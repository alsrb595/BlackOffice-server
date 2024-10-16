package org.example.blackoffice.config;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class SeatReservationScheduler {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private Scheduler scheduler;

    public void scheduleSeatReservationJob(Long seatId, LocalDateTime reservationEndTime) throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(SeatReservationJob.class)
                .withIdentity("seatReservationJob", "seatGroup")
                .usingJobData("seatId", seatId)
                .usingJobData("reservationEndTime", reservationEndTime.format(formatter))
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("seatReservationTrigger", "seatGroup")
                .startAt(java.sql.Timestamp.valueOf(reservationEndTime))
                .build();

        if (!scheduler.isStarted()) {
            scheduler.start();  // 이미 시작된 경우 중복으로 시작하지 않도록 처리
        }
        scheduler.scheduleJob(jobDetail, trigger);
    }



}
