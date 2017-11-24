package test.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A Record.
 */
@Entity
@Table(name = "record")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Record implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 100)
    @Column(name = "title", length = 100)
    private String title;

    @Size(max = 1000)
    @Column(name = "content", length = 1000)
    private String content;

    @Column(name = "createtime")
    private Instant createtime;

    @Column(name = "updatetime")
    private Instant updatetime;

    @Column(name = "isdeleted")
    private Integer isdeleted;

    @Column(name = "isdone")
    private Integer isdone;

    @Column(name = "eventlevel")
    private Integer eventlevel;

    @Size(max = 40)
    @Column(name = "openid", length = 40)
    private String openid;

    @ManyToOne
    private WxUser openid;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Record title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public Record content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Instant getCreatetime() {
        return createtime;
    }

    public Record createtime(Instant createtime) {
        this.createtime = createtime;
        return this;
    }

    public void setCreatetime(Instant createtime) {
        this.createtime = createtime;
    }

    public Instant getUpdatetime() {
        return updatetime;
    }

    public Record updatetime(Instant updatetime) {
        this.updatetime = updatetime;
        return this;
    }

    public void setUpdatetime(Instant updatetime) {
        this.updatetime = updatetime;
    }

    public Integer getIsdeleted() {
        return isdeleted;
    }

    public Record isdeleted(Integer isdeleted) {
        this.isdeleted = isdeleted;
        return this;
    }

    public void setIsdeleted(Integer isdeleted) {
        this.isdeleted = isdeleted;
    }

    public Integer getIsdone() {
        return isdone;
    }

    public Record isdone(Integer isdone) {
        this.isdone = isdone;
        return this;
    }

    public void setIsdone(Integer isdone) {
        this.isdone = isdone;
    }

    public Integer getEventlevel() {
        return eventlevel;
    }

    public Record eventlevel(Integer eventlevel) {
        this.eventlevel = eventlevel;
        return this;
    }

    public void setEventlevel(Integer eventlevel) {
        this.eventlevel = eventlevel;
    }

    public String getOpenid() {
        return openid;
    }

    public Record openid(String openid) {
        this.openid = openid;
        return this;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public WxUser getOpenid() {
        return openid;
    }

    public Record openid(WxUser wxUser) {
        this.openid = wxUser;
        return this;
    }

    public void setOpenid(WxUser wxUser) {
        this.openid = wxUser;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Record record = (Record) o;
        if (record.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), record.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Record{" +
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
