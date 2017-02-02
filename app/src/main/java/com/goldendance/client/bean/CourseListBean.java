package com.goldendance.client.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by hemingway on 2017/2/2.
 */

public class CourseListBean implements Serializable {
    private int pageSize;
    private int pageNumber;
    private int totalCount;
    ArrayList<CourseBean> list;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public ArrayList<CourseBean> getList() {
        return list;
    }

    public void setList(ArrayList<CourseBean> list) {
        this.list = list;
    }
}
