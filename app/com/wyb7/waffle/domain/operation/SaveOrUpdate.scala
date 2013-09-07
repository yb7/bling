package com.wyb7.waffle.domain.operation

/**
 * Author: wangyibin
 * Date: 11-2-14
 * Time: 下午4:08
 */
trait SaveOrUpdate extends HibernateEntityHelper{
    def saveOrUpdate(){
        checkFieldsUnique
        checkCombinedFieldsUnique
        session.saveOrUpdate(this)
        session.flush()
    }
}
