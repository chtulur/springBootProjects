package com.chtulur.invoiceapp.repo.implementation;

import java.util.Collection;
import static java.util.Map.of;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.chtulur.invoiceapp.exception.ApiException;
import com.chtulur.invoiceapp.rowmapper.RoleRowMapper;
import com.chtulur.invoiceapp.model.Role;
import com.chtulur.invoiceapp.repo.RoleRepository;
import static com.chtulur.invoiceapp.enums.RoleType.ROLE_USER;
import static com.chtulur.invoiceapp.query.RoleQuery.*;
import static java.util.Objects.requireNonNull;


@Repository
@RequiredArgsConstructor
@Slf4j
public class RoleRepoImpl implements RoleRepository<Role> {
    private final NamedParameterJdbcTemplate jdbc;

    @Override
    public Role create(Role data) {
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }

    @Override
    public Collection<Role> list(int page, int pageSize) {
        throw new UnsupportedOperationException("Unimplemented method 'list'");
    }

    @Override
    public Role get(Long id) {
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }

    @Override
    public Role update(Role data) {
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public Boolean delete(Long id) {
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public void addRoleToUser(Long userId, String roleName) {
        log.info("Adding role {} to user id: {}", roleName, userId);

        try {
            Role role = jdbc.queryForObject(
                    SELECT_ROLE_BY_NAME_QUERY,
                    of("roleName", roleName),
                    new RoleRowMapper()
            );

            jdbc.update(
                    INSERT_ROLE_TO_USER_QUERY,
                    of(
                            "userId", userId,
                            "roleId", requireNonNull(role).getRoleId())
            );
        } catch (EmptyResultDataAccessException exception) {
            throw new ApiException("No role found by name: " + ROLE_USER.name());
        } catch (Exception exception) {
            throw new ApiException("An error occurred. Please try again.");
        }
    }

    @Override
    public Role getRoleByUserId(Long userId) {
        throw new UnsupportedOperationException("Unimplemented method 'getRoleByUserId'");
    }

    @Override
    public Role getRoleByUserEmail(String email) {
        throw new UnsupportedOperationException("Unimplemented method 'getRoleByUserEmail'");
    }

    @Override
    public void updateUserRole(Long userId, String roleName) {
        throw new UnsupportedOperationException("Unimplemented method 'updateUserRole'");
    }

}
