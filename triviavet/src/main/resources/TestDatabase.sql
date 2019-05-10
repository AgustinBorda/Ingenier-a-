CREATE DATABASE IF NOT EXISTS trivia_test;

use trivia_test;

CREATE TABLE IF NOT EXISTS users (
  id int(11) NOT NULL auto_increment PRIMARY KEY,
  username VARCHAR(56) NOT NULL UNIQUE,
  password VARCHAR(56),
  admin BOOLEAN,
  created_at DATETIME,
  updated_at DATETIME
);

CREATE TABLE IF NOT EXISTS questions (
  id int(11) NOT NULL auto_increment PRIMARY KEY,
  user_id int(11),
  description VARCHAR(255) NOT NULL UNIQUE,
  active BOOLEAN,
  created_at DATETIME,
  updated_at DATETIME
);


CREATE TABLE IF NOT EXISTS options (
  id int(11) NOT NULL auto_increment PRIMARY KEY,
  question_id int(11) ,
  foreign key (question_id) references questions (id),
  description VARCHAR(255) NOT NULL ,
  correct BOOLEAN,
  created_at DATETIME,
  updated_at DATETIME
);
