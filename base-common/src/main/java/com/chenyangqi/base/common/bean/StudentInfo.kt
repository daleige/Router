package com.chenyangqi.base.common.bean

data class StudentInfo(
    var name: String,
    var age: Int
){
    override fun toString(): String {
        return "StudentInfo(name='$name', age=$age)"
    }
}