
    create table certificates (
        course_id bigint,
        date datetime(6),
        id bigint not null auto_increment,
        user_id bigint,
        primary key (id)
    ) engine=InnoDB;

    create table courses (
        hours_per_week integer not null,
        iteration integer not null,
        end_date datetime(6) not null,
        id bigint not null auto_increment,
        instructor_id bigint not null,
        start_date datetime(6) not null,
        description varchar(255) not null,
        title varchar(255) not null,
        category enum ('CLOUD_DEVOPS','CYBER_SECURITY','WEB_DEV') not null,
        primary key (id)
    ) engine=InnoDB;

    create table enrollments (
        status tinyint not null check (status between 0 and 2),
        course_id bigint not null,
        enrollment_date datetime(6),
        id bigint not null auto_increment,
        user_id bigint not null,
        primary key (id)
    ) engine=InnoDB;

    create table ratings (
        stars integer not null check ((stars<=5) and (stars>=1)),
        course_id bigint not null,
        id bigint not null auto_increment,
        user_id bigint not null,
        comment varchar(300),
        primary key (id)
    ) engine=InnoDB;

    create table users (
        enabled bit not null,
        id bigint not null auto_increment,
        username varchar(20) not null,
        full_name varchar(255) not null,
        password varchar(255) not null,
        role enum ('ADMIN','INSTRUCTOR','STUDENT') not null,
        primary key (id)
    ) engine=InnoDB;

    alter table certificates 
       add constraint FKs5rftqrsgkog7h4piv3f7a9s6 
       foreign key (course_id) 
       references courses (id);

    alter table certificates 
       add constraint FKd3f6enpb3p3xovee9klklf05r 
       foreign key (user_id) 
       references users (id);

    alter table courses 
       add constraint FKcyfum8goa6q5u13uog0563gyp 
       foreign key (instructor_id) 
       references users (id);

    alter table enrollments 
       add constraint FKho8mcicp4196ebpltdn9wl6co 
       foreign key (course_id) 
       references courses (id);

    alter table enrollments 
       add constraint FK3hjx6rcnbmfw368sxigrpfpx0 
       foreign key (user_id) 
       references users (id);

    alter table ratings 
       add constraint FKbegfifgmkbhd1pj5vitred35g 
       foreign key (course_id) 
       references courses (id);

    alter table ratings 
       add constraint FKb3354ee2xxvdrbyq9f42jdayd 
       foreign key (user_id) 
       references users (id);
