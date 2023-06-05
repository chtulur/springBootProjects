package com.chtulur.invoiceapp.repo;

import java.util.Collection;

import com.chtulur.invoiceapp.model.Role;

public interface RoleRepository<T extends Role> {
	T create (T data);
	Collection<T> list (int page, int pageSize);
	T get (Long id);
	T update (T data);
	Boolean delete (Long id);
	
  //More Complex Operations
  void addRoleToUser(Long userId, String roleName);
  Role getRoleByUserId(Long userId);
  Role getRoleByUserEmail(String email);
  void updateUserRole(Long userId, String roleName);
}
