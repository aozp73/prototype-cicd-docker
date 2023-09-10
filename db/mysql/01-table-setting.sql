CREATE TABLE tes (
  id BIGINT auto_increment primary key,
  name varchar(255) NOT NULL
);

-- MainIntroduce 테이블
CREATE TABLE main_introduce_tb (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    introduce_img_name VARCHAR(255),
    introduce_img_url VARCHAR(255),
    title VARCHAR(255),
    content TEXT,
    created_at DATETIME,
    updated_at DATETIME
);

-- MyBlog 테이블
CREATE TABLE my_blog_tb (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    main_title VARCHAR(255),
    sub_title VARCHAR(255),
    content TEXT,
    blog_img_name VARCHAR(255),
    blog_img_url VARCHAR(255),
    created_at DATETIME,
    updated_at DATETIME
);

-- User 테이블
CREATE TABLE user_tb (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) UNIQUE,
    password VARCHAR(255),
    role VARCHAR(50),
    created_at DATETIME
);

-- MyProject 테이블
CREATE TABLE my_project_tb (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    project_img_name VARCHAR(255),
    project_img_url VARCHAR(255),
    title VARCHAR(255),
    member INT,
    start_date DATE,
    end_date DATE,
    readme_Url VARCHAR(255),
    github_link VARCHAR(255),
    individual_performance_img_name VARCHAR(255),
    individual_performance_img_url VARCHAR(255),
    created_at DATETIME,
    updated_at DATETIME
);

-- MyProjectRoleCode 테이블
CREATE TABLE my_project_role_code_tb (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    project_role ENUM('BackEnd', 'FrontEnd', 'DevOps')
);

-- MyProjectRole 테이블
CREATE TABLE my_project_role_tb (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    project_id BIGINT,
    role_code_id BIGINT,
    created_at DATETIME,
    updated_at DATETIME,
    FOREIGN KEY (project_id) REFERENCES my_project_tb(id),
    FOREIGN KEY (role_code_id) REFERENCES my_project_role_code_tb(id)
);

-- MySkillTypeCode 테이블
CREATE TABLE my_skill_type_code_tb (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    skill_type ENUM('BackEnd', 'FrontEnd', 'DevOps', 'ETC')
);

-- MySkill 테이블
CREATE TABLE my_skill_tb (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    skill VARCHAR(255),
    my_skill_code_id BIGINT,
    created_at DATETIME,
    updated_at DATETIME,
    FOREIGN KEY (my_skill_code_id) REFERENCES my_skill_type_code_tb(id)
);
