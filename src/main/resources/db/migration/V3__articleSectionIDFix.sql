ALTER TABLE Article
DROP FOREIGN KEY article_ibfk_1;

ALTER TABLE Article
ADD FOREIGN KEY (section_id) REFERENCES Section(section_id);