package com.wyb7.waffle.commons.util

import org.apache.commons.lang3.StringUtils
import java.text.{NumberFormat, DecimalFormat}

/**
 * User: houhou
 * Date: 12-6-7
 * Time: 上午6:01
 */

object CommonPredef {
    implicit def strToEmptyRelatedStringWrapper(str: String) = new EmptyRelatedStringWrapper(str)

    implicit def longToCommonLongWrapper(l: Long) = new CommonLongWrapper(l)

    def aboveZeroOp(l: Long) = if (l > 0) Some(l) else None
    def notNullOp[T](t: T):Option[T] = if (t != null) Some(t) else None
    def falseToNone(b: Boolean) = if (b) Some(b) else None
}

class EmptyRelatedStringWrapper(str: String) {
    def nullToEmpty: String = if (str == null) "" else str

    def notEmptyOp: Option[String] = if (StringUtils.isNotBlank(str)) Some(str) else None
}

class CommonLongWrapper(l: Long) {
    def formatWithPrefix0(width: Int): String = {
        val decFormatter: DecimalFormat = NumberFormat.getInstance.asInstanceOf[DecimalFormat]
        decFormatter.applyPattern("0" * width)
        decFormatter.setMaximumIntegerDigits(width)
        decFormatter.setMaximumIntegerDigits(width)
        decFormatter.format(l)
    }
    def aboveZeroOp = if (l > 0) Some(l) else None
}