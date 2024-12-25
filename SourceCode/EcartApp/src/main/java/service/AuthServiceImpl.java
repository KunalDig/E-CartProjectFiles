package service;

import repository.AuthRepo;
import repository.AuthRepoImpl;

public class AuthServiceImpl implements AuthService {
	AuthRepo authRepo = new AuthRepoImpl();

	public String authenticate(String username, String password) {
		return authRepo.authenticate(username, password);
	}

}
