CREATE TABLE users(
  id PRIMARY KEY UNIQUE NOT NULL,
  login TEXT  UNIQUE NOT NULL ,
  password TEXT NOT NULL,
  role TEXT NOT NULL

);
