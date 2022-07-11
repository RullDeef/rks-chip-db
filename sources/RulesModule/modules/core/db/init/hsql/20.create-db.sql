-- begin RULESMODULE_RULE_MANAGER
alter table RULESMODULE_RULE_MANAGER add constraint FK_RULESMODULE_RULE_MANAGER_DATA_SCRIPT foreign key (DATA_SCRIPT_ID) references RULESMODULE_RULE_DATA_SCRIPT(ID)^
create index IDX_RULESMODULE_RULE_MANAGER_DATA_SCRIPT on RULESMODULE_RULE_MANAGER (DATA_SCRIPT_ID)^
-- end RULESMODULE_RULE_MANAGER
-- begin RULESMODULE_RULE_MANAGER_RULE_SCRIPT_LINK
alter table RULESMODULE_RULE_MANAGER_RULE_SCRIPT_LINK add constraint FK_RRMRSL_RULE_MANAGER foreign key (RULE_MANAGER_ID) references RULESMODULE_RULE_MANAGER(ID)^
alter table RULESMODULE_RULE_MANAGER_RULE_SCRIPT_LINK add constraint FK_RRMRSL_RULE_SCRIPT foreign key (RULE_SCRIPT_ID) references RULESMODULE_RULE_SCRIPT(ID)^
-- end RULESMODULE_RULE_MANAGER_RULE_SCRIPT_LINK