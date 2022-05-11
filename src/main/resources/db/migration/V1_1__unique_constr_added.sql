alter table users add unique (email);
create index index_users_id_email on users using btree(id, email);

alter table groups add unique (name);
create index index_groups_id_name on groups using btree(id, name);

alter table tags add unique (name);
create index index_tags_id_name on tags using btree(id, name);