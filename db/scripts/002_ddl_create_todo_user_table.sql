CREATE TABLE IF NOT EXISTS todo_user (
   userId SERIAL PRIMARY KEY,
   name VARCHAR,
   login VARCHAR UNIQUE,
   password VARCHAR
);