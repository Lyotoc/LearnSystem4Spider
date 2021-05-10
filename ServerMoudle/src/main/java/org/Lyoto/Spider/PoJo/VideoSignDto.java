package org.Lyoto.Spider.PoJo;


/**
 * @author Lyoto
 * @Date 2021-05-07 11:53
 **/
public class VideoSignDto {
    private Integer status = 0;
    private Integer videoId;
    private Integer duration;

    private String videoImgUrl;
    private String signature;
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VideoSignDto that = (VideoSignDto) o;

        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (videoId != null ? !videoId.equals(that.videoId) : that.videoId != null) return false;
        if (duration != null ? !duration.equals(that.duration) : that.duration != null) return false;
        if (videoImgUrl != null ? !videoImgUrl.equals(that.videoImgUrl) : that.videoImgUrl != null) return false;
        if (signature != null ? !signature.equals(that.signature) : that.signature != null) return false;
        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        int result = status != null ? status.hashCode() : 0;
        result = 31 * result + (videoId != null ? videoId.hashCode() : 0);
        result = 31 * result + (duration != null ? duration.hashCode() : 0);
        result = 31 * result + (videoImgUrl != null ? videoImgUrl.hashCode() : 0);
        result = 31 * result + (signature != null ? signature.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    public VideoSignDto(Integer videoId, String signature){
        this.videoId = videoId;
        this.signature = signature;
    }

    @Override
    public String toString() {
        return "videoSignDto{" +
                "status=" + status +
                ", videoId=" + videoId +
                ", duration=" + duration +
                ", videoImgUrl='" + videoImgUrl + '\'' +
                ", signature='" + signature + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public VideoSignDto(){
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getVideoId() {
        return videoId;
    }

    public void setVideoId(Integer videoId) {
        this.videoId = videoId;
    }

    public String getVideoImgUrl() {
        return videoImgUrl;
    }

    public void setVideoImgUrl(String videoImgUrl) {
        this.videoImgUrl = videoImgUrl;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
