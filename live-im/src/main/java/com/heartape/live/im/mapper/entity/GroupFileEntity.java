package com.heartape.live.im.mapper.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@TableName("live_message_group_file")
public class GroupFileEntity {

    /** id */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /** messageId */
    private String messageId;

    /** 下载路径 */
    private String url;

    /** 下载路径 */
    private String filename;

    /** 文件大小 */
    private Integer size;

}
