package test.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A WxUser.
 */
@Entity
@Table(name = "wx_user")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class WxUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 40)
    @Column(name = "openid", length = 40)
    private String openid;

    @Column(name = "subscribe")
    private String subscribe;

    @Size(max = 20)
    @Column(name = "nick_name", length = 20)
    private String nickName;

    @Column(name = "sex")
    private Integer sex;

    @Column(name = "language")
    private String language;

    @Column(name = "city")
    private String city;

    @Column(name = "province")
    private String province;

    @Column(name = "country")
    private String country;

    @Column(name = "subscribetime")
    private Instant subscribetime;

    @Size(max = 1000)
    @Column(name = "headimgurl", length = 1000)
    private String headimgurl;

    @Column(name = "iscancel")
    private Integer iscancel;

    @Column(name = "createtime")
    private Instant createtime;

    @Column(name = "updatetime")
    private Instant updatetime;

    @OneToMany(mappedBy = "wxUser")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Record> openids = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOpenid() {
        return openid;
    }

    public WxUser openid(String openid) {
        this.openid = openid;
        return this;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getSubscribe() {
        return subscribe;
    }

    public WxUser subscribe(String subscribe) {
        this.subscribe = subscribe;
        return this;
    }

    public void setSubscribe(String subscribe) {
        this.subscribe = subscribe;
    }

    public String getNickName() {
        return nickName;
    }

    public WxUser nickName(String nickName) {
        this.nickName = nickName;
        return this;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getSex() {
        return sex;
    }

    public WxUser sex(Integer sex) {
        this.sex = sex;
        return this;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getLanguage() {
        return language;
    }

    public WxUser language(String language) {
        this.language = language;
        return this;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCity() {
        return city;
    }

    public WxUser city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public WxUser province(String province) {
        this.province = province;
        return this;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public WxUser country(String country) {
        this.country = country;
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Instant getSubscribetime() {
        return subscribetime;
    }

    public WxUser subscribetime(Instant subscribetime) {
        this.subscribetime = subscribetime;
        return this;
    }

    public void setSubscribetime(Instant subscribetime) {
        this.subscribetime = subscribetime;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public WxUser headimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
        return this;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public Integer getIscancel() {
        return iscancel;
    }

    public WxUser iscancel(Integer iscancel) {
        this.iscancel = iscancel;
        return this;
    }

    public void setIscancel(Integer iscancel) {
        this.iscancel = iscancel;
    }

    public Instant getCreatetime() {
        return createtime;
    }

    public WxUser createtime(Instant createtime) {
        this.createtime = createtime;
        return this;
    }

    public void setCreatetime(Instant createtime) {
        this.createtime = createtime;
    }

    public Instant getUpdatetime() {
        return updatetime;
    }

    public WxUser updatetime(Instant updatetime) {
        this.updatetime = updatetime;
        return this;
    }

    public void setUpdatetime(Instant updatetime) {
        this.updatetime = updatetime;
    }

    public Set<Record> getOpenids() {
        return openids;
    }

    public WxUser openids(Set<Record> records) {
        this.openids = records;
        return this;
    }

    public WxUser addOpenid(Record record) {
        this.openids.add(record);
        record.setWxUser(this);
        return this;
    }

    public WxUser removeOpenid(Record record) {
        this.openids.remove(record);
        record.setWxUser(null);
        return this;
    }

    public void setOpenids(Set<Record> records) {
        this.openids = records;
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
        WxUser wxUser = (WxUser) o;
        if (wxUser.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), wxUser.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "WxUser{" +
            "id=" + getId() +
            ", openid='" + getOpenid() + "'" +
            ", subscribe='" + getSubscribe() + "'" +
            ", nickName='" + getNickName() + "'" +
            ", sex='" + getSex() + "'" +
            ", language='" + getLanguage() + "'" +
            ", city='" + getCity() + "'" +
            ", province='" + getProvince() + "'" +
            ", country='" + getCountry() + "'" +
            ", subscribetime='" + getSubscribetime() + "'" +
            ", headimgurl='" + getHeadimgurl() + "'" +
            ", iscancel='" + getIscancel() + "'" +
            ", createtime='" + getCreatetime() + "'" +
            ", updatetime='" + getUpdatetime() + "'" +
            "}";
    }
}
