package com.lyjsoft.nep.service.impl;

import com.lyjsoft.nep.controller.NepsFeedbackViewController;
import com.lyjsoft.nep.controller.NepsSelectAqiViewController;
import com.lyjsoft.nep.entity.Supervisor;
import com.lyjsoft.nep.po.Admin;
import com.lyjsoft.nep.service.SupervisorService;
import com.lyjsoft.nep.util.FileUtil;

import java.util.List;

public class SupervisorServiceImpl implements SupervisorService {
    private static Object rePassword;
    @Override
    public boolean login(String loginCode,String password) {
        // TODO Auto-generated method stub
        String ProPaht = System.getProperty("user.dir") + "/src/main/resources/NepDatas/ObjectData/";
        List<Supervisor> slist =(List<Supervisor>) FileUtil.readObject(ProPaht+"supervisor.txt");
        boolean isLogin = false;
        for(Supervisor s:slist){
            if(s.getLoginCode().equals(loginCode) && s.getPassword().equals(password)){
                NepsSelectAqiViewController.supervisor = s;	//将当前登录成功用户身份共享给下一个界面
                NepsFeedbackViewController.supervisor = s;	//将当前登录成功用户身份共享给下一个界面
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean register(Supervisor supervisor) {
        // TODO Auto-generated method stub
        String ProPaht = System.getProperty("user.dir") + "/src/main/resources/NepDatas/ObjectData/";
        List<Supervisor> slist = (List<Supervisor>)FileUtil.readObject(ProPaht+"supervisor.txt");
        System.out.println(slist.size());
        for(Supervisor s:slist){
            if(s.getLoginCode().equals(supervisor.getLoginCode())){
                return false;
            }
        }
        slist.add(supervisor);
        FileUtil.writeObject(ProPaht+"supervisor.txt", slist);
        return true;
    }

    @Override
    public boolean increasePoints(String loginCode) {
        String ProPaht = System.getProperty("user.dir") + "/src/main/resources/NepDatas/ObjectData/";
        List<Supervisor> slist = (List<Supervisor>) FileUtil.readObject(ProPaht+"supervisor.txt");
        if (slist == null) return false;
        for (Supervisor s : slist) {
            if (s.getLoginCode().equals(loginCode)) {
                // 如果积分为null，初始化为0
                if (s.getPoints() == null) {
                    s.setPoints(0);
                }
                s.setPoints(s.getPoints() + 1);
                FileUtil.writeObject(ProPaht+"supervisor.txt", slist);
                return true;
            }
        }
        return false;
    }
    public static boolean updatePassword(String loginCode, String password, String rePassword) {
        String ProPaht = System.getProperty("user.dir") + "/src/main/resources/NepDatas/ObjectData/";
        List<Supervisor> slist = (List<Supervisor>)FileUtil.readObject(ProPaht+"supervisor.txt");

        for(Supervisor s : slist) {
            if(s.getLoginCode().equals(loginCode)) {
                s.setPassword(rePassword);
                FileUtil.writeObject(ProPaht+"supervisor.txt", slist);
                return true;
            }
        }
        return false;
    }



}
