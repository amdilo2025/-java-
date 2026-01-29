package com.lyjsoft.nep;

import com.lyjsoft.nep.chart.CityPollutionChart;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Insets;

public class NepvMain extends Application {
    private CityPollutionChart pollutionChart;

    @Override
    public void start(Stage stage) {
        // 创建主布局
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));

        // 创建图表
        pollutionChart = new CityPollutionChart();

        // 创建刷新按钮
        Button refreshButton = new Button("刷新数据");
        refreshButton.setOnAction(e -> pollutionChart.updateChart());

        // 添加组件到布局
        root.getChildren().addAll(pollutionChart, refreshButton);

        // 创建场景
        Scene scene = new Scene(root, 800, 500);
        stage.setTitle("城市污染等级趋势图");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
} 