create table user
(
    id          int auto_increment comment '自增主键id'
        primary key,
    name        varchar(50)                             not null comment '用户名称',
    password    varchar(128)                            not null,
    user_no     varchar(64)                             not null comment '用户随机编号',
    employee_id mediumtext                              not null comment '职位编号 学生则为学号',
    create_time datetime    default current_timestamp() null comment '用户创建时间',
    modify_time datetime                                null comment '用户修改时间',
    last_time   datetime                                null comment '最后登录时间',
    role        varchar(16) default 'unabsorbed'        null comment '用户权限组',
    constraint user_emp_id
        unique (employee_id) using hash,
    constraint user_uuid
        unique (user_no)
);

