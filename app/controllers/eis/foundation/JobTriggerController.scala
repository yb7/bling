package controllers.eis.foundation

import org.springframework.stereotype.Component
import org.quartz.impl.matchers.GroupMatcher
import collection.JavaConverters._
import com.wyb7.waffle.commons.value.GenericActionResult._


import org.springframework.beans.factory.annotation.Autowired
import org.quartz.{TriggerKey, Trigger, Scheduler, JobKey}
import play.api.mvc.{Action, Controller}
import controllers.support.JacksonJsonSupport
import java.text.SimpleDateFormat

/**
 * Author: Wang Yibin
 * Date: 11-6-21
 * Time: 下午8:05
 */

@Component
class JobTriggerController extends Controller with JacksonJsonSupport {
    private var scheduler: Scheduler = _
    @Autowired
    def setScheduler(scheduler: Scheduler) {
        this.scheduler = scheduler
    }

    def query() = Action {
        val triggerSummaries = scheduler.getTriggerGroupNames.asScala
                .map(group => scheduler.getTriggerKeys(GroupMatcher.triggerGroupEquals(group)).asScala).flatten
                .map(scheduler.getTrigger(_))
                .map(TriggerSummary(_, scheduler))
        OkJson(successResult("", triggerSummaries))
    }


    def delete(triggerKey: String) = Action {
        if (scheduler.unscheduleJob(triggerKey.triggerKey))
            OkJson(successResult())
        else
            OkJson(failureResult(s"删除${triggerKey}失败"))
    }


    def triggerJob(triggerKey: String) = Action { implicit request =>

        val trigger = scheduler.getTrigger(triggerKey.triggerKey)
        val jobData = trigger.getJobDataMap
        scheduler.triggerJob(trigger.getJobKey, jobData)
        Ok
    }

    implicit class SchedulerKeyWrapper(str: String) {
        def splitByDot = {
            val index = str.indexOf(".")
            (str.substring(0, index), str.substring(index + 1, str.length))
        }

        def triggerKey: TriggerKey = {
            val (group, name) = splitByDot
            new TriggerKey(name, group)
        }

        def jobKey: JobKey = {
            val (group, name) = splitByDot
            new JobKey(name, group)
        }
    }
}

case class TriggerSummary(triggerKey: String, description: String, previousFireTime: String,
                          nextFireTime: String, state: String, jobKey: String)
object TriggerSummary {
    val df = new SimpleDateFormat("yy-MM-dd HH:mm:ss")
    private def format(date: java.util.Date) = {
        if (date == null) "" else df.format(date)
    }

    def apply(trigger: Trigger, scheduler: Scheduler):TriggerSummary = {
        new TriggerSummary(trigger.getKey.toString, trigger.getDescription, format(trigger.getPreviousFireTime),
            format(trigger.getNextFireTime), scheduler.getTriggerState(trigger.getKey).toString,
            trigger.getJobKey.toString)
    }
}

