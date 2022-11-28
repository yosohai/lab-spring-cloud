package org.lab.base.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.scheduling.support.CronSequenceGenerator;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;

public class CrontabUtil {

    /**
     * @desc 计算corn执行的时间间隔
     * @date 2021/08/12 13:40
     */
    public static long calculateCronTimeInterval(String cron, Date date) {
        if (StringUtils.isBlank(cron)) {
            throw new IllegalArgumentException("参数不能为空");
        }

        String[] cronArr = cron.split("\\s+");
        if (cronArr.length == 7) {
            cron = String.join(" ", Arrays.copyOfRange(cronArr, 0, cronArr.length - 1));
        }
        if (date == null) {
            date = new Date();
        }
        CronSequenceGenerator cronSequenceGenerator = new CronSequenceGenerator(cron);
        Date nextDate = cronSequenceGenerator.next(date);
        Date secondDate = cronSequenceGenerator.next(nextDate);
        //间隔毫秒数
        long timeInerval = secondDate.getTime() - nextDate.getTime();
        //间隔秒数
        return timeInerval / 1000;
    }


    /**
     * @desc 计算corn执行的时间间隔
     * @date 2021/08/12 13:40
     */
    public static long calculateCronTimeInterval2(String cron, LocalDateTime date) {
        if (StringUtils.isBlank(cron)) {
            throw new IllegalArgumentException("参数不能为空");
        }

        String[] cronArr = cron.split("\\s+");
        if (cronArr.length == 7) {
            cron = String.join(" ", Arrays.copyOfRange(cronArr, 0, cronArr.length - 1));
        }
        if (date == null) {
            date = LocalDateTime.now();
        }
        CronExpression cronExpression = CronExpression.parse(cron);
        LocalDateTime nextDate = cronExpression.next(date);
        LocalDateTime secondDate = cronExpression.next(nextDate);
        //间隔秒数
        return secondDate.getSecond() - nextDate.getSecond();
    }
}
