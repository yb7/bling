package com.wyb7.waffle.commons.value

/**
 * User: abin
 * Date: 13-9-7
 * Time: 下午1:56
 */
case class Pagination[T](total:Long, start:Int, size:Int, data:List[T],
                         message:String = "", success:Boolean = true) {
}

object Pagination {
    def createWithAllData[T](data:List[T]) = Pagination(data.size, 0, data.size, data)
    def create[T](total: Long, start: Int, data: List[T]) = Pagination(total, 0, data.size, data)
}