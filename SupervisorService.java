package com.lyjsoft.nep.service;

import com.lyjsoft.nep.entity.Supervisor;

public interface SupervisorService {
    /**
     * 公众监督员登录功能
     * @return
     */
    public boolean login(String loginCode,String password);

    /**
     * 公众监督员注册功能
     * @param supervisor
     * @return
     */
    public boolean register(Supervisor supervisor);

    /**
     * 增加监督员积分
     * @param loginCode 监督员登录账号
     * @return 是否成功
     */
    public boolean increasePoints(String loginCode);
    public static boolean updatePassword(String loginCode,String password,String repassword){
        return true;
    }



}
