package test.myapp.service.dto;


import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the WxUser entity.
 */
public class WxUserDTO implements Serializable {

    private Long id;

    @Size(max = 40)
    private String openid;

    private String subscribe;

    @Size(max = 20)
    private String nickName;

    private Integer sex;

    private String language;

    private String city;

    private String province;

    private String country;

    private Instant subscribetime;

    @Size(max = 1000)
    private String headimgurl;

    private Integer iscancel;

    private Instant createtime;

    private Instant updatetime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(String subscribe) {
        this.subscribe = subscribe;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Instant getSubscribetime() {
        return subscribetime;
    }

    public void setSubscribetime(Instant subscribetime) {
        this.subscribetime = subscribetime;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public Integer getIscancel() {
        return iscancel;
    }

    public void setIscancel(Integer iscancel) {
        this.iscancel = iscancel;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        WxUserDTO wxUserDTO = (WxUserDTO) o;
        if(wxUserDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), wxUserDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "WxUserDTO{" +
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
