package com.lyjsoft.nep.service.impl;


import com.lyjsoft.nep.entity.AqiFeedback;
import com.lyjsoft.nep.entity.GridMember;
import com.lyjsoft.nep.entity.Supervisor;
import com.lyjsoft.nep.service.AqiFeedbackService;
import com.lyjsoft.nep.service.SupervisorService;
import com.lyjsoft.nep.util.FileUtil;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class AqiFeedbackServiceImpl implements AqiFeedbackService {
    private SupervisorService supervisorService;

    public AqiFeedbackServiceImpl() {
        this.supervisorService = new SupervisorServiceImpl();
    }

    @Override
    public void saveFeedBack(AqiFeedback afb) {
        String ProPaht = System.getProperty("user.dir") + "/src/main/resources/NepDatas/ObjectData/";

        List<AqiFeedback> afList = (List<AqiFeedback>) FileUtil.readObject(ProPaht+"aqifeedback.txt");
        afb.setAfId(afList.size()+1);
        afList.add(afb);
        FileUtil.writeObject(ProPaht+"aqifeedback.txt", afList);

        supervisorService.increasePoints(afb.getAfName());
    }

    @Override
    public void assignGridMember(String afId,String realName) {
        String ProPaht = System.getProperty("user.dir") + "/src/main/resources/NepDatas/ObjectData/";

        List<AqiFeedback> alist = (List<AqiFeedback>)FileUtil.readObject(ProPaht+"aqifeedback.txt");
        for (AqiFeedback af : alist) {
            if(af.getAfId().toString().equals(afId)){
                af.setGmName(realName);
                af.setState("已指派");
                break;
            }
        }
        FileUtil.writeObject(ProPaht+"aqifeedback.txt", alist);
        
        // 更新网格员状态为工作中
        List<GridMember> glist = (List<GridMember>) FileUtil.readObject(ProPaht+"gridmember.txt");
        for(GridMember gm : glist) {
            if(gm.getRealName().equals(realName)) {
                gm.setState("工作中");
                break;
            }
        }
        FileUtil.writeObject(ProPaht+"gridmember.txt", glist);
    }

    @Override
    public void confirmData(AqiFeedback afb) {
        String ProPaht = System.getProperty("user.dir") + "/src/main/resources/NepDatas/ObjectData/";
        List<AqiFeedback> afList = (List<AqiFeedback>)FileUtil.readObject(ProPaht+"aqifeedback.txt");
        for(int i = 0; i< afList.size();i++){
            AqiFeedback a = afList.get(i);
            if(a.getGmName() != null && a.getGmName().equals(afb.getGmName()) && a.getAfId().intValue()==afb.getAfId().intValue()){
                a.setState(afb.getState());
                a.setConfirmDate(afb.getConfirmDate());
                a.setCo(afb.getCo());
                a.setSo2(afb.getSo2());
                a.setPm(afb.getPm());
                a.setConfirmLevel(afb.getConfirmLevel());
                a.setConfirmExplain(afb.getConfirmExplain());
                break;
            }
        }
        FileUtil.writeObject(ProPaht+"aqifeedback.txt", afList);
        
        // 更新网格员状态为空闲
        List<GridMember> glist = (List<GridMember>) FileUtil.readObject(ProPaht+"gridmember.txt");
        for(GridMember gm : glist) {
            if(gm.getRealName().equals(afb.getGmName())) {
                gm.setState("空闲");
                break;
            }
        }
        FileUtil.writeObject(ProPaht+"gridmember.txt", glist);
    }

    @Override
    public List<AqiFeedback> getUnprocessedFeedbacksOrderByPoints() {
        String ProPaht = System.getProperty("user.dir") + "/src/main/resources/NepDatas/ObjectData/";
        List<AqiFeedback> afList = (List<AqiFeedback>) FileUtil.readObject(ProPaht+"aqifeedback.txt");
        List<Supervisor> slist = (List<Supervisor>) FileUtil.readObject(ProPaht+"supervisor.txt");

        // 创建监督员积分映射
        java.util.Map<String, Integer> supervisorPoints = new java.util.HashMap<>();
        for (Supervisor s : slist) {
            supervisorPoints.put(s.getLoginCode(), s.getPoints() != null ? s.getPoints() : 0);
        }

        // 过滤未处理的反馈并按积分排序
        return afList.stream()
                .filter(af -> "未指派".equals(af.getState()))
                .sorted(Comparator.comparing(af -> supervisorPoints.getOrDefault(af.getAfName(), 0), Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }

}
