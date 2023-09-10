INSERT INTO tes(name) VALUES('테스트');

INSERT user_tb(email, password, role, created_at) VALUES('aozp73@naver.com', '$2a$12$Q3DO7IuHDt/MmlgBHf91MO2lDs5cnWWxQRzU2/3sIHFFnq/JJWy3u', 'admin', now());
INSERT my_project_role_code_tb(project_role) VALUES('BackEnd');
INSERT my_project_role_code_tb(project_role) VALUES('FrontEnd');
INSERT my_project_role_code_tb(project_role) VALUES('DevOps');

INSERT my_skill_type_code_tb(skill_type) VALUES('BackEnd');
INSERT my_skill_type_code_tb(skill_type) VALUES('FrontEnd');
INSERT my_skill_type_code_tb(skill_type) VALUES('DevOps');
INSERT my_skill_type_code_tb(skill_type) VALUES('ETC');