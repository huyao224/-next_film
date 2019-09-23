package com.next.huyao.film.dao.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
/**
 * <p>
 * 
 * </p>
 *
 * @author huyao
 * @since 2019-09-16
 */
public class NextUser extends Model<NextUser> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "UUID", type = IdType.AUTO)
    private Integer uuid;

    private String userName;

    private String userPwd;

    public Integer getUuid() {
        return uuid;
    }

    public void setUuid(Integer uuid) {
        this.uuid = uuid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    @Override
    protected Serializable pkVal() {
        return null;
    }

    @Override
    public String toString() {
        return "NextUser{" +
        ", uuid=" + uuid +
        ", userName=" + userName +
        ", userPwd=" + userPwd +
        "}";
    }
}
