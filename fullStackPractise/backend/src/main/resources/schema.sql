-- General rules
-- use underscore_names instead of camelCase
-- table names should be plural
-- spell out id fields (item_id instead of id)
-- don't use ambiguous column names
-- name foreign key columns the same as the columns they refer to
-- use caps for all SQL queries

SET NAMES "UTF8MB4";
SET TIME_ZONE = "+2:00";

USE practisedb;

DROP TABLE IF EXISTS Users;

CREATE TABLE Users
(
  user_id       BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  first_name    VARCHAR(50) NOT NULL,
  last_name     VARCHAR(50) NOT NULL,
  email         VARCHAR(100) NOT NULL,
  password      VARCHAR(255) DEFAULT NULL,
  address       VARCHAR(255) DEFAULT NULL,
  phone         VARCHAR(30) DEFAULT NULL,
  title         VARCHAR(50) DEFAULT NULL,
  bio           VARCHAR(255) DEFAULT NULL,
  enabled       BOOLEAN DEFAULT FALSE,
  non_locked    BOOLEAN DEFAULT TRUE,
  using_mfa     BOOLEAN DEFAULT FALSE,
  created_at    DATETIME DEFAULT CURRENT_TIMESTAMP,
  image_url     VARCHAR(255) DEFAULT "https://cdn-icons-png.flaticon.com/512/149/149071.png",
  CONSTRAINT UQ_Users_Email UNIQUE (email)
);

DROP TABLE IF EXISTS Roles;


CREATE TABLE Roles
(
  role_id     BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name        VARCHAR(50) NOT NULL,
  permissions VARCHAR(255) NOT NULL,
  CONSTRAINT UQ_Roles_Name UNIQUE (name)
);

DROP TABLE IF EXISTS User_Roles;

CREATE TABLE User_Roles
(
    user_role_id  BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id       BIGINT UNSIGNED NOT NULL,
    role_id       BIGINT UNSIGNED NOT NULL,
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (role_id) REFERENCES Roles(role_id) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT UQ_User_Roles_User_id UNIQUE (user_id)
);

DROP TABLE IF EXISTS Events;

CREATE TABLE Events
(
    event_id    BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    type        VARCHAR(50) NOT NULL CHECK(type IN ("LOGIN_ATTEMPT", "LOGIN_ATTEMPT_FAILURE", "PROFILE_UPDATE", "PROFILE_PICTURE_UPDATE", "ROLE_UPDATE", "ACCOUNT_SETTINGS_UPDATE", "PASSWORD_UPDATE", "MFA_UPDATE")),
    description VARCHAR(255) NOT NULL,
    CONSTRAINT UQ_Events_Type UNIQUE (type)
);

DROP TABLE IF EXISTS User_Events;

CREATE TABLE User_Events
(
    user_event_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id       BIGINT UNSIGNED NOT NULL,
    event_id      BIGINT UNSIGNED NOT NULL,
    device        VARCHAR(100) DEFAULT NULL,
    ip_address    VARCHAR(100) DEFAULT NULL,
    created_at    DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (event_id) REFERENCES Events(event_id) ON DELETE RESTRICT ON UPDATE CASCADE
);


DROP TABLE IF EXISTS Account_Verifications;

CREATE TABLE Account_Verifications
(
    account_verification_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id                 BIGINT UNSIGNED NOT NULL,
    url                     VARCHAR(255) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT UQ_Account_Verifications_user_id UNIQUE (user_id),
    CONSTRAINT UQ_Account_Verifications_url UNIQUE (url)
);


DROP TABLE IF EXISTS Reset_Password_Verification;

CREATE TABLE Reset_Password_Verification
(
    reset_password_verification_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id                        BIGINT UNSIGNED NOT NULL,
    url                            VARCHAR(255) NOT NULL,
    expiration_date                DATETIME NOT NULL,
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT UQ_Reset_Password_Verification_user_id UNIQUE (user_id),
    CONSTRAINT UQ_Reset_Password_Verification_url UNIQUE (url)
);


DROP TABLE IF EXISTS Two_Factor_Auth;

CREATE TABLE Two_Factor_Auth
(
    two_factor_auth_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id            BIGINT UNSIGNED NOT NULL,
    code               VARCHAR(10) NOT NULL,
    expiration_date    DATETIME NOT NULL,
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT UQ_Two_Factor_Auth_user_id UNIQUE (user_id),
    CONSTRAINT UQ_Two_Factor_Auth_url UNIQUE (code)
);
