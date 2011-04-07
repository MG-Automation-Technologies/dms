insert into OKM_USER (USR_ID, USR_NAME, USR_PASSWORD, USR_EMAIL, USR_ACTIVE) values ('okmAdmin', 'Administrator', '21232f297a57a5a743894a0e4a801fc3', 'admin@noreply.com', true);
insert into OKM_ROLE (ROL_ID, ROL_ACTIVE) values ('AdminRole', true);
insert into OKM_ROLE (ROL_ID, ROL_ACTIVE) values ('UserRole', true);
insert into OKM_USER_ROLE (UR_USER, UR_ROLE) values ('okmAdmin', 'AdminRole');
insert into OKM_PROFILE (PRF_ID, PRF_NAME, PRF_ACTIVE) values (1, 'Default', true);
