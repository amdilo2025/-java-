package com.lyjsoft.nep.controller;


import com.lyjsoft.nep.NepsMain;
import com.lyjsoft.nep.service.SupervisorService;
import com.lyjsoft.nep.service.impl.SupervisorServiceImpl;
import com.lyjsoft.nep.util.JavafxUtil;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.Map;

public class NepsLoginViewController {

    @FXML
    private TextField txt_id;	//绑定登录账号文本框
    @FXML
    private PasswordField txt_password;	//绑定登录密码框
    @FXML
    private Label errorLabel;

    private Map<String, Integer> loginFailMap = new HashMap<>();
    private Map<String, Integer> lockCountMap = new HashMap<>();
    private Map<String, Long> unlockTimeMap = new HashMap<>();

    //主舞台
    public static Stage primaryStage;
    //多态
    public SupervisorService supervisorService = new SupervisorServiceImpl();

    public TextField getTxt_id() {
        return txt_id;
    }
    public void setTxt_id(TextField txt_id) {
        this.txt_id = txt_id;
    }
    public PasswordField getTxt_password() {
        return txt_password;
    }
    public void setTxt_password(PasswordField txt_password) {
        this.txt_password = txt_password;
    }

    /**
     * 绑定登录按钮事件
     */

    public void login() {
        String userId = txt_id.getText();
        long now = System.currentTimeMillis();

        // 检查ID长度
        if(txt_id.getText().length() != 11) {
            JavafxUtil.showAlert(primaryStage, "修改密码失败", "ID形式有误", "请输入正确形式","warn");
            txt_id.setText("");
            return;
        }

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

        boolean flag = supervisorService.login(userId, txt_password.getText());
        if (flag) {
            loginFailMap.put(userId, 0);
            // 解锁
            unlockTimeMap.remove(userId);
            lockCountMap.put(userId, 0);
            NepsSelectAqiViewController.primaryStage = primaryStage;
            JavafxUtil.showStage(NepsMain.class, "view/NepsSelectAqiView.fxml", primaryStage, "东软环保公众监督平台-公众监督员端-AQI数据反馈");
        } else {
            int failCount = loginFailMap.getOrDefault(userId, 0) + 1;
            loginFailMap.put(userId, failCount);
            if (failCount >= 3) {
                // 锁定逻辑
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
                JavafxUtil.showAlert(primaryStage, "登录失败", "用户名密码错误", "", "warn");
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

    /**
     * 绑定注册按钮事件
     */
    public void register(){
        //跳转到公众监督员注册界面
        NepsRegisterViewController.primaryStage = primaryStage;
        JavafxUtil.showStage(NepsMain.class,"view/NepsRegisterView.fxml", primaryStage, "东软环保公众监督平台-公众监督员端-公众监督员注册");
    }

    public void assesmentscore(){
        NepsRegisterViewController.primaryStage = primaryStage;
        JavafxUtil.showStage(NepsMain.class,"view/NepsRegisterView.fxml", primaryStage, "东软环保公众监督平台-公众监督员端-公众监督员注册");
    }
    public void forget(){
        NepsForgetViewController.primaryStage = primaryStage;
        JavafxUtil.showStage(NepsMain.class,"view/NepsForgetView.fxml", primaryStage, "更改密码");
    }

}

