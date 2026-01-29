package com.lyjsoft.nep.chart;

import com.lyjsoft.nep.entity.AqiFeedback;
import com.lyjsoft.nep.entity.ProvinceCity;
import com.lyjsoft.nep.util.FileUtil;
import javafx.collections.FXCollections;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CityPollutionChart extends VBox {

    private LineChart<String, Number> chart;
    private Map<String, List<AqiFeedback>> cityData;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private ComboBox<String> cityComboBox;
    private ComboBox<String> pollutantComboBox;
    private List<ProvinceCity> provinceCityList;

    public CityPollutionChart() {
        // 创建Y轴（污染物浓度）
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("污染物浓度 (μg/m³)");

        // 创建X轴（日期）
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("日期");

        // 创建折线图
        chart = new LineChart<>(xAxis, yAxis);
        chart.setTitle("各城市污染物浓度趋势");
        chart.setCreateSymbols(true);
        chart.setLegendVisible(false);

        // 创建城市选择下拉菜单
        HBox controlBox = new HBox(10); // 10是组件之间的间距
        Label cityLabel = new Label("选择城市：");
        cityComboBox = new ComboBox<>();
        
        // 创建污染物选择下拉菜单
        Label pollutantLabel = new Label("选择污染物：");
        pollutantComboBox = new ComboBox<>();
        pollutantComboBox.getItems().addAll("SO₂", "CO", "PM2.5");
        pollutantComboBox.setValue("SO₂");
        
        controlBox.getChildren().addAll(cityLabel, cityComboBox, pollutantLabel, pollutantComboBox);

        // 加载省份城市数据
        loadProvinceCityData();
        // 加载污染数据
        loadData();

        // 添加城市选择和污染物选择事件监听器
        cityComboBox.setOnAction(e -> updateChart());
        pollutantComboBox.setOnAction(e -> updateChart());

        // 添加到布局
        getChildren().addAll(controlBox, chart);
        chart.getStylesheets().add(getClass().getResource("/com/lyjsoft/nep/css/ChartStyleView.css").toExternalForm());
        this.setStyle("-fx-background-color: #BAD59C;");
    }

    private void loadProvinceCityData() {
        String proPath = System.getProperty("user.dir") + "/src/main/resources/NepDatas/ObjectData/";
        provinceCityList = (List<ProvinceCity>) FileUtil.readObject(proPath + "province_city.txt");
        
        // 获取所有城市并添加到下拉菜单
        List<String> allCities = provinceCityList.stream()
                .flatMap(province -> province.getCityName().stream())
                .collect(Collectors.toList());
        
        cityComboBox.setItems(FXCollections.observableArrayList(allCities));
        if (!allCities.isEmpty()) {
            cityComboBox.setValue(allCities.get(0));
        }
    }

    private void loadData() {
        String proPath = System.getProperty("user.dir") + "/src/main/resources/NepDatas/ObjectData/";
        Object obj = FileUtil.readObject(proPath + "aqifeedback.txt");
        if (obj == null) {
            System.out.println("读取aqifeedback.txt失败，返回null");
            showError("读取aqifeedback.txt失败，文件不存在或内容为空！");
            cityData = new HashMap<>();
            return;
        }
        if (!(obj instanceof java.util.List)) {
            System.out.println("读取到的对象不是List类型: " + obj.getClass());
            showError("读取到的对象不是List类型: " + obj.getClass());
            cityData = new HashMap<>();
            return;
        }
        java.util.List<?> rawList = (java.util.List<?>) obj;
        if (rawList.isEmpty() || !(rawList.get(0) instanceof AqiFeedback)) {
            System.out.println("List为空或元素不是AqiFeedback类型");
            showError("aqifeedback.txt内容为空或元素不是AqiFeedback类型！");
            cityData = new HashMap<>();
            return;
        }
        java.util.List<AqiFeedback> allData = (java.util.List<AqiFeedback>) rawList;
        System.out.println("成功读取到AqiFeedback数据，条数: " + allData.size());
        for (AqiFeedback f : allData) {
            System.out.println("城市: " + f.getCityName() + ", 日期: " + f.getDate() + ", 预估等级: " + f.getEstimateGrade() + ", 实测等级: " + f.getConfirmLevel());
        }
        cityData = allData.stream()
                .collect(Collectors.groupingBy(AqiFeedback::getCityName));
        updateChart();
    }

    private void showError(String msg) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("数据读取错误");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    public void updateChart() {
        chart.getData().clear();
        String selectedCity = cityComboBox.getValue();
        String selectedPollutant = pollutantComboBox.getValue();
        if (selectedCity == null || selectedPollutant == null) return;

        // 获取最近7天的日期
        LocalDate today = LocalDate.now();
        List<String> last7Days = new ArrayList<>();
        for (int i = 6; i >= 0; i--) {
            last7Days.add(today.minusDays(i).format(DATE_FORMATTER));
        }

        List<AqiFeedback> cityFeedbacks = cityData.get(selectedCity);
        if (cityFeedbacks == null) return;

        // 按日期分组
        Map<String, List<AqiFeedback>> dailyMap = cityFeedbacks.stream()
            .collect(Collectors.groupingBy(AqiFeedback::getDate));

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName(selectedPollutant);

        for (String date : last7Days) {
            List<AqiFeedback> dayList = dailyMap.get(date);
            if (dayList != null && !dayList.isEmpty()) {
                double avg = Double.NaN;
                switch (selectedPollutant) {
                    case "SO₂":
                        avg = dayList.stream().filter(f->f.getSo2()!=null).mapToDouble(AqiFeedback::getSo2).average().orElse(Double.NaN);
                        break;
                    case "CO":
                        avg = dayList.stream().filter(f->f.getCo()!=null).mapToDouble(AqiFeedback::getCo).average().orElse(Double.NaN);
                        break;
                    case "PM2.5":
                        avg = dayList.stream().filter(f->f.getPm()!=null).mapToDouble(AqiFeedback::getPm).average().orElse(Double.NaN);
                        break;
                }
                if (!Double.isNaN(avg)) {
                    avg = Math.round(avg * 10.0) / 10.0;
                    series.getData().add(new XYChart.Data<>(date, avg));
                }
            }
        }
        if (!series.getData().isEmpty()) chart.getData().add(series);
    }
}