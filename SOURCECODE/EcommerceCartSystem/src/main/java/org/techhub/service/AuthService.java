package org.techhub.service;

import java.util.Optional;

import org.techhub.model.UserModel;

public interface AuthService {
	public boolean registerUser(UserModel user);

	public Optional<UserModel> login(String username, String password);

	public boolean isAdmin(String username);
}
