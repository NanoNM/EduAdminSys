create table class
(
    id          int auto_increment comment '自增主键'
        primary key,
    class_name  varchar(64)                          null comment '班级名称',
    class_year  int                                  null comment '班级年级 对应的是年级表年级id',
    create_time datetime default current_timestamp() null comment '创建时间',
    modify_time datetime default current_timestamp() null
)
    comment '班级表';

create table grade
(
    id          int auto_increment comment '自增主键'
        primary key,
    grade_name  varchar(64)                                   not null comment '年级名称',
    grade_year  year        default year(current_timestamp()) null comment '年级年份',
    create_time datetime    default current_timestamp()       null comment '创建时间',
    modify_time datetime    default current_timestamp()       null comment '修改时间',
    status      varchar(16) default 'normal'                  null,
    constraint grade_pk
        unique (grade_year)
)
    comment '年级表';

create table student_class
(
    student_id int null comment '学生id',
    class_id   int null comment '班级id',
    constraint student_class_pk
        unique (student_id)
)
    comment '学生班级表';

create table user
(
    id          int auto_increment comment '自增主键id'
        primary key,
    name        varchar(50)                             not null comment '用户名称',
    password    varchar(128)                            not null,
    user_no     varchar(64)                             not null comment '用户随机编号',
    employee_id mediumtext                              not null comment '职位编号 学生则为学号',
    reg_key     varchar(16)                             null,
    create_time datetime    default current_timestamp() null comment '用户创建时间',
    modify_time datetime    default current_timestamp() null comment '用户修改时间',
    last_time   datetime                                null comment '最后登录时间',
    role        varchar(16) default 'unabsorbed'        null comment '用户权限组',
    constraint user_uuid
        unique (user_no)
);

