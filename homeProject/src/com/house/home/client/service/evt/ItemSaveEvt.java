package com.house.home.client.service.evt;

import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;
import java.util.Map;
public class ItemSaveEvt extends BaseEvt {
		@NotEmpty(message="操作员编号不能为空")
		private String id;
		@NotEmpty(message="批次名称不能为空")
		private String remarks;
		private String itemType;
		private String batchDetail;
		private String batchType;
		private String status;
		private String custCode;
		private String otherRemarks;
		
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getRemarks() {
			return remarks;
		}
		public void setRemarks(String remarks) {
			this.remarks = remarks;
		}
		public String getItemType() {
			return itemType;
		}
		public void setItemType(String itemType) {
			this.itemType = itemType;
		}
		public String getBatchDetail() {
			return batchDetail;
		}
		public void setBatchDetail(String batchDetail) {
			this.batchDetail = batchDetail;
		}
		public String getBatchType() {
			return batchType;
		}
		public void setBatchType(String batchType) {
			this.batchType = batchType;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public String getCustCode() {
			return custCode;
		}
		public void setCustCode(String custCode) {
			this.custCode = custCode;
		}
		public String getOtherRemarks() {
			return otherRemarks;
		}
		public void setOtherRemarks(String otherRemarks) {
			this.otherRemarks = otherRemarks;
		}
	
		
}
