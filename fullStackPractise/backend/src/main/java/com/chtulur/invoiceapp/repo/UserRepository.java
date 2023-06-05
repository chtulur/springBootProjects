package com.chtulur.invoiceapp.repo;

import java.util.Collection;

import com.chtulur.invoiceapp.model.User;

public interface UserRepository<T extends User> {
//	Basic CRUD operation
	T create (T data);
	Collection<T> list (int page, int pageSize);
	T get (Long id);
	T update (T data);
	Boolean delete (Long id);
	
//More Complex Operations
}
