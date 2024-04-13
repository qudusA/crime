create table if not exists audit_trail_entity
(
    log_id    bigserial
        primary key,
    action    varchar(255),
    details   varchar(255),
    timestamp timestamp(6),
    user_name varchar(255)
);

alter table audit_trail_entity
    owner to postgres;

create table if not exists complain_entity
(
    id              bigserial
        primary key,
    "created-by"    bigint       not null,
    "created-date"  timestamp(6) not null,
    crime_type      varchar(255),
    description     varchar(255),
    is_addressed    boolean,
    location        varchar(255),
    "modified-by"   bigint,
    "modified-date" timestamp(6)
);

alter table complain_entity
    owner to postgres;

create table if not exists case_entity
(
    id           bigserial
        primary key,
    case_status  varchar(255),
    created_at   timestamp(6) not null,
    created_by   bigint,
    type_of_case varchar(255) not null,
    crime_id     bigint
        constraint uk_af30adic5ynxhtvksvjo00nkr
            unique
        constraint fk61j90sert7bqxeu4rk9wqg06m
            references complain_entity
);

alter table case_entity
    owner to postgres;

create table if not exists list_of_court_house_entity
(
    id                     bigserial
        primary key,
    court_house_name       varchar(255)
        constraint uk_i61bkfv2kw2nxuw42oslfjesp
            unique,
    latitude               varchar(255),
    location               varchar(255),
    longitude              varchar(255),
    numbers_of_court_rooms integer not null
);

alter table list_of_court_house_entity
    owner to postgres;

create table if not exists list_of_court_rooms_entity
(
    room_id     bigserial
        primary key,
    room_number varchar(255)
        constraint uk_o1rojiq53cpmbakwsma3ql70t
            unique
);

alter table list_of_court_rooms_entity
    owner to postgres;

create table if not exists list_of_police_department_entity
(
    id         bigserial
        primary key,
    department varchar(255)
);

alter table list_of_police_department_entity
    owner to postgres;

create table if not exists list_of_prison_department_entity
(
    id         bigserial
        primary key,
    department varchar(255)
);

alter table list_of_prison_department_entity
    owner to postgres;

create table if not exists list_of_prison_facility
(
    id                             bigserial
        primary key,
    prison_current_inmate_capacity integer,
    prison_current_staff_capacity  integer,
    inmate_max_capacity            integer not null,
    latitude                       varchar(255),
    location                       varchar(255),
    longitude                      varchar(255),
    prison_facility_name           varchar(255)
        constraint uk_6ksghhwb2ug5gjuq32kmrod43
            unique,
    prison_max_staff_capacity      integer
);

alter table list_of_prison_facility
    owner to postgres;

create table if not exists open_case_on_match
(
    id       bigserial
        primary key,
    to_match varchar(255)
);

alter table open_case_on_match
    owner to postgres;

create table if not exists police_ranks_entity
(
    id   bigserial
        primary key,
    rank varchar(255) not null
        constraint uk_7oay4nn49hpggibqsvwpfcn0g
            unique
);

alter table police_ranks_entity
    owner to postgres;

create table if not exists suspect_entity
(
    suspect_id    bigserial
        primary key,
    address       varchar(255),
    date_of_birth varchar(255) not null,
    first_name    varchar(255) not null,
    last_name     varchar(255) not null,
    phone_number  varchar(255) not null
);

alter table suspect_entity
    owner to postgres;

create table if not exists suspect_case
(
    suspect_id bigint not null
        constraint fknnweqdoecjnmahrmm18o5jo89
            references suspect_entity,
    case_id    bigint not null
        constraint fk5sb3hs826fom0b794d84y6jgs
            references case_entity
);

alter table suspect_case
    owner to postgres;

create table if not exists user_entity
(
    id                 bigserial
        primary key,
    address            varchar(255),
    crated_at          timestamp(6) not null,
    date_of_birth      varchar(255) not null,
    email              varchar(255) not null
        constraint uk_4xad1enskw4j1t2866f7sodrx
            unique,
    first_name         varchar(255) not null,
    is_verified        boolean      not null,
    last_modified_date timestamp(6),
    last_name          varchar(255) not null,
    password           varchar(255) not null,
    phone_number       varchar(255) not null,
    role               varchar(255)
        constraint user_entity_role_check
            check ((role)::text = ANY
                   ((ARRAY ['USER'::character varying, 'ADMIN'::character varying, 'MANAGER'::character varying, 'LAW_ENFORCEMENT_OFFICER'::character varying, 'CLARK'::character varying, 'WARDEN'::character varying, 'JUDGE'::character varying])::text[]))
);

alter table user_entity
    owner to postgres;

create table if not exists crime_entity
(
    crime_id              bigserial
        primary key,
    created_at            timestamp(6) not null,
    crime_date            varchar(255),
    crime_description     varchar(255),
    crime_location        varchar(255),
    crime_type            varchar(255),
    image                 varchar(5000),
    status                varchar(255),
    updated_at            timestamp(6),
    video                 varchar(5000),
    investigating_officer bigint
        constraint fk7k9bc5bjdx1tq32a4ulyxxt0c
            references user_entity,
    constraint uko4v9coj6c1niqoodntehewko3
        unique (crime_date, crime_location, crime_type)
);

alter table crime_entity
    owner to postgres;

create table if not exists criminal_entity
(
    criminal_id            bigserial
        primary key,
    criminal_address       varchar(255),
    criminal_age           integer,
    contact_number         varchar(255),
    criminal_date_of_birth varchar(255),
    criminal_email         varchar(255)
        constraint uk_8cqo5k6adf1n0gu0jv7gk68xb
            unique,
    eye_color              varchar(255),
    criminal_first_name    varchar(255),
    criminal_gender        varchar(255),
    hair_color             varchar(255),
    height                 double precision,
    criminal_last_name     varchar(255),
    nationality            varchar(255),
    criminal_photograph    varchar(255),
    scars                  varchar(255),
    tattoos                varchar(255),
    weight                 double precision,
    crime_id               bigint not null
        constraint fkrsl7t8why2nhhpb68aqak0ly5
            references crime_entity
);

alter table criminal_entity
    owner to postgres;

create table if not exists head_of_prison_department_entity
(
    id                 bigserial
        primary key,
    department_id      bigint
        constraint fkqr9apb7wul6t3th2u170938ef
            references list_of_prison_department_entity,
    prison_facility_id bigint
        constraint fkhd2xf2q8k30cggeol1ciw9j22
            references list_of_prison_facility,
    user_id            bigint
        constraint uk_50hp3rstl3k58ueuyw4vpm8m
            unique
        constraint fkrf6awjhkmpu9ayftxcsshumgl
            references user_entity
);

alter table head_of_prison_department_entity
    owner to postgres;

create table if not exists list_of_police_station_entity
(
    id                                    bigserial
        primary key,
    police_station_current_staff_capacity integer,
    latitude                              varchar(255),
    location                              varchar(255),
    longitude                             varchar(255),
    police_station_max_staff_capacity     integer not null,
    police_station_name                   varchar(255)
        constraint uk_5h6u05adaaaurchiwu9x0vtj2
            unique,
    head_id                               bigint
        constraint uk_igq2akea6l6gt7g8umlyxuj1y
            unique
        constraint fkec4enhgxfu3gaxe20upjus9pi
            references user_entity
);

alter table list_of_police_station_entity
    owner to postgres;

create table if not exists head_of_department_entity
(
    id                bigserial
        primary key,
    department_id     bigint
        constraint fk65f0qwtwy81y2kj3qb9yqtkvf
            references list_of_police_department_entity,
    police_station_id bigint
        constraint fkc2y9llwp6qvj097isu7d0rx19
            references list_of_police_station_entity,
    user_id           bigint
        constraint uk_jyjlaf5iv2fx7gxhon1ojvsj0
            unique
        constraint fk3ue44tybgy2q00q6nfpykkfjn
            references user_entity,
    constraint ukemn5hq0l29u5eh5j4dedpauax
        unique (department_id, police_station_id)
);

alter table head_of_department_entity
    owner to postgres;

create table if not exists token_entity
(
    id              bigserial
        primary key,
    expiration_time timestamp(6),
    is_expired      boolean not null,
    token           varchar(255),
    user_id         bigint  not null
        constraint fkchycpasyr16kt66k09e6ompve
            references user_entity
);

alter table token_entity
    owner to postgres;

create table if not exists warden_ranks_entity
(
    id   bigserial
        primary key,
    rank varchar(255)
        constraint uk_dhgk193jw0j237ugr21omxurb
            unique
);

alter table warden_ranks_entity
    owner to postgres;

create table if not exists police_warden_judge_entity
(
    id                   bigserial
        primary key,
    court_room_id        bigint
        constraint fk36hrx0l26i7q7se84tyfkc6bi
            references list_of_court_rooms_entity,
    department_id        bigint
        constraint fk49jb0yj690p4t20l4d6iyqtb
            references list_of_police_department_entity,
    facility_id          bigint
        constraint fkinnode9atsbmt4l7rbxd9slm4
            references list_of_prison_facility,
    court_house_id       bigint
        constraint fknak0tc53vn7wk49ohmw5c8oqr
            references list_of_court_house_entity,
    police_station_id    bigint
        constraint fkfhxs5opy4tkc0xlrjtvgdg995
            references list_of_police_station_entity,
    police_rank_id       bigint
        constraint fkmslejp7fscg7bsilvsbi91is
            references police_ranks_entity,
    prison_department_id bigint
        constraint fk6iolr2y9wkikhbugi0bquvey8
            references list_of_prison_department_entity,
    user_id              bigint
        constraint uk_6w5lhx6p5u1rvt57w1xw49ce6
            unique
        constraint fkkmoi6mdw99megmnudyjmnlwhp
            references user_entity,
    warden_rank_id       bigint
        constraint fkc9vlbwewj5qy2jqogq9tw58vc
            references warden_ranks_entity
);

alter table police_warden_judge_entity
    owner to postgres;

create table if not exists charged_case_entity
(
    charged_case_id bigserial
        primary key,
    case_id         bigint
        constraint uk_mc836h6hqcxrdak2uxvs83mph
            unique
        constraint fknq0fkqydp2ikqdfi0ko8yhygj
            references case_entity,
    court_house_id  bigint
        constraint uk_meq23da5vqy7spcca8rjl7les
            unique
        constraint fkabk7ii1j46uebunr49ssn62rh
            references list_of_court_house_entity,
    court_room_id   bigint
        constraint uk_a96ci9hdg9ifala7v8iverdov
            unique
        constraint fk8lhh5ohmc23pvmltkmv3luexi
            references police_warden_judge_entity
);

alter table charged_case_entity
    owner to postgres;

create table if not exists punishment_entity
(
    punishment_id          bigserial
        primary key,
    amount_paid            integer,
    "created-by"           bigint       not null,
    "created-date"         timestamp(6) not null,
    end_date               date,
    fine_amount            integer,
    "modified-by"          bigint,
    "modified-date"        timestamp(6),
    punishment_description varchar(255),
    punishment_status      varchar(255),
    punishment_type        varchar(255),
    start_date             date,
    "charged-case_id"      bigint
        constraint fkojxl1nvpmfq0qfq6fx3ny4rwy
            references charged_case_entity
);

alter table punishment_entity
    owner to postgres;

