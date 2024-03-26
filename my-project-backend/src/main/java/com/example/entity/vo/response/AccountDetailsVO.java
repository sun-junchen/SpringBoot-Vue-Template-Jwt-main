package com.example.entity.vo.response;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.entity.BaseData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 数据库中的用户详细信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDetailsVO implements BaseData {

    Integer gender;
    String phone;
    String qq;
    String wx;
    String description;
}
