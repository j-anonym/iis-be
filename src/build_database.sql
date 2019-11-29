--blah blah blah

--types
create type user_type as ENUM ('P','R','S');
CREATE TYPE player_type as ENUM ('M','W','MW');

drop table if exists tournaments;
create table tournaments(
                            id_tournament SERIAL PRIMARY KEY,
                            prize integer,
                            name text,
                            date_from date,
                            date_to date,
                            place text,
                            occupation integer,
                            cost integer,
                            capacity integer,
                            is_singles bool,
                            type player_type,
                            sponsors text
);

drop table if exists teams;
create table teams(
                      id_team SERIAL PRIMARY KEY,
                      name text,
                      logo text
);

drop table if exists team_matches;
create table team_matches(
                             id_team_match SERIAL PRIMARY KEY,
                             sets_home int,
                             sets_away int,
                             games_home int,
                             games_away int
);

drop table if exists player_matches;
create table player_matches(
                               id_player_match SERIAL PRIMARY KEY,
                               sets_home int,
                               sets_away int,
                               games_home int,
                               games_away int
);

drop table if exists statistics;
create table statistics(
                           id_stat SERIAL PRIMARY KEY,
                           won_matches int,
                           lost_matches int,
                           won_sets int,
                           lost_sets int,
                           won_games int,
                           lost_games int
);

drop table if exists users;
create table users (
    id_user       SERIAL PRIMARY KEY,
    password varchar(255),
    username varchar(255),
    id_stat     int,
    name        text,
    surname     text,
    birth       date,
    sex         player_type,
    nationality text,
    is_admin bool not null default false,
    is_left_handed bool
);
--alter table users add foreign key (id_stat) references statistics(id_stat);
insert into users(id_stat, name, surname, birth, sex, nationality, is_admin, is_left_handed) VALUES (
                          0, 'admin', NULL, NULL, NULL, NULL, true, NULL
                         );
--alter table users add foreign key (id_stat) references statistics(id_stat);

alter table teams add column id_stat int;
alter table teams add foreign key (id_stat) references statistics(id_stat);


alter table player_matches add column id_user_home int;
alter table player_matches add column id_user_away int;

alter table player_matches add foreign key (id_user_home) references users(id_user);
alter table player_matches add foreign key (id_user_away) references users(id_user);


alter table team_matches add column id_team_home int;
alter table team_matches add column id_team_away int;


alter table team_matches add foreign key (id_team_home) references teams(id_team);
alter table team_matches add foreign key (id_team_away) references teams(id_team);


alter table team_matches add column id_referee int;
alter table player_matches add column id_referee int;

alter table team_matches add foreign key (id_referee) references users(id_user);
alter table player_matches add foreign key (id_referee) references users(id_user);


alter table team_matches add column id_tournament int;
alter table player_matches add column id_tournament int;

alter table team_matches add foreign key (id_tournament) references tournaments(id_tournament);
alter table player_matches add foreign key (id_tournament) references tournaments(id_tournament);


alter table teams add column id_player_1 int;
alter table teams add column id_player_2 int;

alter table teams add foreign key (id_player_1) references users(id_user);
alter table teams add foreign key (id_player_2) references users(id_user);


alter table tournaments add column id_staff int;

alter table tournaments add foreign key (id_staff) references users(id_user);


create table team_tournament(
                                id_team int,
                                id_tournament int,
                                is_confirmed bool
);

alter table team_tournament ADD FOREIGN KEY (id_tournament) REFERENCES tournaments(id_tournament);
alter table team_tournament ADD FOREIGN KEY (id_team) REFERENCES teams(id_team);

create table player_tournament(
    id_player int,
    type user_type,
    id_tournament int,
    is_confirmed bool
);

alter table player_tournament ADD FOREIGN KEY (id_tournament) REFERENCES tournaments(id_tournament);
alter table player_tournament ADD FOREIGN KEY (id_player) REFERENCES users(id_user);

