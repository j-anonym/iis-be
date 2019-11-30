--blah blah blah

--types
create type user_type as ENUM ('P','R','S');
CREATE TYPE player_type as ENUM ('M','W','MW');

drop table if exists tournaments;
create table tournaments
(
    id_tournament SERIAL PRIMARY KEY,
    prize         integer,
    name          text,
    date_from     date,
    date_to       date,
    place         text,
    occupation    integer,
    cost          integer,
    capacity      integer,
    is_singles    bool,
    type          player_type,
    sponsors      text
);

drop table if exists teams;
create table teams
(
    id_team SERIAL PRIMARY KEY,
    name    text,
    logo    text
);

drop table if exists team_matches;
create table team_matches
(
    id_team_match SERIAL PRIMARY KEY,
    sets_home     int,
    sets_away     int,
    games_home    int,
    games_away    int
);

drop table if exists player_matches;
create table player_matches
(
    id_player_match SERIAL PRIMARY KEY,
    sets_home       int,
    sets_away       int,
    games_home      int,
    games_away      int
);

drop table if exists statistics;
create table statistics
(
    id_stat      SERIAL PRIMARY KEY,
    won_matches  int,
    lost_matches int,
    won_sets     int,
    lost_sets    int,
    won_games    int,
    lost_games   int
);

drop table if exists users;
create table users
(
    id_user        SERIAL PRIMARY KEY,
    password       varchar(255),
    username       varchar(255) UNIQUE,
    id_stat        int,
    name           text,
    surname        text,
    birth          date,
    sex            player_type,
    nationality    text,
    is_admin       bool not null default false,
    is_left_handed bool
);
alter table users add foreign key (id_stat) references statistics(id_stat);
INSERT INTO statistics (won_matches, lost_matches, won_sets, lost_sets, won_games, lost_games) VALUES (0,0,0,0,0,0);
insert into users(id_stat, name, username, password, surname, birth, sex, nationality, is_admin, is_left_handed)
VALUES (1, NULL, 'admin', '$2a$10$BHOa/UosYva6e.Mnff0GdOdsUonuFYFatfhv0XHEeY8osP7d8VFO2', NULL, NULL, NULL, NULL, true, NULL);

alter table teams
    add column id_stat int;
alter table teams
    add foreign key (id_stat) references statistics (id_stat);


alter table player_matches
    add column id_user_home int;
alter table player_matches
    add column id_user_away int;

alter table player_matches
    add foreign key (id_user_home) references users (id_user) ON DELETE CASCADE;
alter table player_matches
    add foreign key (id_user_away) references users (id_user) ON DELETE CASCADE;


alter table team_matches
    add column id_team_home int;
alter table team_matches
    add column id_team_away int;


alter table team_matches
    add foreign key (id_team_home) references teams (id_team) ON DELETE CASCADE;
alter table team_matches
    add foreign key (id_team_away) references teams (id_team) ON DELETE CASCADE;


alter table team_matches
    add column id_referee int;
alter table player_matches
    add column id_referee int;

alter table team_matches
    add foreign key (id_referee) references users (id_user) ON DELETE SET NULL;
alter table player_matches
    add foreign key (id_referee) references users (id_user) ON DELETE SET NULL;


alter table team_matches
    add column id_tournament int;
alter table player_matches
    add column id_tournament int;

alter table team_matches
    add foreign key (id_tournament) references tournaments (id_tournament) ON DELETE CASCADE;
alter table player_matches
    add foreign key (id_tournament) references tournaments (id_tournament) ON DELETE CASCADE;


alter table teams
    add column id_player_1 int;
alter table teams
    add column id_player_2 int;

alter table teams
    add foreign key (id_player_1) references users (id_user) ON DELETE SET NULL;
alter table teams
    add foreign key (id_player_2) references users (id_user) ON DELETE SET NULL;


alter table tournaments
    add column id_staff int;

alter table tournaments
    add foreign key (id_staff) references users (id_user) ON DELETE SET NULL;


create table team_tournament
(
    id_team       int,
    id_tournament int,
    is_confirmed  bool
);

alter table team_tournament
    ADD FOREIGN KEY (id_tournament) REFERENCES tournaments (id_tournament) ON DELETE CASCADE;
alter table team_tournament
    ADD FOREIGN KEY (id_team) REFERENCES teams (id_team) ON DELETE CASCADE;

create table player_tournament
(
    id_player     int,
    type          user_type,
    id_tournament int,
    is_confirmed  bool
);

alter table player_tournament
    ADD FOREIGN KEY (id_tournament) REFERENCES tournaments (id_tournament) ON DELETE CASCADE;
alter table player_tournament
    ADD FOREIGN KEY (id_player) REFERENCES users (id_user) ON DELETE CASCADE;

create or replace function update_occupation() returns trigger AS
$$
BEGIN
    IF (NEW.is_confirmed = TRUE) AND (NEW.type = 'P') THEN
        UPDATE tournaments
        SET occupation = occupation + 1
        WHERE id_tournament = NEW.id_tournament;
    END IF;

    RETURN NEW;
END
$$
    language plpgsql;

create or replace function update_occupation_teams() returns trigger AS
$$
BEGIN
    IF (NEW.is_confirmed = TRUE) THEN
        UPDATE tournaments
        SET occupation = occupation + 1
        WHERE id_tournament = NEW.id_tournament;
    END IF;

    RETURN NEW;
END
$$
    language plpgsql;


drop trigger if exists trigger_update_occupation on player_tournament;

CREATE TRIGGER trigger_update_occupation
    AFTER UPDATE
    ON player_tournament
    FOR EACH ROW
EXECUTE PROCEDURE update_occupation();

drop trigger if exists trigger_update_occupation on team_tournament;

CREATE TRIGGER trigger_update_occupation
    AFTER UPDATE
    ON team_tournament
    FOR EACH ROW
EXECUTE PROCEDURE update_occupation_teams();

create or replace function generate_matches() returns trigger AS
$$
DECLARE
    cap     INTEGER;
    counter INTEGER := 0;

BEGIN
    SELECT new.capacity into cap;

    LOOP
        --count number of matches
        cap = cap / 2;
        counter := counter + cap;
        EXIT WHEN cap = 1;
    END LOOP;

    LOOP
        EXIT WHEN counter = 0;
        IF (new.is_singles) THEN
            INSERT INTO player_matches(id_tournament) VALUES (new.id_tournament);
        ELSE
            INSERT INTO team_matches(id_tournament) VALUES (new.id_tournament);
        END IF;
        SELECT counter - 1 INTO counter;
    END LOOP;

    RETURN NEW;
END
$$
    language plpgsql;


drop trigger if exists trigger_generate_matches on tournaments;

CREATE TRIGGER trigger_generate_matches
    AFTER INSERT
    ON tournaments
    FOR EACH ROW
EXECUTE PROCEDURE generate_matches();


create or replace function generate_statistics() returns trigger AS
$$
DECLARE
    id INTEGER;
BEGIN
    INSERT INTO statistics (won_matches, lost_matches, won_sets, lost_sets, won_games, lost_games) VALUES (0,0,0,0,0,0)
    RETURNING id_stat INTO id;
    UPDATE users SET id_stat = id WHERE id_user = new.id_user;
    RETURN new;
END
$$
    language plpgsql;


drop trigger if exists trigger_generate_statistics on users;

CREATE TRIGGER trigger_generate_statistics
    AFTER INSERT
    ON users
    FOR EACH ROW
EXECUTE PROCEDURE generate_statistics();

