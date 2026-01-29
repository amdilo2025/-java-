package com.lyjsoft.nep.controller;

import com.lyjsoft.nep.entity.AqiFeedback;
import com.lyjsoft.nep.entity.GridMember;
import com.lyjsoft.nep.entity.Supervisor;
import com.lyjsoft.nep.service.AqiFeedbackService;
import com.lyjsoft.nep.service.impl.AqiFeedbackServiceImpl;
import com.lyjsoft.nep.util.FileUtil;
import com.lyjsoft.nep.util.JavafxUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class NepmAqiAssignViewController implements Initializable {
    @FXML
    private Pane txt_pane1;
    @FXML
    private Pane txt_pane2;
    @FXML
    private Pane txt_pane3;
    @FXML
    private TextField txt_afId;
    @FXML
    private Label label_afId;
    @FXML
    private Label  label_afName;
    @FXML
    private Label  label_proviceName;
    @FXML
    private Label  label_cityName;
    @FXML
    private Label  label_address;
    @FXML
    private Label  label_infomation;
    @FXML
    private Label  label_estimateGrade;
    @FXML
    private Label  label_date;
    @FXML
    private ComboBox<String> combo_realName;
    //当前舞台
    public static Stage aqiInfoStage;
    //多态
    private AqiFeedbackService aqiFeedbackService = new AqiFeedbackServiceImpl();
    // 当前显示的反馈列表
    private List<AqiFeedback> currentFeedbacks;
    // 当前显示的反馈索引
    private int currentFeedbackIndex = 0;

    public Pane getTxt_pane1() {
        return txt_pane1;
    }
    public void setTxt_pane1(Pane txt_pane1) {
        this.txt_pane1 = txt_pane1;
    }
    public Pane getTxt_pane2() {
        return txt_pane2;
    }
    public void setTxt_pane2(Pane txt_pane2) {
        this.txt_pane2 = txt_pane2;
    }
    public Pane getTxt_pane3() {
        return txt_pane3;
    }
    public void setTxt_pane3(Pane txt_pane3) {
        this.txt_pane3 = txt_pane3;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //初始化三个pane容器样式
        txt_pane1.setStyle("-fx-border-color: #CCC;");
        txt_pane2.setStyle("-fx-background-color: #CCC;");
        txt_pane3.setStyle("-fx-border-color: #CCC;");
        //标签初始化
        initConroller();
        //初始化网格员
        String ProPaht = System.getProperty("user.dir") + "/src/main/resources/NepDatas/ObjectData/";
        List<GridMember> glist = (List<GridMember>) FileUtil.readObject(ProPaht+"gridmember.txt");
        List<AqiFeedback> afList = (List<AqiFeedback>) FileUtil.readObject(ProPaht+"aqifeedback.txt");

        for (GridMember gm : glist) {
            if(gm.getState().equals("工作中") || gm.getState().equals("空闲")){
                // 只判断"已指派"状态的任务
                boolean hasTask = false;
                for(AqiFeedback af : afList) {
                    if(af.getGmName() != null && af.getGmName().equals(gm.getRealName()) &&
                            af.getState().equals("已指派")) {
                        hasTask = true;
                        break;
                    }
                }
                String displayText = gm.getRealName() + (hasTask ? " (有任务)" : " (空闲)");
                combo_realName.getItems().add(displayText);
            }
        }
    }

    public void queryFeedback(){
        String ProPaht = System.getProperty("user.dir") + "/src/main/resources/NepDatas/ObjectData/";
        List<AqiFeedback> afList = (List<AqiFeedback>) FileUtil.readObject(ProPaht+"aqifeedback.txt");
        List<Supervisor> slist = (List<Supervisor>) FileUtil.readObject(ProPaht+"supervisor.txt");

        // 创建监督员积分映射
        java.util.Map<String, Integer> supervisorPoints = new java.util.HashMap<>();
        for (Supervisor s : slist) {
            supervisorPoints.put(s.getLoginCode(), s.getPoints() != null ? s.getPoints() : 0);
        }

        // 过滤未处理的反馈并按积分排序
        currentFeedbacks = afList.stream()
                .filter(af -> "未指派".equals(af.getState()))
                .sorted((af1, af2) -> {
                    int points1 = supervisorPoints.getOrDefault(af1.getAfName(), 0);
                    int points2 = supervisorPoints.getOrDefault(af2.getAfName(), 0);
                    return Integer.compare(points2, points1); // 按积分从高到低排序
                })
                .collect(java.util.stream.Collectors.toList());

        if (currentFeedbacks.isEmpty()) {
            JavafxUtil.showAlert(aqiInfoStage, "查询失败", "没有待处理的反馈信息", "请稍后再试","warn");
            initConroller();
            return;
        }

        // 显示第一个反馈信息（积分最高的监督员的反馈）
        currentFeedbackIndex = 0;
        displayFeedback(currentFeedbacks.get(0));
    }

    // 显示下一个反馈
    public void nextFeedback() {
        if (currentFeedbacks == null || currentFeedbacks.isEmpty()) {
            JavafxUtil.showAlert(aqiInfoStage, "提示", "没有更多反馈信息", "请先查询反馈信息","warn");
            return;
        }

        currentFeedbackIndex = (currentFeedbackIndex + 1) % currentFeedbacks.size();
        displayFeedback(currentFeedbacks.get(currentFeedbackIndex));
    }

    // 显示上一个反馈
    public void previousFeedback() {
        if (currentFeedbacks == null || currentFeedbacks.isEmpty()) {
            JavafxUtil.showAlert(aqiInfoStage, "提示", "没有更多反馈信息", "请先查询反馈信息","warn");
            return;
        }

        currentFeedbackIndex = (currentFeedbackIndex - 1 + currentFeedbacks.size()) % currentFeedbacks.size();
        displayFeedback(currentFeedbacks.get(currentFeedbackIndex));
    }

    // 显示反馈信息
    private void displayFeedback(AqiFeedback af) {
        label_afId.setText(af.getAfId()+"");
        label_afName.setText(af.getAfName());
        label_address.setText(af.getAddress());
        label_cityName.setText(af.getCityName());
        label_date.setText(af.getDate());
        label_estimateGrade.setText(af.getEstimateGrade());
        label_infomation.setText(af.getInfomation());
        label_proviceName.setText(af.getProviceName());
    }

    public void assignGridMember(){
        //前做判断
        if(label_afId.getText().equals("无")){
            JavafxUtil.showAlert(aqiInfoStage, "指派失败", "未找到要指派的反馈信息", "请选择要指派的反馈信息","warn");
            return;
        }
        if(combo_realName.getValue().equals("请选择网格员")){
            JavafxUtil.showAlert(aqiInfoStage, "指派失败", "您没有选择要指派的网格员", "请选择您要指派的网格员","warn");
            return;
        }

        // 从显示文本中提取网格员姓名
        String displayText = combo_realName.getValue();
        String realName = displayText.split(" ")[0];

        // 检查网格员是否已有任务
        String ProPaht = System.getProperty("user.dir") + "/src/main/resources/NepDatas/ObjectData/";
        List<AqiFeedback> afList = (List<AqiFeedback>) FileUtil.readObject(ProPaht+"aqifeedback.txt");
        for(AqiFeedback af : afList) {
            if(af.getGmName() != null && af.getGmName().equals(realName) &&
                    af.getState().equals("已指派")) {
                JavafxUtil.showAlert(aqiInfoStage, "指派失败", "该网格员已有任务", "请选择其他空闲的网格员","warn");
                return;
            }
        }

        String afId = label_afId.getText();
        aqiFeedbackService.assignGridMember(afId, realName);
        JavafxUtil.showAlert(aqiInfoStage, "指派成功", "AQI反馈信息指派成功!", "请等待网格员实测数据信息","info");
        initConroller();
    }

    // 界面空间初始化方法
    public void initConroller(){
        txt_afId.setText("");
        label_afId.setText("无");
        label_afName.setText("无");
        label_address.setText("无");
        label_cityName.setText("无");
        label_date.setText("无");
        label_estimateGrade.setText("无");
        label_infomation.setText("无");
        label_proviceName.setText("无");
        combo_realName.setValue("请选择网格员");
        currentFeedbacks = null;
        currentFeedbackIndex = 0;
    }
}