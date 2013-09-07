package com.wyb7.waffle.commons.value

/**
 * User: abin
 * Date: 13-9-7
 * Time: 下午1:56
 */
case class Pagination[T](total:Long, start:Long, size:Long, data:List[T],
                message:String = "", success:Boolean = true) {
    def this(data:List[T]) = this(data.size, 0, data.size, data)
}

object Pagination {
    def createWithAllData[T](data:List[T]) = Pagination(data.size, 0, data.size, data)
}