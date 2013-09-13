package com.wyb7.waffle.commons.xls

import org.apache.commons.lang3.StringUtils
import org.apache.poi.hssf.usermodel.{HSSFRow, HSSFCell, HSSFSheet}
import collection.JavaConverters._
import com.wyb7.waffle.commons.exception.BizRuntimeException
import com.wyb7.waffle.commons.util.Logger

/**
 * Author: Wang Yibin
 * Date: 11-9-15
 * Time: 下午5:22
 */
class ErrorMessage {
    private var _messages = List.empty[String]
    def addMessage(msg: String) {
        _messages :+= msg
    }
    def messages = _messages
    def hasError = !_messages.isEmpty
}

object ErrorMessage {
    def NoError = new ErrorMessage
}
class ReadXlsToMap(sheet: HSSFSheet, columns: Set[String]) {
    private val logger = Logger(classOf[ReadXlsToMap])
    
    private val HeadRowNum = 0

    private var headMap = Map.empty[String, Int]
    private var currentRow: HSSFRow = null
    var headRow: HSSFRow = null

    def read[T](rowMapper: RowMapper[T]): Either[ErrorMessage, List[T]] = {
        implicit val errorMessage = new ErrorMessage

        logger.debug("has %d rows in sheet %s".format(sheet.getPhysicalNumberOfRows, sheet.getSheetName))
        if (isEmpty) {
            logger.debug("sheet is empty")
            return Right(List.empty[T])
        }
        readHead
        verifyHead
        val list = for (i: Int <- (HeadRowNum + 1) to sheet.getLastRowNum) yield {
            currentRow = sheet.getRow(i)
            val values = parseEachRowToMap()
            try {
                rowMapper.map(values, errorMessage, i)
            } catch {
                case e: Throwable => logger.error("Error in rowMapper.map", e)
                    errorMessage.addMessage(e.toString)
                    null.asInstanceOf[T]
            }
        }
        if (errorMessage.hasError) {
            Left(errorMessage)
        } else {
            Right(list.toList)
        }
    }
    def read(rch: RowCallbackHandler) {
        read(new RowMapper[Unit] {
            def map(values: Map[String, HSSFCell], errorMessage: ErrorMessage, rowCount: Int) {
                rch.processRow(values, errorMessage, rowCount)
                return
            }
        })
    }
    private def isEmpty  = sheet.getLastRowNum < 1

    private def readHead {
        headRow = sheet.getRow(HeadRowNum)
        headMap = headRow.iterator().asScala.toIterable.asInstanceOf[Iterable[HSSFCell]].map(
                            cell => (StringUtils.trim(cell.getStringCellValue)->cell.getColumnIndex)
                        ).toMap
        logger.debug("%d columns in head".format(headMap.size))
    }

    private def verifyHead(implicit errorMessage: ErrorMessage) {
        val missingColumns = columns -- headMap.keySet
        if (!missingColumns.isEmpty) {
            errorMessage.addMessage("列 " + missingColumns.mkString("、")  + " 无法找到")
        }
    }

    def parseEachRowToMap(): Map[String, HSSFCell] = {
        val map = for ((head, index) <- headMap) yield {
            (head -> currentRow.getCell(index))
        }
        return map.toMap
    }
}
trait RowMapper[T] {
    def map(values: Map[String, HSSFCell], errorMessage: ErrorMessage, rowCount: Int): T
}

trait RowCallbackHandler {
    def processRow(cells: Map[String, HSSFCell], errorMessage: ErrorMessage, rowCount: Int): Unit
}