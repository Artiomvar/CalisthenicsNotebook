DROP TABLE IF EXISTS exercise_musclegroup;
DROP TABLE IF EXISTS exercise;
DROP TABLE IF EXISTS musclegroup;

CREATE TABLE exercise (
                          id INT PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          average_burn DOUBLE NOT NULL,
                          created_on DATE NOT NULL,
                          image_file_name VARCHAR(255)
);

CREATE TABLE musclegroup (
                             id INT PRIMARY KEY,
                             name VARCHAR(255) NOT NULL,
                             description VARCHAR(255),
                             established_on DATE NOT NULL,
                             complexity_factor DOUBLE NOT NULL,
                             image_file_name VARCHAR(255) -- Add this column
);

CREATE TABLE exercise_musclegroup (
                                      exercise_id INT NOT NULL,
                                      musclegroup_id INT NOT NULL,
                                      PRIMARY KEY (exercise_id, musclegroup_id),
                                      FOREIGN KEY (exercise_id) REFERENCES exercise(id) ON DELETE CASCADE,
                                      FOREIGN KEY (musclegroup_id) REFERENCES musclegroup(id) ON DELETE CASCADE
);
