package com.lyjsoft.nep.controller;


import com.lyjsoft.nep.NepmMain;
import com.lyjsoft.nep.NepsMain;
import com.lyjsoft.nep.service.AdminService;
import com.lyjsoft.nep.service.impl.AdminServiceImpl;
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

public class NepmLoginViewController {
    @FXML
    private TextField txt_id;
    @FXML
    private PasswordField txt_password;
    @FXML
    private Label errorLabel;
    //多态
    private AdminService adminService = new AdminServiceImpl();
    //主舞台
    public static Stage primaryStage;

    // 记录失败次数
    private Map<String, Integer> loginFailMap = new HashMap<>();
    // 记录锁定次数
    private Map<String, Integer> lockCountMap = new HashMap<>();
    // 记录解锁时间
    private Map<String, Long> unlockTimeMap = new HashMap<>();

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

    public void login() {
        String userId = txt_id.getText();
        String password = txt_password.getText();
        long now = System.currentTimeMillis();

        // 检查是否被锁定
        if (unlockTimeMap.containsKey(userId)) {
            long unlockTime = unlockTimeMap.get(userId);
            if (unlockTime == -1) {
                JavafxUtil.showAlert(primaryStage, "账号锁定", "该账号已被永久锁定", "请联系管理员", "warn");
                return;
            } else if (now < unlockTime) {
                long seconds = (unlockTime - now) / 1000;
                JavafxUtil.showAlert(primaryStage, "账号锁定", "该账号已被锁定", "请" + seconds + "秒后再试", "warn");
                return;
            } else {
                // 锁定时间已过，解除锁定
                unlockTimeMap.remove(userId);
                loginFailMap.put(userId, 0);
            }
        }

        boolean isLogin = adminService.login(userId, password);
        if (isLogin) {
            // 登录成功，重置锁定和失败次数
            loginFailMap.put(userId, 0);
            lockCountMap.put(userId, 0);
            unlockTimeMap.remove(userId);
            NepmMainViewController.primaryStage = primaryStage;
            JavafxUtil.showStage(NepmMain.class,"view/NepmMainView.fxml", primaryStage,"东软环保公众监督平台-管理端-主功能界面");
        } else {
            int failCount = loginFailMap.getOrDefault(userId, 0) + 1;
            loginFailMap.put(userId, failCount);
            int lockCount = lockCountMap.getOrDefault(userId, 0);

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
                    unlockTimeMap.put(userId, -1L); // 永久锁定
                    JavafxUtil.showAlert(primaryStage, "账号锁定", "该账号已被永久锁定", "请联系管理员", "warn");
                }
            } else {
                JavafxUtil.showAlert(primaryStage, "登录失败", "用户名密码错误", "请重新输入用户名和密码", "warn");
            }
        }
        String username = txt_id.getText();
        String password1 = txt_password.getText();
        // 模拟密码验证
        if (!"correct".equals(password1)) {
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
        NepmForgetViewController.primaryStage = primaryStage;
        JavafxUtil.showStage(NepmMain.class,"view/NepmForgetView.fxml", primaryStage, "更改密码");
    }


}
