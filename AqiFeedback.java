package com.lyjsoft.nep.po;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AqiFeedback implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonProperty("af_id")
    private Integer afId;

    @JsonProperty("tel_id")
    private String telId;

    @JsonProperty("province_id")
    private Integer provinceId;

    @JsonProperty("city_id")
    private Integer cityId;

    private String address;

    private String information;

    @JsonProperty("estimated_grade")
    private Integer estimatedGrade;

    @JsonProperty("af_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date afDate;

    @JsonProperty("af_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private Date afTime;

    @JsonProperty("gm_id")
    private Integer gmId;

    @JsonProperty("assign_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date assignDate;

    @JsonProperty("assign_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private Date assignTime;

    private Integer state;

    private String remarks;

    public AqiFeedback() {
    }

    public AqiFeedback(Integer afId, String telId, Integer provinceId, Integer cityId, String address, String information, Integer estimatedGrade, Date afDate, Date afTime, Integer gmId, Date assignDate, Date assignTime, Integer state, String remarks) {
        this.afId = afId;
        this.telId = telId;
        this.provinceId = provinceId;
        this.cityId = cityId;
        this.address = address;
        this.information = information;
        this.estimatedGrade = estimatedGrade;
        this.afDate = afDate;
        this.afTime = afTime;
        this.gmId = gmId;
        this.assignDate = assignDate;
        this.assignTime = assignTime;
        this.state = state;
        this.remarks = remarks;
    }

    public Integer getAfId() {
        return afId;
    }

    public void setAfId(Integer afId) {
        this.afId = afId;
    }

    public String getTelId() {
        return telId;
    }

    public void setTelId(String telId) {
        this.telId = telId;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public Integer getEstimatedGrade() {
        return estimatedGrade;
    }

    public void setEstimatedGrade(Integer estimatedGrade) {
        this.estimatedGrade = estimatedGrade;
    }

    public Date getAfDate() {
        return afDate;
    }

    public void setAfDate(Date afDate) {
        this.afDate = afDate;
    }

    public Date getAfTime() {
        return afTime;
    }

    public void setAfTime(Date afTime) {
        this.afTime = afTime;
    }

    public Integer getGmId() {
        return gmId;
    }

    public void setGmId(Integer gmId) {
        this.gmId = gmId;
    }

    public Date getAssignDate() {
        return assignDate;
    }

    public void setAssignDate(Date assignDate) {
        this.assignDate = assignDate;
    }

    public Date getAssignTime() {
        return assignTime;
    }

    public void setAssignTime(Date assignTime) {
        this.assignTime = assignTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public String toString() {
        return "AqiFeedback [afId=" + afId + ", telId=" + telId + ", provinceId=" + provinceId + ", cityId=" + cityId + ", address=" + address + ", information=" + information + ", estimatedGrade=" + estimatedGrade + ", afDate=" + afDate + ", afTime=" + afTime + ", gmId=" + gmId + ", assignDate=" + assignDate + ", assignTime=" + assignTime + ", state=" + state + ", remarks=" + remarks + "]";
    }
}