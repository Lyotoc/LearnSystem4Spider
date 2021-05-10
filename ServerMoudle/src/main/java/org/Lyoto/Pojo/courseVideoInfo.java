package org.Lyoto.Pojo;

/**
 * @author Lyoto
 * @Date 2021-05-07 18:37
 **/
public class courseVideoInfo {
    private Integer videoId;
    private Integer duration;
    private String  name;
    private String videoImgUrl;
    private String size;
    private String videoUrl;
    private String format;
    private Integer currentTermId;

    public Integer getVideoId() {
        return videoId;
    }

    public void setVideoId(Integer videoId) {
        this.videoId = videoId;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVideoImgUrl() {
        return videoImgUrl;
    }

    public void setVideoImgUrl(String videoImgUrl) {
        this.videoImgUrl = videoImgUrl;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public Integer getCurrentTermId() {
        return currentTermId;
    }

    public void setCurrentTermId(Integer currentTermId) {
        this.currentTermId = currentTermId;
    }

    @Override
    public String toString() {
        return "courseVideoInfo{" +
                "videoId=" + videoId +
                ", duration=" + duration +
                ", name='" + name + '\'' +
                ", videoImgUrl='" + videoImgUrl + '\'' +
                ", size='" + size + '\'' +
                ", videoUrl='" + videoUrl + '\'' +
                ", format='" + format + '\'' +
                ", currentTermId=" + currentTermId +
                '}';
    }
}
