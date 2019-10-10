ALTER TABLE questions
  DROP COLUMN wrong_attempts,
  DROP COLUMN right_attempts,
  DROP COLUMN total_attempts;

CREATE TABLE question_statistics(
  question_id INTEGER PRIMARY KEY,
  wrong_attempts INTEGER NOT NULL,
  right_attempts INTEGER NOT NULL,
  total_attempts INTEGER NOT NULL,
  FOREIGN KEY (question_id) REFERENCES questions (id) ON DELETE CASCADE
);
