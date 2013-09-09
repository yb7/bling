package com.wyb7.waffle.commons.value

/**
 * generic result type, with message and data.
 * The toString method produce json string.
 *
 * The field data should be a case class.
 * Otherwise the toString method cannot produce the json representation correct
 */
case class GenericActionResult (success:Boolean, message:String, data:Any)

object GenericActionResult {
    implicit def genericActionResultToBoolean(result: GenericActionResult): Boolean = if (result == null) false else result.success
    
    def successResult(message:String = "", data:Any = null) = new GenericActionResult(true, message, data)
    def successResult(data:Any) = new GenericActionResult(true, "", data)
    def failureResult(message:String = "", data:Any = null) = new GenericActionResult(false, message, data)

}
