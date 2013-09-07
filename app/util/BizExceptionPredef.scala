package util

import com.wyb7.waffle.commons.exception.BizRuntimeException

/**
 * Author: Wang Yibin
 * Date: 12-6-1
 * Time: 下午4:41
 */

object BizExceptionPredef {
    implicit def messageToBizRuntimeException(msg: String) = new BizRuntimeException(msg)
}
