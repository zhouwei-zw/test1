package com.example.demo.entity;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 
 * </p>
 *
 * @author alex wong
 * @since 2021-11-08
 */
@Data
@TableName(value = "testbm")
@AllArgsConstructor
@NoArgsConstructor
public class Testbm implements Serializable {

    private static final long serialVersionUID=1L;

      private String id;

    private String name;

    private String sex;

    private Date birth;


}
