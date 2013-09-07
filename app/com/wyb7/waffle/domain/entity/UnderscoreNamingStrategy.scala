package com.wyb7.waffle.domain.entity

import org.hibernate.cfg.ImprovedNamingStrategy
import org.hibernate.AssertionFailure
import org.hibernate.internal.util.StringHelper

/**
 * Author: Wang Yibin
 * Date: 11-6-9
 * Time: 上午11:17
 */

class UnderscoreNamingStrategy extends ImprovedNamingStrategy {
    override def foreignKeyColumnName(propertyName: String, propertyEntityName: String,
                  propertyTableName: String, referencedColumnName: String) = {
        var header: String = if (propertyName != null) StringHelper.unqualify(propertyName + "_" + referencedColumnName)
                            else propertyTableName
        if (header == null) throw new AssertionFailure("NamingStrategy not properly filled")
        columnName(header)
    }

    override def propertyToColumnName(propertyName: String) = {
        val pName = if (propertyName.startsWith("_")) propertyName.substring(1) else propertyName
        super.propertyToColumnName(pName)
    }

//    override def logicalColumnName(columnName: String, propertyName: String): String = {
//        println(s">>>> logic name: $columnName -> $propertyName")
//        val pName = if (propertyName.startsWith("_")) propertyName.substring(1) else propertyName
//        return if (StringHelper.isNotEmpty(columnName)) columnName else StringHelper.unqualify(pName)
////        super.logicalColumnName(columnName, propertyName)
//    }
}