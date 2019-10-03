ALTER TABLE questions
  ADD COLUMN wrong_attempts INT NOT NULL DEFAULT 0;
