INSERT INTO exercise (id, name, average_burn, created_on, image_file_name)
VALUES
    (1, 'Push-up', 50.0, '2022-01-01', 'pushup.jpg'),
    (2, 'Squat', 70.0, '2022-01-05', 'squat.jpg'),
    (3, 'Pull-up', 80.0, '2022-02-10', 'pullup.jpg');

INSERT INTO musclegroup (id, name, description, established_on, complexity_factor, image_file_name)
VALUES
    (1, 'Chest', 'Focus on pectoral muscles', '2020-01-10', 1.5, NULL),
    (2, 'Legs', 'Focus on quadriceps and hamstrings', '2020-03-15', 2.0, NULL),
    (3, 'Back', 'Focus on latissimus dorsi and traps', '2020-02-05', 1.8, NULL);

-- Many-to-many relationships
INSERT INTO exercise_musclegroup (exercise_id, musclegroup_id) VALUES (1,1), (1,3), (2,2);
