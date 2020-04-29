package com.tuya.smart.sweeper.demo.base.bean;

import java.util.List;

/**
 * Create by blitzfeng on 2020-04-15
 */
public class MapDataBean {

    private String devId;
    private String startRow;
    private List<String> dataList;
    private int subRecordId;
    private boolean hasNext;
    private long startTime;
    private long endTime;
    private int status;

    public String getDevId() {
        return devId;
    }

    public void setDevId(String devId) {
        this.devId = devId;
    }

    public String getStartRow() {
        return startRow;
    }

    public void setStartRow(String startRow) {
        this.startRow = startRow;
    }

    public List<String> getDataList() {
        return dataList;
    }

    public void setDataList(List<String> dataList) {
        this.dataList = dataList;
    }

    public int getSubRecordId() {
        return subRecordId;
    }

    public void setSubRecordId(int subRecordId) {
        this.subRecordId = subRecordId;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
