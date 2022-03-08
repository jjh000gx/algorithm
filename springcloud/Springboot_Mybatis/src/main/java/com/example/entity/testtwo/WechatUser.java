package com.example.entity.testtwo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Getter
@Setter
@Table(name = "wechat_user")
public class WechatUser implements Serializable {
    @Column(name = "id")
    @Id
    private Long              id;

    @Column(name = "name")
    private String            name;

    @Column(name = "gzh_flag")
    private String            gzhFlag;

    @Column(name = "openid")
    private String            openid;

}
