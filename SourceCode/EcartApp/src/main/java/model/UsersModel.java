package model;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class UsersModel {
	private int userId;
    private String username;
    private String email;
    private String password;
    private Timestamp createdAt;
    private String role; // Use String to represent the ENUM value ('admin' or 'normal')

}
