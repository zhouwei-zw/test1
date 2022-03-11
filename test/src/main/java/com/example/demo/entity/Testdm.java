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
 * @since 2021-12-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Testdm implements Serializable {

    private static final long serialVersionUID=1L;

      private String id;

    private String realName;

    private String realAge;


}
