package org.Lyoto.Spider.PoJo;

/**
 * 二级目录的POJO类
 * @author Lyoto
 * @Date 2021-05-02 14:43
 **/
public class Secondary_Url {
    private Long id;
    private String name;
    private Integer type;
    private Integer courseCout;
    private String iconUrl;
    private Long parentId;
    private String linkName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getCourseCout() {
        return courseCout;
    }

    public void setCourseCout(Integer courseCout) {
        this.courseCout = courseCout;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getLinkName() {
        return linkName;
    }

    public void setLinkName(String linkName) {
        this.linkName = linkName;
    }
}
