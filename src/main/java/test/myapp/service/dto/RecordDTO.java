package test.myapp.service.dto;


import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Record entity.
 */
public class RecordDTO implements Serializable {

    private Long id;

    @Size(max = 100)
    private String title;

    @Size(max = 1000)
    private String content;

    private Instant createtime;

    private Instant updatetime;

    private Integer isdeleted;

    private Integer isdone;

    private Integer eventlevel;

    @Size(max = 40)
    private String openid;

    private Long openidId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Instant getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Instant createtime) {
        this.createtime = createtime;
    }

    public Instant getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Instant updatetime) {
        this.updatetime = updatetime;
    }

    public Integer getIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(Integer isdeleted) {
        this.isdeleted = isdeleted;
    }

    public Integer getIsdone() {
        return isdone;
    }

    public void setIsdone(Integer isdone) {
        this.isdone = isdone;
    }

    public Integer getEventlevel() {
        return eventlevel;
    }

    public void setEventlevel(Integer eventlevel) {
        this.eventlevel = eventlevel;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public Long getOpenidId() {
        return openidId;
    }

    public void setOpenidId(Long wxUserId) {
        this.openidId = wxUserId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RecordDTO recordDTO = (RecordDTO) o;
        if(recordDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), recordDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RecordDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", content='" + getContent() + "'" +
            ", createtime='" + getCreatetime() + "'" +
            ", updatetime='" + getUpdatetime() + "'" +
            ", isdeleted='" + getIsdeleted() + "'" +
            ", isdone='" + getIsdone() + "'" +
            ", eventlevel='" + getEventlevel() + "'" +
            ", openid='" + getOpenid() + "'" +
            "}";
    }
}
