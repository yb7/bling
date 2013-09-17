package controllers.eis.foundation

import org.springframework.stereotype.Controller
import org.quartz.impl.matchers.GroupMatcher
import collection.JavaConverters._
import org.quartz.impl.matchers.StringMatcher.StringOperatorName
import com.wyb7.waffle.commons.value.GenericActionResult
import com.wyb7.waffle.commons.value.GenericActionResult._
import org.springframework.transaction.annotation.Transactional
import logistics.schedule.dto.{TriggerSummary, TriggerCreateDto}

import org.springframework.web.bind.annotation._

import logistics.core.util.BizExceptionPredef._
import logistics.schedule.dto.SchedulerKeyWrapper._
import org.springframework.beans.factory.annotation.Autowired
import org.quartz.{Scheduler, JobKey}

/**
 * Author: Wang Yibin
 * Date: 11-6-21
 * Time: 下午8:05
 */

@Controller
@RequestMapping(value = Array("/schedule"))
class JobController {
    @Autowired
    private var scheduler: Scheduler = _

    implicit def strToJobKey(str: String) = JobKey.jobKey(str.split("""\.""")(1), str.split("""\.""")(0))

    @Transactional
    @RequestMapping(value = Array("/trigger"), method = Array(RequestMethod.GET))
    @ResponseBody
    def query() : GenericActionResult = {
        val triggerSummaries = scheduler.getTriggerGroupNames.asScala
                .map(group => scheduler.getTriggerKeys(GroupMatcher.triggerGroupEquals(group)).asScala).flatten
                .map(scheduler.getTrigger(_))
                .map(TriggerSummary(_, scheduler))
        return successResult("", triggerSummaries)
    }


    class JobKeyGroupMatcher(compareTo: String, compareWith: StringOperatorName) extends GroupMatcher[JobKey](compareTo, compareWith)

    @Transactional
	@RequestMapping(value = Array("/trigger"), method = Array(RequestMethod.POST))
    @ResponseBody
    def create(@RequestBody dto: TriggerCreateDto): GenericActionResult = {

        val trigger = dto.createTrigger
        if (!scheduler.checkExists(trigger.getKey)) {
            scheduler.scheduleJob(trigger)
        } else {
            throw "%s已存在".format(trigger.getKey)
        }
        successResult()
    }

    @Transactional()
	@RequestMapping(value = Array("/trigger/{triggerKey}"), method = Array(RequestMethod.DELETE))
    @ResponseBody
    def delete(@PathVariable("triggerKey") triggerKey: String): GenericActionResult = {
        return if (scheduler.unscheduleJob(triggerKey.triggerKey)) successResult() else failureResult("删除失败")
    }


    @Transactional
	@RequestMapping(value = Array("/job/{jobKey}"), method = Array(RequestMethod.POST))
    @ResponseBody
    def triggerJob(@PathVariable("jobKey") jobKey: String,
                   @RequestBody action: JobAction): GenericActionResult = {
        action.action match {
            case "trigger" => scheduler.triggerJob(jobKey.jobKey)
        }
        successResult()
    }
}

case class JobAction(action: String)