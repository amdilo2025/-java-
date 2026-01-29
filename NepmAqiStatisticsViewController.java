package com.lyjsoft.nep.controller;

import com.lyjsoft.nep.entity.AqiFeedback;
import com.lyjsoft.nep.util.FileUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class NepmAqiStatisticsViewController implements Initializable {

    @FXML
    private TableView<AqiFeedback> txt_tableView;
    @FXML
    private TableColumn<AqiStat, String> dateColumn;
    @FXML
    private TableColumn<AqiStat, Integer> aqiColumn;
    @FXML
    private TableColumn<AqiStat, String> levelColumn;
    @FXML
    private TableColumn<AqiStat, String> remarkColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub
        //初始化table 数据表
        TableColumn<AqiFeedback, Integer> afIdColumn = new TableColumn<>("编号");
        afIdColumn.setMinWidth(40);
        afIdColumn.setStyle("-fx-alignment: center;");	//居中
        afIdColumn.setCellValueFactory(new PropertyValueFactory<>("afId"));

        TableColumn<AqiFeedback, String> proviceNameColumn = new TableColumn<>("省区域");
        proviceNameColumn.setMinWidth(60);
        proviceNameColumn.setStyle("-fx-alignment: center;");	//居中
        proviceNameColumn.setCellValueFactory(new PropertyValueFactory<>("proviceName"));

        TableColumn<AqiFeedback, String> cityNameColumn = new TableColumn<>("市区域");
        cityNameColumn.setMinWidth(60);
        cityNameColumn.setStyle("-fx-alignment: center;");	//居中
        cityNameColumn.setCellValueFactory(new PropertyValueFactory<>("cityName"));

        TableColumn<AqiFeedback, String> dateColumn = new TableColumn<>("反馈时间");
        dateColumn.setMinWidth(80);
        dateColumn.setStyle("-fx-alignment: center;");	//居中
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<AqiFeedback, String> afNameColumn = new TableColumn<>("反馈者");
        afNameColumn.setMinWidth(60);
        afNameColumn.setStyle("-fx-alignment: center;");	//居中
        afNameColumn.setCellValueFactory(new PropertyValueFactory<>("afName"));

        TableColumn<AqiFeedback, String> so2Column = new TableColumn<>("SQ2浓度(ug/m3)");
        so2Column.setMinWidth(150);
        so2Column.setStyle("-fx-alignment: center;");	//居中
        so2Column.setCellValueFactory(new PropertyValueFactory<>("so2"));

        TableColumn<AqiFeedback, String> coColumn = new TableColumn<>("CO浓度(ug/m3)");
        coColumn.setMinWidth(150);
        coColumn.setStyle("-fx-alignment: center;");	//居中
        coColumn.setCellValueFactory(new PropertyValueFactory<>("co"));

        TableColumn<AqiFeedback, String> pmColumn = new TableColumn<>("PM2.5浓度(ug/m3)");
        pmColumn.setMinWidth(150);
        pmColumn.setStyle("-fx-alignment: center;");	//居中
        pmColumn.setCellValueFactory(new PropertyValueFactory<>("pm"));

        TableColumn<AqiFeedback, String> confirmLevelColumn = new TableColumn<>("实测等级");
        confirmLevelColumn.setMinWidth(60);
        confirmLevelColumn.setStyle("-fx-alignment: center;");	//居中
        confirmLevelColumn.setCellValueFactory(new PropertyValueFactory<>("confirmLevel"));

        TableColumn<AqiFeedback, String> confirmDateColumn = new TableColumn<>("实测日期");
        confirmDateColumn.setMinWidth(80);
        confirmDateColumn.setStyle("-fx-alignment: center;");	//居中
        confirmDateColumn.setCellValueFactory(new PropertyValueFactory<>("confirmDate"));

        TableColumn<AqiFeedback, String> gmNameColumn = new TableColumn<>("网格员");
        gmNameColumn.setMinWidth(60);
        gmNameColumn.setStyle("-fx-alignment: center;");	//居中
        gmNameColumn.setCellValueFactory(new PropertyValueFactory<>("gmName"));

        txt_tableView.getColumns().addAll(afIdColumn, proviceNameColumn,cityNameColumn,dateColumn,afNameColumn,so2Column,coColumn,pmColumn,confirmLevelColumn,confirmDateColumn,gmNameColumn);
        ObservableList<AqiFeedback> data = FXCollections.observableArrayList();
        String ProPaht = System.getProperty("user.dir") + "/src/main/resources/NepDatas/ObjectData/";

        List<AqiFeedback> afList = (List<AqiFeedback>) FileUtil.readObject(ProPaht+"aqifeedback.txt");
        for(AqiFeedback afb:afList){
            if(afb.getState().equals("已实测")){
                data.add(afb);
            }
        }
        txt_tableView.setItems(data);
    }

    public static class AqiStat {
        private String date;
        private int aqi;
        private String level;
        private String remark;

        public AqiStat(String date, int aqi, String level, String remark) {
            this.date = date;
            this.aqi = aqi;
            this.level = level;
            this.remark = remark;
        }
        // getter和setter省略
        public String getDate() { return date; }
        public int getAqi() { return aqi; }
        public String getLevel() { return level; }
        public String getRemark() { return remark; }
    }
}
