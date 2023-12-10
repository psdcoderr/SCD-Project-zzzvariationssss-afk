CREATE TABLE Books (
    b_id INT AUTO_INCREMENT PRIMARY KEY,
    b_title TEXT NOT NULL,
    author TEXT NOT NULL,
    yp INT,
    CONSTRAINT uc_book UNIQUE (b_title, author)
);

CREATE TABLE Poems (
    pid INT AUTO_INCREMENT PRIMARY KEY,
    b_id INT,
    p_title TEXT,
    FOREIGN KEY (b_id) REFERENCES Books(b_id) ON DELETE CASCADE
);

CREATE TABLE Verses (
    v_id INT AUTO_INCREMENT PRIMARY KEY,
    verse TEXT,
    p_id INT,
    FOREIGN KEY (p_id) REFERENCES Poems(pid) ON DELETE CASCADE
);

CREATE TABLE Tokens (
    t_id INT AUTO_INCREMENT PRIMARY KEY,
    token TEXT,
    v_id INT,
    FOREIGN KEY (v_id) REFERENCES Verses(v_id) ON DELETE CASCADE
);

CREATE TABLE Roots (
    r_id INT AUTO_INCREMENT PRIMARY KEY,
    t_id INT,
    root TEXT NOT NULL,
    FOREIGN KEY (t_id) REFERENCES Tokens(t_id) ON DELETE CASCADE
);