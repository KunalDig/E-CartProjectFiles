package org.techhub.service;

import java.util.Optional;

import org.techhub.model.UserModel;
import org.techhub.repository.AuthRepository;
import org.techhub.repository.AuthRepositoryImpl;

public class AuthServiceImpl implements AuthService {
	AuthRepository arepo = new AuthRepositoryImpl();

	@Override
	public boolean registerUser(UserModel user) {
		return arepo.registerUser(user);
	}

	@Override
	public Optional<UserModel> login(String username, String password) {
		return arepo.login(username, password);
	}

	@Override
	public boolean isAdmin(String username) {
		return arepo.isAdmin(username);
	}
}
