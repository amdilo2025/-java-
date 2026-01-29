package com.lyjsoft.nep.service;


import com.lyjsoft.nep.entity.GridMember;

public interface GridMemberService {
    /**
     * 网格员登录
     * @param loginCode
     * @param password
     * @return
     */
    public GridMember login(String loginCode, String password);
    public static boolean updatePassword(String loginCode, String newPassword, String rePassword) {
        return true;
    }
}
