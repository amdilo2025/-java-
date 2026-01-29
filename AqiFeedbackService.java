package com.lyjsoft.nep.service;


import com.lyjsoft.nep.entity.AqiFeedback;

import java.util.List;

public interface AqiFeedbackService {
    /**
     * 添加反馈信息
     * @param afb
     */
    public void saveFeedBack(AqiFeedback afb);
    /**
     * 指派网格员,修改反馈信息中网格员和状态
     */
    public void assignGridMember(String afId,String realName);
    /**
     * 提交实测AQI数据
     * @param afb
     */
    public void confirmData(AqiFeedback afb);

    /**
     * 获取未处理的反馈列表，按监督员积分排序
     * @return 反馈列表
     */
    public List<AqiFeedback> getUnprocessedFeedbacksOrderByPoints();
}
