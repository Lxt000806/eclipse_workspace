package com.house.home.client.service.resp;


@SuppressWarnings("rawtypes")
public class GetWaterAreaCfmResp extends BaseResponse {
    private Double waterArea;

    public Double getWaterArea() {
        return waterArea;
    }

    public void setWaterArea(Double waterArea) {
        this.waterArea = waterArea;
    }
}
