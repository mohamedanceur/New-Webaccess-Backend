package com.Atos.Webaccess.User.Entities;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordResetModel {

    private String userName;
    private String oldPassword;
    private String newPassword;
}
