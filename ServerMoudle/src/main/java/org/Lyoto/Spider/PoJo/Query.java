package org.Lyoto.Spider.PoJo;

/**
 * @author Lyoto
 * @Date 2021-05-03 15:06
 **/
public class Query {
    private String sortCriterial;
    private Integer DEFAULT_PAGE_SIZE = 10;
    private Integer DEFAULT_PAGE_INDEX = 1;
    private Integer DEFAULT_TOTLE_COUNT = 0;
    private Integer DEFAULT_OFFSET = 0;
    private Integer pageSize = 20;
    private Integer pageIndex = 1;
    private Integer totlePageCount = 4;
    private Integer totleCount = 72;
    private Integer offset = 0;
    private Integer limit = 20;

    public String getSortCriterial() {
        return sortCriterial;
    }

    public void setSortCriterial(String sortCriterial) {
        this.sortCriterial = sortCriterial;
    }

    public Integer getDEFAULT_PAGE_SIZE() {
        return DEFAULT_PAGE_SIZE;
    }

    public void setDEFAULT_PAGE_SIZE(Integer DEFAULT_PAGE_SIZE) {
        this.DEFAULT_PAGE_SIZE = DEFAULT_PAGE_SIZE;
    }

    public Integer getDEFAULT_PAGE_INDEX() {
        return DEFAULT_PAGE_INDEX;
    }

    public void setDEFAULT_PAGE_INDEX(Integer DEFAULT_PAGE_INDEX) {
        this.DEFAULT_PAGE_INDEX = DEFAULT_PAGE_INDEX;
    }

    public Integer getDEFAULT_TOTLE_COUNT() {
        return DEFAULT_TOTLE_COUNT;
    }

    public void setDEFAULT_TOTLE_COUNT(Integer DEFAULT_TOTLE_COUNT) {
        this.DEFAULT_TOTLE_COUNT = DEFAULT_TOTLE_COUNT;
    }

    public Integer getDEFAULT_OFFSET() {
        return DEFAULT_OFFSET;
    }

    public void setDEFAULT_OFFSET(Integer DEFAULT_OFFSET) {
        this.DEFAULT_OFFSET = DEFAULT_OFFSET;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getTotlePageCount() {
        return totlePageCount;
    }

    public void setTotlePageCount(Integer totlePageCount) {
        this.totlePageCount = totlePageCount;
    }

    public Integer getTotleCount() {
        return totleCount;
    }

    public void setTotleCount(Integer totleCount) {
        this.totleCount = totleCount;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
