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
    private String categoryName;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }




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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MocCourseQueryVo that = (MocCourseQueryVo) o;

        if (categoryId != null ? !categoryId.equals(that.categoryId) : that.categoryId != null) return false;
        if (categoryChannelId != null ? !categoryChannelId.equals(that.categoryChannelId) : that.categoryChannelId != null)
            return false;
        if (orderBy != null ? !orderBy.equals(that.orderBy) : that.orderBy != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (pageIndex != null ? !pageIndex.equals(that.pageIndex) : that.pageIndex != null) return false;
        return pageSize != null ? pageSize.equals(that.pageSize) : that.pageSize == null;
    }

    @Override
    public int hashCode() {
        int result = categoryId != null ? categoryId.hashCode() : 0;
        result = 31 * result + (categoryChannelId != null ? categoryChannelId.hashCode() : 0);
        result = 31 * result + (orderBy != null ? orderBy.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (pageIndex != null ? pageIndex.hashCode() : 0);
        result = 31 * result + (pageSize != null ? pageSize.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MocCourseQueryVo{" +
                "categoryId=" + categoryId +
                ", categoryChannelId=" + categoryChannelId +
                ", orderBy=" + orderBy +
                ", status=" + status +
                ", pageIndex=" + pageIndex +
                ", pageSize=" + pageSize +
                '}';
    }
}
