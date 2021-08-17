package com.chenyangqi.base.common.bean

/**
 * 用户信息实体类
 */
data class UserInfo(
    val age: Int,
    val email: String,
    val id: String,
    val username: String
){
    override fun toString(): String {
        return "UserInfo(age=$age, email='$email', id='$id', username='$username')"
    }
}