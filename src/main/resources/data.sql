INSERT INTO teachers (id, name) VALUES (1, 'Sandra Klaver');
INSERT INTO teachers (id, name) VALUES (2, 'Manon Vos');
INSERT INTO teachers (id, name) VALUES (3, 'Nina Schipper');
INSERT INTO teachers (id, name) VALUES (4, 'Niels Brouwer');
INSERT INTO teachers (id, name) VALUES (5, 'Daniel van Dijk');
INSERT INTO teachers (id, name) VALUES (6, 'Martin Pieters');
INSERT INTO teachers (id, name) VALUES (7, 'Ad Ministrator');

SELECT setval(pg_get_serial_sequence('teachers','id'),coalesce(max(id),0)+1,false) FROM teachers;
-- ALTER SEQUENCE public.teachers_id_seq RESTART 100;


INSERT INTO students (id, gender, name, grade_year, class_name, mentor_id ) VALUES (1, 'M', 'Nathan Jansen', 1, '1A', 3);
INSERT INTO students (id, gender, name, grade_year, class_name, mentor_id ) VALUES (2, 'M', 'Lucas Bakker', 1, '1A', 3);
INSERT INTO students (id, gender, name, grade_year, class_name, mentor_id ) VALUES (3, 'M', 'Thomas Visser', 1, '1A', 3);
INSERT INTO students (id, gender, name, grade_year, class_name, mentor_id ) VALUES (4, 'V', 'Lisa Smit', 1, '1A', 3);
INSERT INTO students (id, gender, name, grade_year, class_name, mentor_id ) VALUES (5, 'V', 'Hanna Mulder', 1, '1A', 3);
INSERT INTO students (id, gender, name, grade_year, class_name, mentor_id ) VALUES (6, 'M', 'Mustafa El Dar', 1, '1B', 2);
INSERT INTO students (id, gender, name, grade_year, class_name, mentor_id ) VALUES (7, 'M', 'Mark Hendriks', 2, '2B', 2);
INSERT INTO students (id, gender, name, grade_year, class_name, mentor_id ) VALUES (8, 'V', 'Emma Dijkstra', 1, '1B', 2);
INSERT INTO students (id, gender, name, grade_year, class_name, mentor_id ) VALUES (9, 'V', 'Aisha Mulkar', 1, '1B', 2);
INSERT INTO students (id, gender, name, grade_year, class_name, mentor_id ) VALUES (10, 'V', 'Louise de Haan', 1, '1B', 2);
INSERT INTO students (id, gender, name, grade_year, class_name, mentor_id ) VALUES (11, 'V', 'Sarah Dekker', 1, '1B', 2);

SELECT setval(pg_get_serial_sequence('students','id'),coalesce(max(id),0)+1,false) FROM students;
-- ALTER SEQUENCE public.students_id_seq RESTART 100;

INSERT INTO workshops ( dt_start, dt_reservations_start, dt_reservations_end, duration, min_grade_year, max_grade_year, room, category, title, min_participants, max_participants, teacher_id, creator_id ) VALUES ( '2024-04-01 10:30', '2024-01-01', '2024-03-31', 60, 1, 6, 'auditorium', 'sociaal', 'Moppen tappen like a Pro', 8, 20, 6, 1 );
INSERT INTO workshops ( dt_start, dt_reservations_start, dt_reservations_end, duration, min_grade_year, max_grade_year, room, category, title, min_participants, max_participants, teacher_id, creator_id ) VALUES ( CURRENT_DATE + INTERVAL '14 DAYS 10 HOURS', CURRENT_DATE - INTERVAL '1 WEEK 12 HOURS', CURRENT_DATE + INTERVAL '10 DAYS 12 HOURS', 45, 1, 2, 'gymzaal', 'sport', 'Capoeira', 20, 50, 4, 1 );
INSERT INTO workshops ( dt_start, dt_reservations_start, dt_reservations_end, duration, min_grade_year, max_grade_year, room, category, title, min_participants, max_participants, teacher_id, creator_id ) VALUES ( CURRENT_DATE + INTERVAL '14 DAYS 10 HOURS', CURRENT_DATE - INTERVAL '1 WEEK 12 HOURS', CURRENT_DATE + INTERVAL '10 DAYS 12 HOURS', 45, 2, 2, 'grasveld', 'techniek', 'Drone vliegen', 2, 4, 4, 2 );
INSERT INTO workshops ( dt_start, dt_reservations_start, dt_reservations_end, duration, min_grade_year, max_grade_year, room, category, title, min_participants, max_participants, teacher_id, creator_id ) VALUES ( CURRENT_DATE + INTERVAL '14 DAYS 10 HOURS', CURRENT_DATE - INTERVAL '1 WEEK 12 HOURS', CURRENT_DATE + INTERVAL '10 DAYS 12 HOURS', 45, 1, 3, 'muzieklokaal', 'muziek', 'Didgeridoo spelen', 8, 16, 5, 2 );
INSERT INTO workshops ( dt_start, dt_reservations_start, dt_reservations_end, duration, min_grade_year, max_grade_year, room, category, title, min_participants, max_participants, teacher_id, creator_id ) VALUES ( CURRENT_DATE + INTERVAL '14 DAYS 10 HOURS 15 MINUTES', CURRENT_DATE - INTERVAL '1 WEEK 12 HOURS', CURRENT_DATE + INTERVAL '10 DAYS 12 HOURS', 90, 1, 6, 'kantine', 'culinair', 'Maak zelf de lekkerste quiche', 2, 8, 6, 2 );
INSERT INTO workshops ( dt_start, dt_reservations_start, dt_reservations_end, duration, min_grade_year, max_grade_year, room, category, title, min_participants, max_participants, teacher_id, creator_id ) VALUES ( CURRENT_DATE + INTERVAL '14 DAYS 10 HOURS', CURRENT_DATE - INTERVAL '1 WEEK 12 HOURS', CURRENT_DATE + INTERVAL '10 DAYS 12 HOURS', 45, 1, 5, 'schoolplein', 'muziek', 'Djembe spelen als een malle', 15, 35, 5, 2 );
INSERT INTO workshops ( dt_start, dt_reservations_start, dt_reservations_end, duration, min_grade_year, max_grade_year, room, category, title, min_participants, max_participants, teacher_id, creator_id ) VALUES ( CURRENT_DATE + INTERVAL '21 DAYS 10 HOURS', CURRENT_DATE - INTERVAL '1 WEEK 12 HOURS', CURRENT_DATE + INTERVAL '1 WEEK 12 HOURS', 45, 2, 6, 'technieklokaal', 'techniek', '3D printen', 6, 12, 6, 2 );
INSERT INTO workshops ( dt_start, dt_reservations_start, dt_reservations_end, duration, min_grade_year, max_grade_year, room, category, title, min_participants, max_participants, teacher_id, creator_id ) VALUES ( CURRENT_DATE + INTERVAL '21 DAYS 10 HOURS', CURRENT_DATE - INTERVAL '1 WEEK 12 HOURS', CURRENT_DATE + INTERVAL '2 WEEKS 12 HOURS', 45, 1, 6, 'buiten', 'sport', 'Freerunnen', 6, 12, 4, 2 );
INSERT INTO workshops ( dt_start, dt_reservations_start, dt_reservations_end, duration, min_grade_year, max_grade_year, room, category, title, min_participants, max_participants, teacher_id, creator_id ) VALUES ( CURRENT_DATE + INTERVAL '21 DAYS 10 HOURS', CURRENT_DATE - INTERVAL '1 WEEK 14 HOURS', CURRENT_DATE + INTERVAL '2 WEEKS 12 HOURS', 45, 1, 6, 'gymzaal', 'sport', 'Klimmuur', 6, 12, 4, 2 );
INSERT INTO workshops ( dt_start, dt_reservations_start, dt_reservations_end, duration, min_grade_year, max_grade_year, room, category, title, min_participants, max_participants, teacher_id, creator_id ) VALUES ( CURRENT_DATE + INTERVAL '21 DAYS 10 HOURS', CURRENT_DATE - INTERVAL '1 WEEK 12 HOURS', CURRENT_DATE + INTERVAL '2 WEEKS 12 HOURS', 45, 1, 3, 'muzieklokaal', 'ckv', 'Lipdub maken', 6, 12, 5, 2 );
INSERT INTO workshops ( dt_start, dt_reservations_start, dt_reservations_end, duration, min_grade_year, max_grade_year, room, category, title, min_participants, max_participants, teacher_id, creator_id ) VALUES ( CURRENT_DATE + INTERVAL '21 DAYS 10 HOURS', CURRENT_DATE - INTERVAL '1 WEEK 12 HOURS', CURRENT_DATE + INTERVAL '2 WEEKS 12 HOURS', 45, 1, 1, 'theaterzaal', 'ckv', 'Fotografie cursus', 6, 12, 5, 2 );
INSERT INTO workshops ( dt_start, dt_reservations_start, dt_reservations_end, duration, min_grade_year, max_grade_year, room, category, title, min_participants, max_participants, teacher_id, creator_id ) VALUES ( CURRENT_DATE + INTERVAL '21 DAYS 10 HOURS', CURRENT_DATE - INTERVAL '1 WEEK 12 HOURS', CURRENT_DATE + INTERVAL '2 WEEKS 12 HOURS', 45, 2, 2, 'theaterzaal', 'ckv', 'Video editing', 6, 12, 5, 2 );
INSERT INTO workshops ( dt_start, dt_reservations_start, dt_reservations_end, duration, min_grade_year, max_grade_year, room, category, title, min_participants, max_participants, teacher_id, creator_id ) VALUES ( CURRENT_DATE + INTERVAL '27 DAYS 10 HOURS', CURRENT_DATE - INTERVAL '1 WEEK 12 HOURS', CURRENT_DATE + INTERVAL '3 WEEKS 12 HOURS', 45, 1, 1, 'aula', 'speciaal', 'Alleen voor 1e leerjaar', 6, 12, 6, 2 );
INSERT INTO workshops ( dt_start, dt_reservations_start, dt_reservations_end, duration, min_grade_year, max_grade_year, room, category, title, min_participants, max_participants, teacher_id, creator_id ) VALUES ( CURRENT_DATE + INTERVAL '27 DAYS 10 HOURS', CURRENT_DATE - INTERVAL '1 WEEK 12 HOURS', CURRENT_DATE + INTERVAL '3 WEEKS 12 HOURS', 45, 2, 2, 'aula', 'speciaal', 'Alleen voor 2e leerjaar', 6, 12, 6, 2 );
INSERT INTO workshops ( dt_start, dt_reservations_start, dt_reservations_end, duration, min_grade_year, max_grade_year, room, category, title, min_participants, max_participants, teacher_id, creator_id ) VALUES ( CURRENT_DATE + INTERVAL '27 DAYS 10 HOURS', CURRENT_DATE - INTERVAL '1 WEEK 12 HOURS', CURRENT_DATE + INTERVAL '3 WEEKS 12 HOURS', 45, 6, 6, 'aula', 'speciaal', 'Alleen voor 6e leerjaar', 6, 12, 6, 2 );
INSERT INTO workshops ( dt_start, dt_reservations_start, dt_reservations_end, duration, min_grade_year, max_grade_year, room, category, title, min_participants, max_participants, teacher_id, creator_id ) VALUES ( CURRENT_DATE + INTERVAL '27 DAYS 10 HOURS', CURRENT_DATE + INTERVAL '1 WEEK 12 HOURS', CURRENT_DATE + INTERVAL '25 DAY 12 HOURS' , 45, 1, 3, 'aula', 'speciaal', 'Voor hele onderbouw 1 t/m 3', 6, 12, 6, 2 );




INSERT INTO users ( enabled, username, email, password, mentor_id, student_id ) VALUES ( true, 'admin', 'admin@school.nl', '$2a$12$d6A5s4jUnNg3YI20mJ.RmeO6PNFhuyTvx5yUToRPhosL3QVowTRYy', null, null);
INSERT INTO users ( enabled, username, email, password, mentor_id, student_id ) VALUES ( true, 'planner', 'planner@school.nl', '$2a$12$d6A5s4jUnNg3YI20mJ.RmeO6PNFhuyTvx5yUToRPhosL3QVowTRYy', 1, null);
INSERT INTO users ( enabled, username, email, password, mentor_id, student_id ) VALUES ( true, 'mentor1', 'mentor1@school.nl', '$2a$12$d6A5s4jUnNg3YI20mJ.RmeO6PNFhuyTvx5yUToRPhosL3QVowTRYy', 2, null);
INSERT INTO users ( enabled, username, email, password, mentor_id, student_id ) VALUES ( true, 'mentor2', 'mentor2@school.nl', '$2a$12$d6A5s4jUnNg3YI20mJ.RmeO6PNFhuyTvx5yUToRPhosL3QVowTRYy', 3, null);
INSERT INTO users ( enabled, username, email, password, mentor_id, student_id ) VALUES ( true, 'teacher1', 'teacher1@school.nl', '$2a$12$d6A5s4jUnNg3YI20mJ.RmeO6PNFhuyTvx5yUToRPhosL3QVowTRYy', 4, null);
INSERT INTO users ( enabled, username, email, password, mentor_id, student_id ) VALUES ( true, 'teacher2', 'teacher2@school.nl', '$2a$12$d6A5s4jUnNg3YI20mJ.RmeO6PNFhuyTvx5yUToRPhosL3QVowTRYy', 5, null);
INSERT INTO users ( enabled, username, email, password, mentor_id, student_id ) VALUES ( true, 'teacher3', 'teacher3@school.nl', '$2a$12$d6A5s4jUnNg3YI20mJ.RmeO6PNFhuyTvx5yUToRPhosL3QVowTRYy', 6, null);

INSERT INTO users ( enabled, username, email, password, mentor_id, student_id ) VALUES ( true, 'student1', 'student1@school.nl', '$2a$12$d6A5s4jUnNg3YI20mJ.RmeO6PNFhuyTvx5yUToRPhosL3QVowTRYy', 2, 1);
INSERT INTO users ( enabled, username, email, password, mentor_id, student_id ) VALUES ( true, 'student2', 'student2@school.nl', '$2a$12$d6A5s4jUnNg3YI20mJ.RmeO6PNFhuyTvx5yUToRPhosL3QVowTRYy', 3, 7);


INSERT INTO authorities ( username, authority ) VALUES ('admin','ADMIN');

INSERT INTO authorities ( username, authority ) VALUES ('planner','PLANNER');

INSERT INTO authorities ( username, authority ) VALUES ('mentor1','MENTOR');
INSERT INTO authorities ( username, authority ) VALUES ('mentor2','MENTOR');

INSERT INTO authorities ( username, authority ) VALUES ('teacher1','TEACHER');
INSERT INTO authorities ( username, authority ) VALUES ('teacher2','TEACHER');
INSERT INTO authorities ( username, authority ) VALUES ('teacher3','TEACHER');

INSERT INTO authorities ( username, authority ) VALUES ('student1','STUDENT');
INSERT INTO authorities ( username, authority ) VALUES ('student2','STUDENT');


INSERT INTO reservations ( student_id, workshop_id, priority, dt_reserved ) VALUES ( 7, 2, 3, CURRENT_DATE);
INSERT INTO reservations ( student_id, workshop_id, priority, dt_reserved ) VALUES ( 1, 2, 2, CURRENT_DATE - INTERVAL '2 DAYS');
INSERT INTO reservations ( student_id, workshop_id, priority, dt_reserved ) VALUES ( 1, 3, 3, CURRENT_DATE - INTERVAL '4 DAYS');
INSERT INTO reservations ( student_id, workshop_id, priority, dt_reserved ) VALUES ( 2, 2, 1, CURRENT_DATE - INTERVAL '3 DAYS');
INSERT INTO reservations ( student_id, workshop_id, priority, dt_reserved ) VALUES ( 3, 4, 3, CURRENT_DATE - INTERVAL '1 DAYS');
INSERT INTO reservations ( student_id, workshop_id, priority, dt_reserved ) VALUES ( 4, 3, 1, CURRENT_DATE - INTERVAL '2 DAYS');
INSERT INTO reservations ( student_id, workshop_id, priority, dt_reserved ) VALUES ( 5, 4, 2, CURRENT_DATE - INTERVAL '1 DAYS');
INSERT INTO reservations ( student_id, workshop_id, priority, dt_reserved ) VALUES ( 5, 3, 1, CURRENT_DATE - INTERVAL '1 DAYS');

INSERT INTO reservations ( student_id, workshop_id, priority, dt_reserved ) VALUES ( 5, 12, 3, CURRENT_DATE - INTERVAL '1 DAYS');
INSERT INTO reservations ( student_id, workshop_id, priority, dt_reserved ) VALUES ( 4, 12, 2, CURRENT_DATE - INTERVAL '1 DAYS');
INSERT INTO reservations ( student_id, workshop_id, priority, dt_reserved ) VALUES ( 3, 11, 1, CURRENT_DATE - INTERVAL '1 DAYS');
UPDATE reservations SET dt_processed = CURRENT_DATE WHERE student_id = 1 AND workshop_id = 2;


INSERT INTO bookings ( student_id, workshop_id, dt_booked, attended, feedback ) VALUES ( 7, 2, '2023-01-09', true, 'goed meegedaan, jochie!');
INSERT INTO bookings ( student_id, workshop_id, dt_booked, attended, feedback ) VALUES ( 7, 8, '2023-01-11', true, 'kan beter opletten');
INSERT INTO bookings ( student_id, workshop_id, dt_booked, attended, feedback ) VALUES ( 1, 2, '2023-01-16', false, 'ziek');
