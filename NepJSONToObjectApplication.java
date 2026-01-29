package com.lyjsoft.nep.util;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lyjsoft.nep.po.*;
import com.lyjsoft.nep.util.FileUtil;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

// His项目将json文件转换为object文件
public class NepJSONToObjectApplication {

	public static ClassLoader classLoader = NepJSONToObjectApplication.class.getClassLoader();
	public static ObjectMapper objectMapper = new ObjectMapper();

	public static String getPath(){
		return System.getProperty("user.dir")+"/src/main/resources/NepDatas/JsonToObjectData/";
	}

	public static void setAdmin(){
		List<Admin> admins = new ArrayList<>();
        try {
        	InputStream inputStream = classLoader.getResourceAsStream("NepDatas/JSONData/admins.json");
            // 将JSON数组字符串转换为User对象列表
        	admins = objectMapper.readValue(inputStream, new TypeReference<List<Admin>>() {});

            // 打印解析后的对象列表
            for (Admin admin : admins) {
            	System.out.println(admin);
            }
            FileUtil.writeObject(getPath() + "Admin.txt",admins);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Admin> admin = (List<Admin>)FileUtil.readObject(getPath() + "Admin.txt");
        System.out.println(admin);
	}


	public static void setAqiFeedback(){
		List<AqiFeedback> aqiFeedbacks = new ArrayList<AqiFeedback>();
        try {
        	InputStream inputStream = classLoader.getResourceAsStream("NepDatas/JSONData/aqi_feedback.json");

        	aqiFeedbacks = objectMapper.readValue(inputStream, new TypeReference<List<AqiFeedback>>() {});

            for (AqiFeedback aqiFeedback : aqiFeedbacks) {
            	System.out.println(aqiFeedback);
            }
            FileUtil.writeObject(getPath() + "AqiFeedback.txt",aqiFeedbacks);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<AqiFeedback> aqiFeedback = (List<AqiFeedback>) FileUtil.readObject(getPath() + "AqiFeedback.txt");
        System.out.println(aqiFeedback);
	}




	public static void setApi(){
		List<Api> apis = new ArrayList<Api>();
        try {
        	InputStream inputStream = classLoader.getResourceAsStream("NepDatas/JSONData/aqi.json");

        	apis = objectMapper.readValue(inputStream, new TypeReference<List<Api>>() {});

            for (Api api : apis) {
            	System.out.println(api);
            }
            FileUtil.writeObject(getPath() + "Api.txt",apis);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Api> api = (List<Api>)FileUtil.readObject(getPath() + "Api.txt");
        System.out.println(api);
	}

	public static void setGridCity(){
		List<GridCity> gridCitys = new ArrayList<GridCity>();
        try {
        	InputStream inputStream = classLoader.getResourceAsStream("NepDatas/JSONData/grid_city.json");

        	gridCitys = objectMapper.readValue(inputStream, new TypeReference<List<GridCity>>() {});

            for (GridCity gridCity : gridCitys) {
            	System.out.println(gridCity);
            }
            FileUtil.writeObject(getPath() + "GridCity.txt",gridCitys);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<GridCity> gridCity = (List<GridCity>)FileUtil.readObject(getPath() + "GridCity.txt");
        System.out.println(gridCity);
	}

	public static void setGridMember(){
		List<GridMember> gridMembers = new ArrayList<GridMember>();
        try {
        	InputStream inputStream = classLoader.getResourceAsStream("NepDatas/JSONData/grid_member.json");

        	gridMembers = objectMapper.readValue(inputStream, new TypeReference<List<GridMember>>() {});

            for (GridMember gridMember : gridMembers) {
            	System.out.println(gridMember);
            }
            FileUtil.writeObject(getPath() + "GridMember.txt",gridMembers);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<GridMember> disposalRequest = (List<GridMember>)FileUtil.readObject(getPath() + "GridMember.txt");
        System.out.println(disposalRequest);
	}


	public static void setGridProvince(){
		List<GridProvince> gridProvinces = new ArrayList<GridProvince>();
        try {
        	InputStream inputStream = classLoader.getResourceAsStream("NepDatas/JSONData/grid_province.json");

        	gridProvinces = objectMapper.readValue(inputStream, new TypeReference<List<GridProvince>>() {});

            for (GridProvince drugInfo : gridProvinces) {
            	System.out.println(drugInfo);
            }
            FileUtil.writeObject(getPath() + "GridProvince.txt",gridProvinces);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<GridProvince> gridProvince = (List<GridProvince>)FileUtil.readObject(getPath() + "GridProvince.txt");
        System.out.println(gridProvince);
	}
	public static void setStatistics(){
		List<Statistics> statisticss = new ArrayList<Statistics>();
        try {
        	InputStream inputStream = classLoader.getResourceAsStream("NepDatas/JSONData/statistics.json");

        	statisticss = objectMapper.readValue(inputStream, new TypeReference<List<Statistics>>() {});

            for (Statistics statistics : statisticss) {
            	System.out.println(statistics);
            }
            FileUtil.writeObject(getPath() + "Statistics.txt",statisticss);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Statistics> statistics = (List<Statistics>)FileUtil.readObject(getPath() + "Statistics.txt");
        System.out.println(statistics);
	}

	public static void setSupervisor(){
		List<Supervisor> supervisors = new ArrayList<Supervisor>();
        try {
        	InputStream inputStream = classLoader.getResourceAsStream("NepDatas/JSONData/supervisor.json");

        	supervisors = objectMapper.readValue(inputStream, new TypeReference<List<Supervisor>>() {});

            for (Supervisor supervisor : supervisors) {
            	System.out.println(supervisor);
            }
            FileUtil.writeObject(getPath() + "Supervisor.txt",supervisors);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Supervisor> supervisor = (List<Supervisor>)FileUtil.readObject(getPath() + "Supervisor.txt");
        System.out.println(supervisor);
	}
	public static void main(String[] args) throws Exception {

		NepJSONToObjectApplication.setAdmin();
		NepJSONToObjectApplication.setAqiFeedback();
		NepJSONToObjectApplication.setApi();
		NepJSONToObjectApplication.setGridCity();
		NepJSONToObjectApplication.setGridMember();
		NepJSONToObjectApplication.setGridProvince();
		NepJSONToObjectApplication.setStatistics();
		NepJSONToObjectApplication.setSupervisor();
	}
}