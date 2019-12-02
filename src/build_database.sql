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
alter table users
    add foreign key (id_stat) references statistics (id_stat);
INSERT INTO statistics (won_matches, lost_matches, won_sets, lost_sets, won_games, lost_games)
VALUES (0, 0, 0, 0, 0, 0);
insert into users(id_stat, name, username, password, surname, birth, sex, nationality, is_admin, is_left_handed)
VALUES (1, NULL, 'admin', '$2a$10$BHOa/UosYva6e.Mnff0GdOdsUonuFYFatfhv0XHEeY8osP7d8VFO2', NULL, NULL, NULL, NULL, true,
        NULL);

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


-------------------------------- TRIGGERS & FUNCTIONS --------------------------------------
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
    INSERT INTO statistics (won_matches, lost_matches, won_sets, lost_sets, won_games, lost_games)
    VALUES (0, 0, 0, 0, 0, 0)
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

create or replace function generate_statistics_teams() returns trigger AS
$$
DECLARE
    id INTEGER;
BEGIN
    INSERT INTO statistics (won_matches, lost_matches, won_sets, lost_sets, won_games, lost_games)
    VALUES (0, 0, 0, 0, 0, 0)
    RETURNING id_stat INTO id;
    UPDATE teams SET id_stat = id WHERE id_team = new.id_team;
    RETURN new;
END
$$
    language plpgsql;

drop trigger if exists trigger_generate_statistics on teams;

CREATE TRIGGER trigger_generate_statistics
    AFTER INSERT
    ON teams
    FOR EACH ROW
EXECUTE PROCEDURE generate_statistics_teams();

create or replace function update_statistics() returns trigger AS
$$
DECLARE
    id_tour                INTEGER;
    id_home                INTEGER;
    id_away                INTEGER;
    id_player_match_first  INTEGER;
    id_player_match_actual INTEGER;
    sets_home              INTEGER;
    sets_away              INTEGER;
    games_home             INTEGER;
    games_away             INTEGER;
    won_home               INTEGER;
    lost_home              INTEGER;
    cap                    INTEGER;
    match_index            INTEGER;
    total_matches          INTEGER;
    act_col_off            INTEGER;
    nex_coll_off           INTEGER;
    final_boss             INTEGER;
    is_home_null           BOOLEAN;
BEGIN
    SELECT old.id_tournament,
           COALESCE(old.id_user_home, new.id_user_home),
           COALESCE(old.id_user_away, new.id_user_away),
           old.id_player_match
    INTO id_tour, id_home, id_away, id_player_match_actual;

    IF (COALESCE(id_home, id_away) IS NULL) THEN
        raise notice 'dont have id';
        RETURN new;
    END IF;

    IF (new.games_away IS NULL) THEN
        RETURN new;
    END IF;

    SELECT coalesce(new.games_away, 0) - coalesce(old.games_away, 0),
           coalesce(new.games_home, 0) - coalesce(old.games_home, 0),
           coalesce(new.sets_away, 0) - coalesce(old.sets_away, 0),
           coalesce(new.sets_home, 0) - coalesce(old.sets_home, 0)
    INTO games_away, games_home, sets_away, sets_home;

    IF (sets_home >= sets_away) THEN
        SELECT 1, 0 INTO won_home, lost_home;
    ELSE
        SELECT 1, 0 INTO lost_home, won_home;
    END IF;

    raise notice '%, %, %, %', games_away, games_home, sets_away, sets_home;
    UPDATE statistics
    SET won_games    = won_games + games_home,
        won_sets     = won_sets + sets_home,
        lost_games   = lost_games + games_away,
        lost_sets    = lost_sets + sets_away,
        won_matches  = won_matches + won_home,
        lost_matches = lost_matches + lost_home
    WHERE id_stat = (SELECT id_stat FROM users WHERE id_user = id_home);

    UPDATE statistics
    SET won_games    = won_games + games_away,
        won_sets     = won_sets + sets_away,
        lost_games   = lost_games + games_home,
        lost_sets    = lost_sets + sets_home,
        won_matches  = won_matches + lost_home,
        lost_matches = lost_matches + won_home
    WHERE id_stat = (SELECT id_stat FROM users WHERE id_user = id_away);


    -- final boss -> update next match in knockout
    SELECT capacity FROM tournaments WHERE id_tournament = id_tour INTO cap;

    select id_player_match
    from player_matches
    where id_tournament = id_tour
    ORDER BY id_player_match
    LIMIT 1
    INTO id_player_match_first;

    IF (id_player_match_first = cap - 1) THEN
        return new;
    END IF;

    SELECT id_player_match_actual - id_player_match_first + 1 INTO match_index;
    raise notice 'match index %',match_index;
    raise notice 'capacity %',cap;
    raise notice 'id_player_match_first %',id_player_match_first;

    total_matches:=0;
    LOOP
        EXIT WHEN cap = 1;
        cap := cap / 2;
        total_matches := total_matches + cap;
        raise notice 'capacity %',cap;
        raise notice 'total_matches %',total_matches;
        IF (total_matches >= match_index) THEN
            act_col_off := total_matches - match_index;
            nex_coll_off := ((cap - act_col_off + 1) / 2);
            final_boss := match_index + act_col_off + nex_coll_off + id_player_match_first - 1;
            raise notice 'act %, next %',act_col_off, nex_coll_off;
            EXIT;
        end if;
    end loop;
    raise notice 'final boss %',final_boss;

    IF (won_home = 1) THEN
        SELECT id_user_home IS NULL FROM player_matches WHERE id_player_match = final_boss INTO is_home_null;
        IF (is_home_null) THEN
            UPDATE player_matches SET id_user_home = id_home WHERE id_player_match = final_boss;
        ELSE
            UPDATE player_matches SET id_user_away = id_home WHERE id_player_match = final_boss;
        end if;
    ELSE
        SELECT id_user_home IS NULL FROM player_matches WHERE id_player_match = final_boss INTO is_home_null;
        IF (is_home_null) THEN
            UPDATE player_matches SET id_user_home = id_away WHERE id_player_match = final_boss;
        ELSE
            UPDATE player_matches SET id_user_away = id_away WHERE id_player_match = final_boss;
        end if;
    END IF;

    return new;
END
$$
    language plpgsql;

drop trigger if exists trigger_update_statistics on player_matches;

CREATE TRIGGER trigger_update_statistics
    AFTER UPDATE
    ON player_matches
    FOR EACH ROW
EXECUTE PROCEDURE update_statistics();


create or replace function update_statistics_team() returns trigger AS
$$
DECLARE
    id_tour                INTEGER;
    id_home                INTEGER;
    id_away                INTEGER;
    id_team_match_first    INTEGER;
    id_team_match_actual   INTEGER;
    sets_home              INTEGER;
    sets_away              INTEGER;
    games_home             INTEGER;
    games_away             INTEGER;
    won_home               INTEGER;
    lost_home              INTEGER;
    cap                    INTEGER;
    match_index            INTEGER;
    total_matches          INTEGER;
    act_col_off            INTEGER;
    nex_coll_off           INTEGER;
    final_boss             INTEGER;
    is_home_null           BOOLEAN;
BEGIN
    SELECT old.id_tournament,
           COALESCE(old.id_team_home, new.id_team_home),
           COALESCE(old.id_team_away, new.id_team_away),
           old.id_team_match
    INTO id_tour, id_home, id_away, id_team_match_actual;

    IF (COALESCE(id_home, id_away) IS NULL) THEN
        raise notice 'dont have id';
        RETURN new;
    END IF;

    IF (new.games_away IS NULL) THEN
        RETURN new;
    END IF;

    SELECT coalesce(new.games_away, 0) - coalesce(old.games_away, 0),
           coalesce(new.games_home, 0) - coalesce(old.games_home, 0),
           coalesce(new.sets_away, 0) - coalesce(old.sets_away, 0),
           coalesce(new.sets_home, 0) - coalesce(old.sets_home, 0)
    INTO games_away, games_home, sets_away, sets_home;

    IF (sets_home >= sets_away) THEN
        SELECT 1, 0 INTO won_home, lost_home;
    ELSE
        SELECT 1, 0 INTO lost_home, won_home;
    END IF;

    raise notice '%, %, %, %', games_away, games_home, sets_away, sets_home;
    UPDATE statistics
    SET won_games    = won_games + games_home,
        won_sets     = won_sets + sets_home,
        lost_games   = lost_games + games_away,
        lost_sets    = lost_sets + sets_away,
        won_matches  = won_matches + won_home,
        lost_matches = lost_matches + lost_home
    WHERE id_stat = (SELECT id_stat FROM teams WHERE id_team = id_home);

    UPDATE statistics
    SET won_games  = won_games + games_away,
        won_sets   = won_sets + sets_away,
        lost_games = lost_games + games_home,
        lost_sets  = lost_sets + sets_home,
        won_matches = won_matches + lost_home,
        lost_matches = lost_matches + won_home
    WHERE id_stat = (SELECT id_stat FROM teams WHERE id_team = id_away);


    -- final boss -> update next match in knockout
    SELECT capacity FROM tournaments WHERE id_tournament = id_tour INTO cap;

    select id_team_match
    from team_matches
    where id_tournament = id_tour
    ORDER BY id_team_match
    LIMIT 1
    INTO id_team_match_first;

    IF (id_team_match_first = cap - 1) THEN
        return new;
    END IF;

    SELECT id_team_match_actual - id_team_match_first + 1 INTO match_index;
    raise notice 'match index %',match_index;
    raise notice 'capacity %',cap;
    raise notice 'id_team_match_first %', id_team_match_first;

    total_matches:=0;
    LOOP
        EXIT WHEN cap = 1;
        cap := cap / 2;
        total_matches := total_matches + cap;
        raise notice 'capacity %',cap;
        raise notice 'total_matches %',total_matches;
        IF (total_matches >= match_index) THEN
            act_col_off := total_matches - match_index;
            nex_coll_off := ((cap - act_col_off + 1) / 2);
            final_boss := match_index + act_col_off + nex_coll_off + id_team_match_first - 1;
            raise notice 'act %, next %',act_col_off, nex_coll_off;
            EXIT;
        end if;
    end loop;
    raise notice 'final boss %',final_boss;

    IF (won_home = 1) THEN
        SELECT id_team_home IS NULL FROM team_matches WHERE id_team_match = final_boss INTO is_home_null;
        IF (is_home_null) THEN
            UPDATE team_matches SET id_team_home = id_home WHERE id_team_match = final_boss;
        ELSE
            UPDATE team_matches SET id_team_away = id_home WHERE id_team_match = final_boss;
        end if;
    ELSE
        SELECT id_team_home IS NULL FROM team_matches WHERE id_team_match = final_boss INTO is_home_null;
        IF (is_home_null) THEN
            UPDATE team_matches SET id_team_home = id_away WHERE id_team_match = final_boss;
        ELSE
            UPDATE team_matches SET id_team_away = id_away WHERE id_team_match = final_boss;
        end if;
    END IF;

    return new;
END
$$
    language plpgsql;

drop trigger if exists trigger_update_statistics on team_matches;

CREATE TRIGGER trigger_update_statistics
    AFTER UPDATE
    ON team_matches
    FOR EACH ROW
EXECUTE PROCEDURE update_statistics_team();


create or replace function update_player_matches() returns trigger AS
$$
DECLARE
    is_confirmed  BOOLEAN;
    id_tournamen INTEGER;
    id_free_away  INTEGER;
    id_free_both  INTEGER;
    id_user INTEGER;

BEGIN
    SELECT new.is_confirmed, old.id_tournament, old.id_player INTO is_confirmed, id_tournamen, id_user;

    IF (COALESCE(is_confirmed, FALSE) = FALSE) THEN
        raise notice 'This was not confirmation update';
        RETURN new;
    END IF;

    SELECT id_player_match
    FROM player_matches
    WHERE COALESCE(id_user_home, id_user_away) IS NULL
    AND player_matches.id_tournament = id_tournamen
    ORDER BY 1
    LIMIT 1
    INTO id_free_both;
    raise notice 'id free both %', id_free_both;

    SELECT id_player_match
    FROM player_matches
    WHERE id_user_away IS NULL
      AND id_user_home IS NOT NULL
      AND player_matches.id_tournament = id_tournamen
    ORDER BY 1
    LIMIT 1
    INTO id_free_away;
    raise notice 'id free both %', id_free_away;


    IF (id_free_both <= id_free_away) THEN
        UPDATE player_matches SET id_user_home = id_user WHERE id_player_match = id_free_both;
        return new;
    ELSE IF (id_free_both > id_free_away) THEN
        UPDATE player_matches SET id_user_away = id_user WHERE id_player_match = id_free_away;
        return new;
        END IF;
    end if;

    IF (id_free_both IS NOT NULL AND id_free_away IS NULL) THEN
        UPDATE player_matches SET id_user_home = id_user WHERE id_player_match = id_free_both;
    ELSE IF (id_free_both IS NULL AND id_free_away IS NOT NULL) THEN
        UPDATE player_matches SET id_user_away = id_user WHERE id_player_match = id_free_away;
        END IF;
    end if;

    return new;
END
$$
    language plpgsql;

drop trigger if exists trigger_update_player_matches on player_tournament;

CREATE TRIGGER trigger_update_player_matches
    AFTER UPDATE
    ON player_tournament
    FOR EACH ROW
EXECUTE PROCEDURE update_player_matches();


create or replace function update_team_matches() returns trigger AS
$$
DECLARE
    is_confirmed  BOOLEAN;
    id_tournamen INTEGER;
    id_free_away  INTEGER;
    id_free_both  INTEGER;
    id_team INTEGER;

BEGIN
    SELECT new.is_confirmed, old.id_tournament, old.id_team INTO is_confirmed, id_tournamen, id_team;

    IF (COALESCE(is_confirmed, FALSE) = FALSE) THEN
        raise notice 'This was not confirmation update';
        RETURN new;
    END IF;

    SELECT id_team_match
    FROM team_matches
    WHERE COALESCE(id_team_home, id_team_away) IS NULL
    AND team_matches.id_tournament = id_tournamen
    ORDER BY 1
    LIMIT 1
    INTO id_free_both;
    raise notice 'id free both %', id_free_both;

    SELECT id_team_match
    FROM team_matches
    WHERE id_team_away IS NULL
        AND id_team_home IS NOT NULL
        AND team_matches.id_tournament = id_tournamen
    ORDER BY 1
    LIMIT 1
    INTO id_free_away;
    raise notice 'id free both %', id_free_away;


    IF (id_free_both <= id_free_away) THEN
        UPDATE team_matches SET id_team_home = id_team WHERE id_team_match = id_free_both;
        return new;
    ELSE IF (id_free_both > id_free_away) THEN
        UPDATE team_matches SET id_team_away = id_team WHERE id_team_match = id_free_away;
        return new;
        END IF;
    end if;

    IF (id_free_both IS NOT NULL AND id_free_away IS NULL) THEN
        UPDATE team_matches SET id_team_home = id_team WHERE id_team_match = id_free_both;
    ELSE IF (id_free_both IS NULL AND id_free_away IS NOT NULL) THEN
        UPDATE team_matches SET id_team_away = id_team WHERE id_team_match = id_free_away;
        END IF;
    end if;

    return new;
END
$$
    language plpgsql;

drop trigger if exists trigger_update_team_matches on team_tournament;

CREATE TRIGGER trigger_update_team_matches
    AFTER UPDATE
    ON team_tournament
    FOR EACH ROW
EXECUTE PROCEDURE update_team_matches();
