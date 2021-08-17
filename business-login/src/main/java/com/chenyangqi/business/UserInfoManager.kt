package com.chenyangqi.business

import com.chenyangqi.base.common.IGetUserInfo
import com.chenyangqi.base.common.bean.UserInfo
import com.chenyangqi.router.annotations.ServiceLoader

@ServiceLoader(
    interfaces = [IGetUserInfo::class],
    singleton = true,
    key = "Login_UserInfoManager"
)
class UserInfoManager : IGetUserInfo {

    override fun getUserInfo(): UserInfo {
        //模拟提供给其他模块所需要的数据
        return UserInfo(18, "766790183@gmail.com", "8888888888", "张三")
    }

}