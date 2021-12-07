ALTER TABLE Article
ADD section_id INT,
ADD FOREIGN KEY (section_id) REFERENCES Section(section_id);