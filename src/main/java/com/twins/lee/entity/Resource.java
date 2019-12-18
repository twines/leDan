package com.twins.lee.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@TableName("resource")
public class Resource {
    @Id
    private int id;
    private String url;
    private int status;
    private String ocr;
    private String qr;
    private int type;

    @TableField("resource_digest")
    private String resourceDigest;

    public String getResourceUri() {
        return this.url + "@" + id;
    }
}
