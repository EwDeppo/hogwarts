alter table student (age INTEGER check (age > 16);
alter table student (name text NOT NULL);
alter table student (name text UNIQUE);
alter table faculty (name text UNIQUE);
alter table faculty (color text UNIQUE);
alter table student (age text default 20);