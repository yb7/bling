package com.wyb7.waffle.commons.util

/**
 * Author: Wang Yibin
 * Date: 11-8-7
 * Time: 下午10:18
 */

object SafeGetter {
//    def safeId[T <: {def id: Long}](obj: T): Long = if (obj == null) 0 else obj.id
//
//    def safeShortCode[T <: {def shortCode: String}](obj: T): String = if (obj == null) "" else obj.shortCode
//
//    def safeName[T <: {def name: String}](obj: T): String = if (obj == null) "" else obj.name
//
//    def safeChnName[T <: {def chnName: String}](obj: T): String = if (obj == null) "" else obj.chnName
//
//    def safeEngName[T <: {def engName: String}](obj: T): String = if (obj == null) "" else obj.engName
//
//    def safeMaterialNumber[T <: {def materialNumber: String}](obj: T): String = if (obj == null) "" else obj.materialNumber
//
//    def safeCargoName[T <: {def cargoName: String}](obj: T): String = if (obj == null) "" else obj.cargoName
//
//    def safeToString[T <: {def toString: String}](obj: T): String = if (obj == null) "" else obj.toString

    def nullSafe[U](f: => U, default:U = null.asInstanceOf[U]):U = {
        try {
            f
        } catch {
            case ex: NullPointerException => default
        }
    }
    def nullIsZero(value: BigDecimal) = if (value == null) BigDecimal("0") else value
}