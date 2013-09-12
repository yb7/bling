package com.wyb7.waffle.commons.xls

import org.apache.poi.hssf.usermodel.HSSFCell
import com.wyb7.waffle.commons.exception.BizRuntimeException
import com.wyb7.waffle.commons.xls.HSSFCellHelper._
import org.apache.commons.lang3.StringUtils
import org.joda.money.BigMoney

/**
 * User: houhou
 * Date: 12-8-28
 * Time: 下午5:00
 */

object MoneyPredef {
    def hssfCellToBigMoney(currency: HSSFCell, amount: HSSFCell)(failure: String => BizRuntimeException = null): BigMoney = {
        def strOf(cell: HSSFCell) = StringUtils.trimToEmpty(hssfCellToStr(cell))
        val unitPrice: String = StringUtils.upperCase(strOf(currency)) + " " + strOf(amount)
        if (StringUtils.isNotBlank(unitPrice)) {
            try {
                BigMoney.parse(unitPrice)
            } catch {
                case e: Exception => if (failure == null) {
                    throw new BizRuntimeException("单价不规范: [%s]".format(unitPrice))
                } else {
                    throw failure(unitPrice)
                }
            }
        } else {
            null
        }
    }
}
