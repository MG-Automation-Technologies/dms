#
# Generic activity database definition
#
CREATE TABLE activity(act_date TIMESTAMP, act_user VARCHAR(32), act_token VARCHAR(48), act_action VARCHAR(48), act_item VARCHAR(256), act_params VARCHAR(256));
