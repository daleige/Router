package com.chenyangqi.router.processor.bean

/**
 * 存储ServiceLoader信息
 */
data class ServiceLoaderBean(
    var interfaceName: String,
    var realPath: String,
    var key: String,
    var singleton: Boolean
){
    override fun toString(): String {
        return "interfaceName=$interfaceName,realPath=$realPath,key=$key,singleton=$singleton"
    }
}