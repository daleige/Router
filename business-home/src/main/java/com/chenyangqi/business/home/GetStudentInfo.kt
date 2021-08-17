package com.chenyangqi.business.home

import com.chenyangqi.base.common.IGetStudentInfo
import com.chenyangqi.base.common.IGetUserInfo
import com.chenyangqi.base.common.bean.StudentInfo
import com.chenyangqi.router.annotations.ServiceLoader

@ServiceLoader(
    interfaces = [IGetStudentInfo::class],
    singleton = true,
    key = "Home_GetStudentInfo"
)
class GetStudentInfo : IGetStudentInfo {

    override fun getStudentInfo(): StudentInfo {
        return StudentInfo("李四", 99)
    }
}