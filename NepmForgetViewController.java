package com.lyjsoft.nep.controller;

import com.lyjsoft.nep.NepmMain;
import com.lyjsoft.nep.service.AdminService;
import com.lyjsoft.nep.service.impl.AdminServiceImpl;
import com.lyjsoft.nep.util.JavafxUtil;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NepmForgetViewController {
    @FXML
    private TextField txt_id;
    @FXML
    private PasswordField txt_password;
    @FXML
    private PasswordField txt_repassword;
    public static Stage primaryStage;
    private AdminService adminService = new AdminServiceImpl();

    public void confirm() {
        if(!txt_password.getText().equals(txt_repassword.getText())) {
            JavafxUtil.showAlert(primaryStage, "修改密码失败", "两次输入密码不一致", "请重新输入确认密码","warn");
            txt_repassword.setText("");
            return;
        }
        if(txt_password.getText().isEmpty()) {
            JavafxUtil.showAlert(primaryStage, "修改密码失败", "密码不能为空", "请输入新密码","warn");
            return;
        }
        boolean flag = adminService.updatePassword(txt_id.getText(), txt_password.getText(),txt_repassword.getText());
        if(flag) {
            JavafxUtil.showAlert(primaryStage, "修改成功", "密码修改成功", "请使用新密码登录","info");
            JavafxUtil.showStage(NepmMain.class, "view/NepmLoginView.fxml", primaryStage, "东软环保公众监督平台-管理端");
        } else {
            JavafxUtil.showAlert(primaryStage, "修改失败", "该账号不存在", "请检查账号是否正确","warn");
            txt_id.setText("");
        }
    }

    public void back() {
        JavafxUtil.showStage(NepmMain.class, "view/NepmLoginView.fxml", primaryStage, "东软环保公众监督平台-管理端");
    }
} 