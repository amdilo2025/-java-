package com.lyjsoft.nep;


import com.lyjsoft.nep.controller.NepgLoginViewController;
import com.lyjsoft.nep.util.JavafxUtil;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * 东软环保公众监督平台-网格员端 应用程序启动
 *
 * @author yichunlin
 *
 */
public class NepgMain extends Application {
	@Override
	public void start(Stage primaryStage) throws IOException {
		NepgLoginViewController.primaryStage = primaryStage;
		JavafxUtil.showStage(NepgMain.class,"view/NepgLoginView.fxml", primaryStage, "东软环保公众监督平台-网格员端");
	}

	public static void main(String[] args) {
		launch(args);
	}
}
