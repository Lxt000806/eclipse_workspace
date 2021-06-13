package com.house.home.client.service.evt;

public class ItemDetailEvt extends BaseEvt {
		private String id;
		private String custCode;

		
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getCustCode() {
			return custCode;
		}
		public void setCustCode(String custCode) {
			this.custCode = custCode;
		}	
}
