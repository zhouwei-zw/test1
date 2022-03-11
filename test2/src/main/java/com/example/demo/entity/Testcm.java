package com.example.demo.entity;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author alex wong
 * @since 2021-11-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Testcm implements Serializable {

    private static final long serialVersionUID=1L;

      private String id;

    private String realName;

    private String realAge;


}
