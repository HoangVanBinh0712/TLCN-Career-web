package com.be.dto.loaduser;

import java.util.Date;

import com.be.utility.datatype.EGender;

import lombok.Data;

@Data
public class LoadUserDTO {

    private String id;

    private String email;

    private String name;

    private String phone;

    private String address;

    private Date birth;

    private EGender gender;
}
