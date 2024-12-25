package service;

import model.UsersModel;
import repository.UserRepositoryImpl;
import repository.UsersRepository;

public class UsersServiceImpl implements UsersService {
	UsersRepository userRepo = new UserRepositoryImpl();

	public boolean isAddNewUser(UsersModel model) {

		return userRepo.isAddNewUser(model);
	}

}
