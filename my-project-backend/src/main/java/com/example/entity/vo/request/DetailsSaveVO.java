package com.example.entity.vo.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 用户详细信息
 */
@Data
public class DetailsSaveVO {

    @Pattern(regexp = "^[a-zA-Z0-9\\u4e00-\\u9fa5]+$")
    @Length(min = 2, max = 10)
    String username;
    @Min(0)
    @Max(1)
    Integer gender;
    @Pattern(regexp = "^(13|14|15|17|18)\\d{9}$")
    @Length(max = 11)
    String phone;
    @Length(max = 13)
    String qq;
    @Length(max = 30)
    String wx;
    @Length(max = 200)
    String description;
}
