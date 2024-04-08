CREATE TABLE task (
                      id BIGINT NOT NULL AUTO_INCREMENT,
                      title VARCHAR(255),
                      description TEXT,
                      completed BOOLEAN,
                      due_date DATETIME,
                      user_id BIGINT,
                      PRIMARY KEY (id),
                      FOREIGN KEY (user_id) REFERENCES USER(id)
);
