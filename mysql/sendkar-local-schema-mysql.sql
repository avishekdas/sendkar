create database sendkar;
GRANT ALL PRIVILEGES ON sendkar.* TO 'mysqluser'@'%' WITH GRANT OPTION;

USE sendkar;
DROP table IF EXISTS users;
DROP table IF EXISTS  roles;
DROP table IF EXISTS  user_roles;

CREATE TABLE `users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(40) NOT NULL,
  `username` varchar(15) NOT NULL,
  `email` varchar(40) NOT NULL,
  `password` varchar(100) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_users_username` (`username`),
  UNIQUE KEY `uk_users_email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `roles` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(60) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_roles_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;


CREATE TABLE `user_roles` (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `fk_user_roles_role_id` (`role_id`),
  CONSTRAINT `fk_user_roles_role_id` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`),
  CONSTRAINT `fk_user_roles_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT IGNORE INTO roles(name) VALUES('ROLE_USER');
INSERT IGNORE INTO roles(name) VALUES('ROLE_ADMIN');

-- CREATE INDEX events_idx ON events(entity_type, entity_id, event_id);
-- CREATE INDEX events_published_idx ON events(published, event_id);

ALTER TABLE `sendkar`.`users` 
ADD COLUMN `emailotp` VARCHAR(10) NULL AFTER `email`,
ADD COLUMN `emailverified` TINYINT NULL AFTER `emailotp`,
ADD COLUMN `mobilenumber` BIGINT(10) NULL AFTER `emailverified`,
ADD COLUMN `mobileotp` VARCHAR(10) NULL AFTER `mobilenumber`,
ADD COLUMN `mobileverified` TINYINT NULL AFTER `mobileotp`;

