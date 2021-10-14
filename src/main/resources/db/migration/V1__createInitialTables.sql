CREATE TABLE `User` (
	user_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    username TEXT,
    `password` TEXT,
    email TEXT,
    first_name TEXT,
    last_name TEXT
);

CREATE TABLE Post (
	post_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	create_on DATETIME,
    last_edited DATETIME,
    user_id INT, 
    FOREIGN KEY (user_id) REFERENCES `User`(user_id)
);

CREATE TABLE `Comment` (
	post_id INT NOT NULL REFERENCES Post,
    content VARCHAR(500),
    parent_id INT NOT NULL,
    FOREIGN KEY(parent_id) REFERENCES Post(post_id),
    PRIMARY KEY(post_id)
);

CREATE TABLE Article (
	post_id INT NOT NULL REFERENCES Post,
    content MEDIUMTEXT,
    access CHAR(3),
    section_id INT NOT NULL,
    FOREIGN KEY(section_id) REFERENCES Post(post_id),
    PRIMARY KEY(post_id)
);

CREATE TABLE Section (
	section_id INT NOT NULL PRIMARY KEY,
    section_name char(200)
);

CREATE TABLE Favorite_Article (
	user_id INT NOT NULL,
    post_id INT NOT NULL,
    PRIMARY KEY(user_id, post_id),
    FOREIGN KEY(user_id) REFERENCES `User`(user_id),
    FOREIGN KEY(post_id) REFERENCES Article(post_id)
);

CREATE TABLE `Role` (
	role_id INT NOT NULL PRIMARY KEY,
    role_name char(50)
);

CREATE TABLE Permission (
	perm_id INT NOT NULL PRIMARY KEY,
    perm_name CHAR(200)
);

CREATE TABLE Role_Permission (
	role_id INT NOT NULL,
    perm_id INT NOT NULL,
    PRIMARY KEY(role_id, perm_id),
    FOREIGN KEY(role_id) REFERENCES `Role`(role_id),
    FOREIGN KEY(perm_id) REFERENCES Permission(perm_id)
);

CREATE TABLE User_Role (
	user_id INT NOT NULL,
    role_id INT NOT NULL,
    PRIMARY KEY(user_id, role_id),
    FOREIGN KEY(user_id) REFERENCES `User`(user_id),
    FOREIGN KEY(role_id) REFERENCES Role(role_id)
);