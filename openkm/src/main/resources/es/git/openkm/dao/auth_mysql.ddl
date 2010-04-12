#
# Generic auth database definition
#
CREATE TABLE users(usr_id VARCHAR(32), usr_name VARCHAR(64), usr_pass VARCHAR(32) NOT NULL, usr_email VARCHAR(32) NOT NULL, usr_active BOOLEAN, PRIMARY KEY(usr_id));
CREATE TABLE roles(rol_id VARCHAR(32), PRIMARY KEY(rol_id));
CREATE TABLE user_role(ur_user VARCHAR(32), ur_role VARCHAR(32), PRIMARY KEY(ur_user, ur_role));
CREATE TABLE mail_accounts(ma_id INTEGER AUTO_INCREMENT, ma_user VARCHAR(32), ma_mhost VARCHAR(32), ma_muser VARCHAR(32), ma_mpass VARCHAR(32), ma_mfolder VARCHAR(32), ma_active BOOLEAN, PRIMARY KEY(ma_id));
CREATE TABLE twitter_accounts(ta_id INTEGER AUTO_INCREMENT, ta_user VARCHAR(32), ta_tuser VARCHAR(32), ta_active BOOLEAN, PRIMARY KEY(ta_id));

# INSERT DEFAULT USER / ROLES
INSERT INTO users (usr_id, usr_name, usr_pass, usr_email, usr_active) VALUES ('okmAdmin', 'Administrator', '21232f297a57a5a743894a0e4a801fc3', '', true);
INSERT INTO roles (rol_id) VALUES ('AdminRole');
INSERT INTO roles (rol_id) VALUES ('UserRole');
INSERT INTO user_role (ur_user, ur_role) VALUES ('okmAdmin', 'AdminRole');
