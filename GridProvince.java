package com.lyjsoft.nep.po;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GridProvince implements Serializable {
    private static final long serialVersionUID = 1L;

	@JsonProperty("province_id")
	private int provinceId;

	@JsonProperty("province_name")
	private String provinceName;

	@JsonProperty("province_abbr")
	private String provinceAbbr;

	private String remarks;

	public GridProvince() {
		super();
	}
	public GridProvince(int provinceId, String provinceName, String provinceAbbr, String remarks) {
		super();
		this.provinceId = provinceId;
		this.provinceName = provinceName;
		this.provinceAbbr = provinceAbbr;
		this.remarks = remarks;
	}
	public void setProvinceId(int provinceId) {
		this.provinceId = provinceId;
	}
	public int getProvinceId() {
		return this.provinceId;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public String getProvinceName() {
		return this.provinceName;
	}
	public void setProvinceAbbr(String provinceAbbr) {
		this.provinceAbbr = provinceAbbr;
	}
	public String getProvinceAbbr() {
		return this.provinceAbbr;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getRemarks() {
		return this.remarks;
	}
	@Override
	public String toString() {
		return "GridProvince [provinceId=" + provinceId + ", provinceName=" + provinceName + ", provinceAbbr="
				+ provinceAbbr + ", remarks=" + remarks + "]";
	}


}
