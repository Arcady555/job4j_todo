CREATE TABLE categories_tasks (
   PRIMARY KEY (task_id, category_id),
   task_id int not null REFERENCES tasks(id),
   category_id int not null REFERENCES category(id)
);