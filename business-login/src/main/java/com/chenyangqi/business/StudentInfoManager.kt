package com.chenyangqi.business

import com.chenyangqi.base.common.IGetStudentInfo
import com.chenyangqi.base.common.bean.StudentInfo
import com.chenyangqi.router.annotations.ServiceLoader

@ServiceLoader(
    interfaces = [IGetStudentInfo::class],
    singleton = true,
    key = "Login_StudentInfoManager"
)
class StudentInfoManager :IGetStudentInfo {
    override fun getStudentInfo(): StudentInfo {
        return StudentInfo("王五",97)
    }
}