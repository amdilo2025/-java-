package com.lyjsoft.nep.service.impl;

import com.lyjsoft.nep.entity.Admin;
import com.lyjsoft.nep.service.AdminService;
import com.lyjsoft.nep.util.FileUtil;

import java.util.List;

public class AdminServiceImpl implements AdminService {
    @Override
    public boolean login(String loginCode, String password) {
        // TODO Auto-generated method stub
        String ProPaht = System.getProperty("user.dir") + "/src/main/resources/NepDatas/ObjectData/";
        List<Admin> alist =(List<Admin>) FileUtil.readObject(ProPaht+"admin.txt");
        for(Admin a : alist){
            if(a.getLoginCode().equals(loginCode) && a.getPassword().equals(password)){
                return true;
            }
        }
        return false;
    }
    public boolean updatePassword(String loginCode, String newPassword,String repassword) {
        String ProPaht = System.getProperty("user.dir") + "/src/main/resources/NepDatas/ObjectData/";
        List<Admin> alist = (List<Admin>) FileUtil.readObject(ProPaht+"admin.txt");
        for(Admin a : alist) {
            if(a.getLoginCode().equals(loginCode)) {
                a.setPassword(newPassword);
                FileUtil.writeObject(ProPaht+"admin.txt", alist);
                return true;
            }
        }
        return false;
    }

}
