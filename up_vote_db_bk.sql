-- -----------------------------------------------------
-- Table `upvote_app`.`posts`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `upvote_app`.`posts` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `question` VARCHAR(140) NOT NULL,
  `expiration_date_time` DATETIME NOT NULL,
  `created_at` DATETIME NOT NULL,
  `updated_at` DATETIME NOT NULL,
  `created_by` INT(11) NOT NULL,
  `updated_by` INT(11) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `upvote_app`.`choices`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `upvote_app`.`choices` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `text` VARCHAR(40) NOT NULL,
  `post_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_choices_post_idx` (`post_id` ASC),
  CONSTRAINT `fk_choices_posts1`
    FOREIGN KEY (`post_id`)
    REFERENCES `upvote_app`.`posts` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `upvote_app`.`roles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `upvote_app`.`roles` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` ENUM('ROLE_USER', 'ROLE_ADMIN') NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `upvote_app`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `upvote_app`.`users` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(40) NOT NULL,
  `username` VARCHAR(15) NOT NULL,
  `email` VARCHAR(40) NOT NULL,
  `password` VARCHAR(100) NOT NULL,
  `created_at` DATETIME NOT NULL,
  `updated_at` DATETIME NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `upvote_app`.`user_roles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `upvote_app`.`user_roles` (
  `user_id` INT(11) NOT NULL,
  `role_id` INT(11) NOT NULL,
  PRIMARY KEY (`user_id`, `role_id`),
  INDEX `fk_user_roles_roles1_idx` (`role_id` ASC),
  INDEX `fk_user_roles_users_idx` (`user_id` ASC),
  CONSTRAINT `fk_user_roles_roles1`
    FOREIGN KEY (`role_id`)
    REFERENCES `upvote_app`.`roles` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_roles_users`
    FOREIGN KEY (`user_id`)
    REFERENCES `upvote_app`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `upvote_app`.`votes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `upvote_app`.`votes` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `post_id` INT(11) NOT NULL,
  `user_id` INT(11) NOT NULL,
  `choice_id` INT(11) NOT NULL,
  `created_at` DATETIME NOT NULL,
  `updated_at` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_votes_post_idx` (`post_id` ASC),
  INDEX `fk_votes_user_idx` (`user_id` ASC),
  INDEX `fk_votes_choice_idx` (`choice_id` ASC),
  CONSTRAINT `fk_votes_choices1`
    FOREIGN KEY (`choice_id`)
    REFERENCES `upvote_app`.`choices` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_votes_posts1`
    FOREIGN KEY (`post_id`)
    REFERENCES `upvote_app`.`posts` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_votes_users1`
    FOREIGN KEY (`user_id`)
    REFERENCES `upvote_app`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

INSERT INTO upvote_app.roles(name) VALUES('ROLE_USER');
INSERT INTO upvote_app.roles(name) VALUES('ROLE_ADMIN');