select student.name, student.age, faculty.name from student inner join student on student.faculty = faculty.name;
select student.name, student.age from student inner join student on student.avatar;