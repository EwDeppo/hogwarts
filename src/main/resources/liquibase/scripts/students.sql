-- liquibase formatted sql

-- changeset igoncharov:1
CREATE INDEX student_name_index ON students (name);

-- changeset igoncharov:2
CREATE INDEX faculty_name_color_index ON faculties (name, color);


