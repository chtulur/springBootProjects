package com.chtulur.invoiceapp.repo.implementation;

import java.util.Collection;
import java.util.UUID;

import static java.util.Map.of;
import static java.util.Objects.requireNonNull;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.chtulur.invoiceapp.exception.ApiException;
import com.chtulur.invoiceapp.model.Role;
import com.chtulur.invoiceapp.model.User;
import com.chtulur.invoiceapp.repo.RoleRepository;
import com.chtulur.invoiceapp.repo.UserRepository;

import static com.chtulur.invoiceapp.query.UserQuery.*;
import static com.chtulur.invoiceapp.enums.RoleType.ROLE_USER;
import static com.chtulur.invoiceapp.enums.VerificationType.ACCOUNT;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserRepoImpl implements UserRepository<User> {
    private final NamedParameterJdbcTemplate jdbc;
    private final RoleRepository<Role> roleRepository;
    private final BCryptPasswordEncoder encoder;

    @Override
    public User create(User user) {

        if (getEmailCount(user.getEmail().trim().toLowerCase()) > 0)
            throw new ApiException("Email already in use. Please use a different email and "
                    + "try again");

        try {
            KeyHolder holder = new GeneratedKeyHolder();
            SqlParameterSource parameters = getSqlParameterSource(user);
            jdbc.update(INSERT_USER_QUERY, parameters, holder);

            user.setUserId(requireNonNull(holder.getKey()).longValue());
            roleRepository.addRoleToUser(user.getUserId(), ROLE_USER.name());

            String verificationUrl = getVerificationUrl(UUID.randomUUID().toString(), ACCOUNT.getType());

            jdbc.update(INSERT_ACCOUNT_VERIFICATION_URL_QUERY, of("userId", user.getUserId(), "url", verificationUrl));

            // emailService.sendVerificationUrl(user.getFirstName(), user.getEmail(),
            // verificationUrl, ACCOUNT);
            user.setEnabled(false);
            user.setNotLocked(true);

            return user;

        } catch (Exception exception) {
            throw new ApiException("An error occurred. Please try again.");
        }
    }

    @Override
    public Collection<User> list(int page, int pageSize) {
        return null;
    }

    @Override
    public User get(Long id) {
        return null;
    }

    @Override
    public User update(User data) {
        return null;
    }

    @Override
    public Boolean delete(Long id) {
        return null;
    }

    private int getEmailCount(String email) {
        Integer count = jdbc.queryForObject(
                COUNT_USER_EMAIL_QUERY,
                of("email", email),
                Integer.class
        );

        return count == null ? 0 : count;
    }

    private SqlParameterSource getSqlParameterSource(User user) {
        return new MapSqlParameterSource()
                .addValue("firstName", user.getFirstName())
                .addValue("lastName", user.getLastName())
                .addValue("email", user.getEmail())
                .addValue("password", encoder.encode(user.getPassword()));
    }

    private String getVerificationUrl(String uuidKey, String type) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/user/verify/" + type + "/" + uuidKey)
                .toUriString();
    }
}
