package com.fcoin.entity;


public class OrderTradeResponse {

    private String  status;
    private String  data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "OrderTradeResponse{" +
                "status='" + status + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}

