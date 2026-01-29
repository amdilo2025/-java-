package com.lyjsoft.nep.controller;


import com.lyjsoft.nep.NepgMain;
import com.lyjsoft.nep.NepsMain;
import com.lyjsoft.nep.entity.GridMember;
import com.lyjsoft.nep.service.GridMemberService;
import com.lyjsoft.nep.service.impl.GridMemberServiceImpl;
import com.lyjsoft.nep.util.JavafxUtil;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.Map;

public class NepgLoginViewController {
    @FXML
    private TextField txt_id;
    @FXML
    private TextField txt_password;
    @FXML
    private Label errorLabel;
    //多态
    private GridMemberService gridMemberService = new GridMemberServiceImpl();
    //主舞台
    public static Stage primaryStage;
    private Map<String, Integer> loginFailMap = new HashMap<>();
    private Map<String, Integer> lockCountMap = new HashMap<>();
    private Map<String, Long> unlockTimeMap = new HashMap<>();

    public TextField getTxt_id() {
        return txt_id;
    }
    public void setTxt_id(TextField txt_id) {
        this.txt_id = txt_id;
    }
    public TextField getTxt_password() {
        return txt_password;
    }
    public void setTxt_password(TextField txt_password) {
        this.txt_password = txt_password;
    }

    public void login(){
        String userId = txt_id.getText();
        long now = System.currentTimeMillis();
        // 检查是否锁定
        int lockCount = lockCountMap.getOrDefault(userId, 0);
        if (lockCount >= 3) {
            JavafxUtil.showAlert(primaryStage, "账号锁定", "该账号已被永久锁定", "请联系管理员", "warn");
            return;
        }
        Long unlockTime = unlockTimeMap.get(userId);
        if (unlockTime != null && now < unlockTime) {
            long secondsLeft = (unlockTime - now) / 1000;
            JavafxUtil.showAlert(primaryStage, "账号锁定", "该账号已被锁定", "请" + secondsLeft + "秒后再试", "warn");
            return;
        }
        if(txt_id.getText().equals("")){
            JavafxUtil.showAlert(primaryStage, "数据格式错误", "登录账号不能为空", "请重新输入登录账号","warn");
            return;
        }
        if(txt_password.getText().equals("")){
            JavafxUtil.showAlert(primaryStage, "数据格式错误", "登录密码不能为空", "请重新输入登录密码","warn");
            return;
        }
        NepgAqiConfirmViewController.primaryStage = primaryStage;
        GridMember gm = gridMemberService.login(txt_id.getText(), txt_password.getText());
        if(gm!=null){
            NepgAqiConfirmViewController.gridMember = gm;
            // 登录成功，重置锁定和失败次数
            loginFailMap.put(userId, 0);
            lockCountMap.put(userId, 0);
            unlockTimeMap.remove(userId);
            JavafxUtil.showStage(NepgMain.class, "view/NepgAqiConfirmView.fxml", primaryStage, "东软环保公众监督平台-确认AQI反馈数据");
        }else{
            int failCount = loginFailMap.getOrDefault(userId, 0) + 1;
            loginFailMap.put(userId, failCount);
            if (failCount >= 3) {
                int newLockCount = lockCount + 1;
                lockCountMap.put(userId, newLockCount);
                loginFailMap.put(userId, 0); // 重置失败次数
                if (newLockCount == 1) {
                    unlockTimeMap.put(userId, now + 60 * 1000); // 60秒
                    JavafxUtil.showAlert(primaryStage, "账号锁定", "该账号已被锁定", "请60秒后再试", "warn");
                } else if (newLockCount == 2) {
                    unlockTimeMap.put(userId, now + 180 * 1000); // 180秒
                    JavafxUtil.showAlert(primaryStage, "账号锁定", "该账号已被锁定", "请180秒后再试", "warn");
                } else if (newLockCount >= 3) {
                    unlockTimeMap.remove(userId); // 永久锁定
                    JavafxUtil.showAlert(primaryStage, "账号锁定", "该账号已被永久锁定", "请联系管理员", "warn");
                }
            } else {
                JavafxUtil.showAlert(primaryStage, "登录失败", "登录账号和密码错误","请重新输入账号和密码","warn");
            }
        }
        String username = txt_id.getText();
        String password = txt_password.getText();
        // 模拟密码验证
        if (!"correct".equals(password)) {
            showError("密码错误，请重新输入！");
        } else {
            // 登录成功逻辑
        }
    }
    private void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);

        // 创建动画
        TranslateTransition slideDown = new TranslateTransition(Duration.seconds(0.5), errorLabel);
        slideDown.setFromY(-30);
        slideDown.setToY(0);
        slideDown.play();

        // 一段时间后隐藏提示
        javafx.animation.PauseTransition pause = new javafx.animation.PauseTransition(Duration.seconds(3));
        pause.setOnFinished(e -> {
            TranslateTransition slideUp = new TranslateTransition(Duration.seconds(0.5), errorLabel);
            slideUp.setFromY(0);
            slideUp.setToY(-30);
            slideUp.setOnFinished(event -> errorLabel.setVisible(false));
            slideUp.play();
        });
        pause.play();
    }
    public void forget(){
        NepgForgetViewController.primaryStage = primaryStage;
        JavafxUtil.showStage(NepgMain.class,"view/NepgForgetView.fxml", primaryStage, "更改密码");
    }

}
