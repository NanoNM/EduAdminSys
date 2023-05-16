create database edusys;

create table class
(
    id            int auto_increment comment '自增主键'
        primary key,
    class_name    varchar(64)                          not null comment '班级名称',
    class_year    int                                  null comment '班级年级 对应的是年级表年级id',
    counsellor_id int                                  null comment '辅导员id',
    create_time   datetime default current_timestamp() null comment '创建时间',
    modify_time   datetime default current_timestamp() null,
    dept_id       int                                  null comment '系id',
    constraint class_pk
        unique (class_name, class_year)
)
    comment '班级表';

create table course_planning
(
    id          int auto_increment comment '自增主键'
        primary key,
    course_name varchar(128) not null comment '学科名',
    week        int          not null comment '周几上课',
    start_class int          not null comment '开始于',
    end_class   int          not null comment '结束于',
    local       varchar(256) not null comment '上课位置',
    class_id    int          null comment '上课的班级',
    week_times  int          null comment '第几周的课'
)
    comment '排课表';

create table curriculum
(
    id              int auto_increment comment '自增主键'
        primary key,
    course_name     varchar(64)   null,
    class_hour      int           null comment '总课时',
    level           int           null comment '对应年级',
    public_required int default 0 null comment '是否为公共必修 0为不是',
    credit          int           not null comment '学分',
    dept_id         int           null comment '从属系id'
);

create table department
(
    id          int auto_increment comment '自增主键'
        primary key,
    dept_name   varchar(128)                         null comment '院系名称',
    edu_sys     int      default 4                   null comment '学制',
    create_tiem datetime default current_timestamp() null,
    modify_time datetime default current_timestamp() null,
    constraint department_pk
        unique (dept_name)
);

create table dept_course
(
    id        int auto_increment comment '自增主键'
        primary key,
    dept_id   int null comment '系id',
    course_id int null comment '学科id'
);

create table edu_admin_notice
(
    id          int auto_increment comment '自增主键'
        primary key,
    title       varchar(128)                         null comment '公告标题',
    content     text                                 null comment '公告内容',
    create_time datetime default current_timestamp() null on update current_timestamp() comment '发布时间'
);

create table exam_class
(
    exam_id    int null,
    classes_id int null
)
    comment '考试信息对应班级';

create table exam_dept
(
    exam_id  int         null,
    dept_id  int         null,
    grade_id varchar(20) null
)
    comment '考试消息对应系';

create table exam_grade
(
    exam_id  int         null comment '考试信息id',
    grade_id varchar(10) null comment '年级id'
)
    comment '考试绑定年级';

create table exam_notification
(
    id         int auto_increment comment '唯一主键'
        primary key,
    name       varchar(128) null comment '相关考试名称',
    exam_local varchar(128) null comment '考试位置',
    start_time datetime     not null comment '考试开始时间'
)
    comment '考试通知表';

create table exam_tab
(
    id          int auto_increment comment '唯一主键'
        primary key,
    name        varchar(128) not null comment '考试名',
    score       double       not null comment '成绩',
    student_id  int          not null comment '学生id',
    exam_marker varchar(20)  null comment '阅卷老师'
)
    comment '成绩表';

create table grade
(
    id            int auto_increment comment '自增主键'
        primary key,
    grade_name    varchar(64)                                   not null comment '年级名称',
    grade_year    year        default year(current_timestamp()) null comment '年级年份',
    create_time   datetime    default current_timestamp()       null comment '创建时间',
    modify_time   datetime    default current_timestamp()       null comment '修改时间',
    status        varchar(16) default 'normal'                  null,
    level         int                                           null comment '年级',
    starting_date date                                          null comment '开学日期',
    constraint grade_level
        unique (level),
    constraint grade_pk
        unique (grade_year)
)
    comment '年级表';

create table stu_info
(
    id              int auto_increment comment '自增主键'
        primary key,
    stu_name        varchar(128)                 not null comment '学生名称',
    stu_gender      varchar(2)  default '男'     not null comment '性别',
    stu_birthday    date                         null comment '出生日期',
    stu_nation      varchar(64)                  null comment '民族',
    stu_id          varchar(64)                  null comment '证件号码',
    stu_school      text                         null comment '院校',
    levels          varchar(32) default '本科'   null comment '层次',
    major           varchar(128)                 null comment '专业',
    shool_id        varchar(32)                  null,
    enrollment_time date                         null comment '入学时间',
    status          varchar(32)                  null comment '学籍状态',
    form            varchar(32) default '全日制' null comment '形式'
);

create table stu_selected_coures
(
    id         int auto_increment comment '自增主键'
        primary key,
    student_id int           null,
    course_id  int           null comment '课程id',
    status     int default 0 null comment '状态',
    constraint stu_selected_coures_pk
        unique (student_id, course_id)
)
    comment '选课对应课程';

create table student_class
(
    student_id int null comment '学生id',
    class_id   int null comment '班级id',
    constraint student_class_pk
        unique (student_id)
)
    comment '学生班级表';

create table teacher_class
(
    id         int auto_increment
        primary key,
    teacher_id int null,
    class_id   int null,
    constraint teacher_class_pk
        unique (teacher_id, class_id)
);

create table teacher_course
(
    id         int auto_increment comment '自增主键'
        primary key,
    teacher_id int null comment '教师id',
    course_id  int null comment '课程id'
);

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
    class_id    int         default -1                  null comment '班级id 用于绑定班级',
    constraint user_uuid
        unique (user_no)
);

create index user__name
    on user (name);

