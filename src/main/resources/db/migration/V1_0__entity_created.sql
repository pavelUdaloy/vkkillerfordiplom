create table users
(
    id         int8         not null,
    email      varchar(255) not null,
    password   varchar(255) not null,
    first_name varchar(255) not null,
    last_name  varchar(255) not null,
    birth_date timestamp    not null,
    primary key (id)
);

create table chats
(
    id   int8         not null,
    name varchar(255) not null,
    primary key (id)
);

create table users_chats
(
    user_id int8 not null,
    chat_id int8 not null,
    constraint users_chats_users_fk foreign key (user_id) references users,
    constraint users_chats_chats_fk foreign key (chat_id) references chats
);

create table messages
(
    id               int8         not null,
    user_id          int8         not null,
    chat_id          int8         not null,
    publication_date timestamp    not null,
    text             varchar(255) not null,
    primary key (id),
    constraint users_messages_fk foreign key (user_id) references users,
    constraint chats_messages_fk foreign key (chat_id) references chats
);

create table groups
(
    id       int8         not null,
    admin_id int8         not null,
    name     varchar(255) not null,
    primary key (id),
    constraint users_groups_fk foreign key (admin_id) references users
);

create table users_groups_subs
(
    user_id  int8 not null,
    group_id int8 not null,
    constraint users_groups_subs_users_fk foreign key (user_id) references users,
    constraint users_groups_subs_groups_fk foreign key (group_id) references groups
);

create table posts
(
    id               int8         not null,
    group_id         int8         not null,
    title            varchar(255) not null,
    text             varchar(255) not null,
    publication_date timestamp    not null,
    primary key (id),
    constraint groups_posts_fk foreign key (group_id) references groups
);

create table comments
(
    id               int8         not null,
    author_id        int8         not null,
    text             varchar(255) not null,
    publication_date timestamp    not null,
    post_id          int8         not null,
    primary key (id),
    constraint posts_comments_fk foreign key (post_id) references posts,
    constraint users_comments_fk foreign key (author_id) references users
);

create table tags
(
    id   int8         not null,
    name varchar(255) not null,
    primary key (id)
);

create table groups_tags
(
    group_id int8 not null,
    tag_id   int8 not null,
    constraint groups_tags_groups_fk foreign key (group_id) references groups,
    constraint groups_tags_tags_fk foreign key (tag_id) references tags
);

create table users_tags_blocks
(
    user_id int8 not null,
    tag_id  int8 not null,
    constraint groups_tags_groups_fk foreign key (user_id) references users,
    constraint groups_tags_tags_fk foreign key (tag_id) references tags
);

create table refresh_jwt_tokens
(
    id      int8 not null,
    user_id int8      not null,
    token   text      not null,
    primary key (id),
    constraint refresh_jwt_tokens_users_fk foreign key (user_id) references users
);
