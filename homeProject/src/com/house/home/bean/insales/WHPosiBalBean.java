package com.house.home.bean.insales;

import com.house.framework.commons.excel.ExcelAnnotation;

/**
 * @Description: TODO 库位调整Excel导入使用bean
 * @author created by zb
 * @date   2018-8-24--下午2:12:58
 */
public class WHPosiBalBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ExcelAnnotation(exportName="产品", order=1)
	private String itCode;
	@ExcelAnnotation(exportName="原库位", order=2)
	private Integer fromKwPk;
	@ExcelAnnotation(exportName="目的库位", order=3)
	private Integer toKwPk;
	@ExcelAnnotation(exportName="数量", order=4)
	private Double qtyMove;
	@ExcelAnnotation(exportName="错误信息", order=5)
	private String error;
	
	public String getItCode() {
		return itCode;
	}
	public void setItCode(String itCode) {
		this.itCode = itCode;
	}
	public Integer getFromKwPk() {
		return fromKwPk;
	}
	public void setFromKwPk(Integer fromKwPk) {
		this.fromKwPk = fromKwPk;
	}
	public Integer getToKwPk() {
		return toKwPk;
	}
	public void setToKwPk(Integer toKwPk) {
		this.toKwPk = toKwPk;
	}
	public Double getQtyMove() {
		return qtyMove;
	}
	public void setQtyMove(Double qtyMove) {
		this.qtyMove = qtyMove;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}

}
