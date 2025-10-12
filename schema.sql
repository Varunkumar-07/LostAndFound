CREATE DATABASE IF NOT EXISTS lost_and_found;

USE lost_and_found;

CREATE TABLE admin (
  admin_id      INT AUTO_INCREMENT PRIMARY KEY,
  username      VARCHAR(50) NOT NULL UNIQUE,
  password_hash VARCHAR(255) NOT NULL,
  created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE item (
  item_id        INT AUTO_INCREMENT PRIMARY KEY,
  title          VARCHAR(100) NOT NULL,
  category       VARCHAR(50),
  description    TEXT,
  location_found VARCHAR(100),
  date_reported  DATE,
  type           ENUM('lost','found') NOT NULL,
  status         ENUM('open','claimed','verified','rejected','closed') DEFAULT 'open',
  reported_by    VARCHAR(100),
  contact        VARCHAR(100),
  created_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE claim (
  claim_id         INT AUTO_INCREMENT PRIMARY KEY,
  item_id          INT NOT NULL,
  claimant_name    VARCHAR(100) NOT NULL,
  claimant_contact VARCHAR(100) NOT NULL,
  proof_description TEXT,
  status           ENUM('pending','approved','rejected') DEFAULT 'pending',
  reviewed_by      INT,
  claimed_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  reviewed_at      TIMESTAMP NULL,
  FOREIGN KEY (item_id) REFERENCES item(item_id),
  FOREIGN KEY (reviewed_by) REFERENCES admin(admin_id)
);

-- Default credentials: admin / admin123 (MD5 hash stored below).
-- On first successful login the system will auto-upgrade this to BCrypt.
-- IMPORTANT: change this password before deploying to production.
-- To set a BCrypt hash directly: print AdminDAO.hashPassword("newpassword") and UPDATE admin SET password_hash = '...'
INSERT INTO admin (username, password_hash) VALUES ('admin', MD5('admin123'));
