package com.house.home.client.service.evt;

public class MaterialSearchEvt extends BaseQueryEvt {
		private String itemType1;
		private String searchText;

		public String getItemType1() {
			return itemType1;
		}
		public void setItemType1(String itemType1) {
			this.itemType1 = itemType1;
		}
		public String getSearchText() {
			return searchText;
		}
		public void setSearchText(String searchText) {
			this.searchText = searchText;
		}
		
}
