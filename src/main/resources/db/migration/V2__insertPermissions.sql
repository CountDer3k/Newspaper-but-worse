DROP TABLE IF EXISTS Role_Permission;
CREATE TABLE Role_Permission (
	role_id INT NOT NULL REFERENCES `Role` (role_id),
    perm_id INT NOT NULL REFERENCES Permission (perm_id)
);

DROP TABLE IF EXISTS User_Role;
CREATE TABLE User_Role (
	user_id INT NOT NULL REFERENCES `User` (user_id),
    role_id INT NOT NULL REFERENCES `Role` (role_id)
);

set foreign_key_checks = 0;
ALTER TABLE Permission MODIFY COLUMN perm_id INT NOT NULL AUTO_INCREMENT;
set foreign_key_checks = 1;

INSERT INTO Permission (perm_name, created_on, modified_on) 
VALUES 
	("CanPostComments", now(), now()),
    ("CanEditMyComments", now(), now()),
    ("CanViewComments", now(), now()),
    ("CanViewSections", now(), now()),
    ("CanCreateReaderAcc", now(), now()),
    ("CanViewMainPage", now(), now()),
    ("CanFavArticles", now(), now()),
    ("CanFilterArticles", now(), now()),
    ("CanReadAllArticles", now(), now()),
    ("CanReadGuestArticles", now(), now()),
    ("CanViewRepliesToMyComments", now(), now()),
    ("CanViewFavArticles", now(), now()),
    ("CanEditFavArticlesList", now(), now()),
    ("CanDeleteFavArticlesList", now(), now()),
    ("CanPostArticle", now(), now()),
    ("CanEditMyArticles", now(), now()),
    ("CanViewMyArticlesList", now(), now()),
    ("CanViewMyArticleComments", now(), now()),
    ("CanRemoveMyArticles", now(), now()),
    ("CanViewRegisteredUserList", now(), now()),
    ("CanDeleteUserAccounts", now(), now()),
    ("CanCreateAuthorAccounts", now(), now()),
    ("CanDeleteAuthorAccounts", now(), now()),
    ("CanDeleteReaderAccounts", now(), now()),
    ("CanDeleteComments", now(), now()),
    ("CanDeleteArticles", now(), now());

set foreign_key_checks = 0;
ALTER TABLE Role MODIFY COLUMN role_id INT NOT NULL AUTO_INCREMENT;
set foreign_key_checks = 1;

INSERT INTO `Role` (role_name, created_on, modified_on) 
VALUES 
	("GUEST", now(), now()),
    ("READER", now(), now()),
    ("AUTHOR", now(), now()),
    ("ADMIN", now(), now());
    
INSERT INTO Role_Permission (role_id, perm_id)
VALUES
	(1, 6),
    (1, 4),
    (1, 3),
    (1, 5),
    (1, 10),
    (2, 1),
    (2, 2),
    (2, 3),
    (2, 4),
    (2, 6),
    (2, 7),
    (2, 8),
    (2, 9),
    (2, 10),
    (2, 11),
    (2, 12),
    (2, 13),
    (2, 14),
    (3, 3),
    (3, 4),
    (3, 6),
    (3, 10),
    (3, 11),
    (3, 1),
    (3, 2),
    (3, 8),
    (3, 9),
    (3, 15),
    (3, 16),
    (3, 17),
    (3, 18),
    (3, 19),
    (4, 3),
    (4, 4),
    (4, 6),
    (4, 10),
    (4, 11),
    (4, 8),
    (4, 9),
    (4, 20),
    (4, 21),
    (4, 22),
    (4, 23),
    (4, 24),
    (4, 25),
    (4, 26);
    
INSERT INTO `User` (username, `password`, email, first_name, last_name, created_on, modified_on) 
VALUES
	("PParker", "$2a$10$kUlCiuGYLHPTwxNnXR9NL.FHeXOt.rmiO6D59kOGf7MsfLKzcU06m", "ArachnidGuy@gmail.com", "Peter", "Parker", now(), now()),
    ("JonahIsTheMan", "$2a$10$A1NQhGFyQuy4m896Y3fRnO/XGQRMyQ.t/QSx1S8GdLaiPHkIv1v4K", "JJJamesonJr@gmail.com", "John", "Jameson", now(), now());
    
INSERT INTO User_Role (user_id, role_id)
VALUES
	((SELECT user_id FROM `User` WHERE username = "PParker" LIMIT 1), 3),
    ((SELECT user_id FROM `User` WHERE username = "JonahIsTheMan" LIMIT 1), 4);
	
