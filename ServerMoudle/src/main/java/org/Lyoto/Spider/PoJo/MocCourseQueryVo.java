package org.Lyoto.Spider.PoJo;

import java.io.Serializable;

/**
 * @author Lyoto
 * @Date 2021-05-02 15:49
 **/
public class MocCourseQueryVo implements Serializable {
    private Long categoryId;
    private Integer categoryChannelId;
    private Integer orderBy = 0;
    private  Integer status = 10;
    private Integer pageIndex = 1;
    private Integer pageSize = 20;



    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getCategoryChannelId() {
        return categoryChannelId;
    }

    public void setCategoryChannelId(Integer categoryChannelId) {
        this.categoryChannelId = categoryChannelId;
    }

    public Integer getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Integer orderBy) {
        this.orderBy = orderBy;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

}
