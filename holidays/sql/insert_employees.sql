insert into employee(
  emp_id,
  created,
  first_name,
  last_name,
  middle_name,
  login_name,
  email,
  password
) 
values(
 nextval('seq_emp_id'),
 now(),
 'INSERTED_TEST',
 'INSERTED_TEST',
 'INSERTED_TEST',
 'INSERTED_TEST',
 'INSERTED_TEST@RT.RU',
 '$2a$10$4zn4y0ug49p3WkpYokK.z.Zlsw4jQS80QstfLgMyMiEW4MdsvhL8i'
);