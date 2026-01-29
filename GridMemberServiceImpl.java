package com.lyjsoft.nep.service.impl;


import com.lyjsoft.nep.entity.GridMember;
import com.lyjsoft.nep.service.GridMemberService;
import com.lyjsoft.nep.util.FileUtil;

import java.util.List;

public class GridMemberServiceImpl implements GridMemberService {
    @Override
    public GridMember login(String loginCode, String password) {
        String ProPaht = System.getProperty("user.dir") + "/src/main/resources/NepDatas/ObjectData/";

        List<GridMember> glist = (List<GridMember>) FileUtil.readObject(ProPaht+"gridmember.txt");
        for(GridMember gm : glist){
            if(gm.getLoginCode().equals(loginCode) && gm.getPassword().equals(password)){
                return gm;
            }
        }
        return null;
    }
    public static boolean updatePassword(String loginCode, String newPassword, String rePassword) {
        String ProPaht = System.getProperty("user.dir") + "/src/main/resources/NepDatas/ObjectData/";
        List<GridMember> glist = (List<GridMember>) FileUtil.readObject(ProPaht+"gridmember.txt");
        for(GridMember gm : glist) {
            if(gm.getLoginCode().equals(loginCode)) {
                gm.setPassword(newPassword);
                FileUtil.writeObject(ProPaht+"gridmember.txt", glist);
                return true;
            }
        }
        return false;
    }
}
