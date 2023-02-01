create type gender as enum ('MALE', 'FEMALE', 'HIDDEN');

create table "user"
(
    id            uuid default gen_random_uuid() not null
        constraint user_pk
            primary key,
    nick          varchar                        not null
        constraint user_uk
            unique,
    name          varchar                        not null,
    date_of_birth date                           not null,
    gender        gender                         not null,
    latitude      float                          not null,
    longitude     float                          not null
);

INSERT INTO public."user" (id, nick, name, date_of_birth, gender, latitude, longitude) VALUES (DEFAULT, 'ivegotsauce', 'Alexander Sharipov', '2002-08-09', 'MALE'::gender, 59.97237, 30.30198);
INSERT INTO public."user" (id, nick, name, date_of_birth, gender, latitude, longitude) VALUES (DEFAULT, 'durov', 'Pavel Durov', '1984-10-10', 'MALE'::gender, 25.0657, 55.1713);