-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema cupcake
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `cupcake` ;

-- -----------------------------------------------------
-- Schema cupcake
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `cupcake` DEFAULT CHARACTER SET utf8 ;
USE `cupcake` ;

-- -----------------------------------------------------
-- Table `cupcake`.`zip`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cupcake`.`zip` ;

CREATE TABLE IF NOT EXISTS `cupcake`.`zip` (
  `zipcode` INT NOT NULL,
  `city` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`zipcode`))
ENGINE = InnoDB;

INSERT INTO `cupcake`.`zip`
(`zipcode`,
`city`)
VALUES
(2860, 'Søborg'),
(2880, 'Bagsværd'),
(3700, 'Rønne'),
(3730, 'Nexø'),
(3760, 'Gudhjem');

-- -----------------------------------------------------
-- Table `cupcake`.`address`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cupcake`.`address` ;

CREATE TABLE IF NOT EXISTS `cupcake`.`address` (
  `address_id` INT NOT NULL AUTO_INCREMENT,
  `street` VARCHAR(45) NOT NULL,
  `street_number` VARCHAR(45) NOT NULL,
  `zipcode` INT NOT NULL,
  PRIMARY KEY (`address_id`),
  INDEX `fk_address_zip_idx` (`zipcode` ASC) VISIBLE,
  CONSTRAINT `fk_address_zip`
    FOREIGN KEY (`zipcode`)
    REFERENCES `cupcake`.`zip` (`zipcode`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

INSERT INTO `cupcake`.`address`
(`street`,
`street_number`,
`zipcode`)
VALUES
('Balladegade', 42, 2860),
('Vejgade', 30, 3700),
('Bytorv', 2, 3730),
('Gadetorvet', 50, 3760),
('Godtgemt', 35, 2880);


-- -----------------------------------------------------
-- Table `cupcake`.`userrole`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cupcake`.`userrole` ;

CREATE TABLE IF NOT EXISTS `cupcake`.`userrole` (
  `userrole_id` INT NOT NULL AUTO_INCREMENT,
  `role_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`userrole_id`))
ENGINE = InnoDB;

INSERT INTO `cupcake`.`userrole`
(`role_name`)
VALUES
("admin"),
("kunde");



-- -----------------------------------------------------
-- Table `cupcake`.`customer`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cupcake`.`customer` ;

CREATE TABLE IF NOT EXISTS `cupcake`.`customer` (
  `customer_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `balance` INT NOT NULL DEFAULT 0,
  `address_id` INT NOT NULL,
  `userrole_id` INT NOT NULL DEFAULT 2,
  PRIMARY KEY (`customer_id`, `address_id`),
  INDEX `fk_customer_address1_idx` (`address_id` ASC) VISIBLE,
  INDEX `fk_customer_user_role1_idx` (`userrole_id` ASC) VISIBLE,
  CONSTRAINT `fk_customer_address1`
    FOREIGN KEY (`address_id`)
    REFERENCES `cupcake`.`address` (`address_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_customer_user_role1`
    FOREIGN KEY (`userrole_id`)
    REFERENCES `cupcake`.`userrole` (`userrole_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

INSERT INTO `cupcake`.`customer`
(`name`,
`email`,
`password`,
`address_id`,
`userrole_id`)
VALUES
("Carsten Cupcake", 'c@cupcake.dk', "1234", 1, 1);

INSERT INTO `cupcake`.`customer`
(`name`,
`email`,
`password`,
`address_id`,
`balance`)
VALUES
("Morten Mangepenge", "m@m.dk", 1234, 2, 400),
("Denis Dynamit", "d@d.dk", 1234, 3, 5000),
("Long Langefinger", "l@l.dk", 1234, 4, 80),
("Sebastian Sauna", "s@S.dk", 1234, 5, 165);



-- -----------------------------------------------------
-- Table `cupcake`.`order`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cupcake`.`order` ;

CREATE TABLE IF NOT EXISTS `cupcake`.`order` (
  `order_id` INT NOT NULL AUTO_INCREMENT,
  `customer_id` INT NOT NULL,
  `date` DATE NOT NULL,
  PRIMARY KEY (`order_id`),
  INDEX `fk_order_customer1_idx` (`customer_id` ASC) VISIBLE,
  CONSTRAINT `fk_order_customer1`
    FOREIGN KEY (`customer_id`)
    REFERENCES `cupcake`.`customer` (`customer_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

INSERT INTO `cupcake`.`order`
(`customer_id`,
`date`)
VALUES
(2, '2022-03-31'),
(3, '2022-04-04'),
(4, '2022-02-20'),
(5, '2022-01-31');



-- -----------------------------------------------------
-- Table `cupcake`.`topping`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cupcake`.`topping` ;

CREATE TABLE IF NOT EXISTS `cupcake`.`topping` (
  `topping_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `price` INT NOT NULL,
  PRIMARY KEY (`topping_id`))
ENGINE = InnoDB;

INSERT INTO `cupcake`.`topping`
(`name`,
`price`)
VALUES
("Chocolate", 5),
("Blueberry", 5),
("Raspberry", 5),
("Crispy", 6),
("Strawberry", 6),
("Rum/Raisin", 7),
("Orange", 8),
("Lemon", 8),
("Blue cheese", 9),
("Jalapeno", 10),
("Foie gras", 50);


-- -----------------------------------------------------
-- Table `cupcake`.`bottom`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cupcake`.`bottom` ;

CREATE TABLE IF NOT EXISTS `cupcake`.`bottom` (
  `bottom_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `price` INT NOT NULL,
  PRIMARY KEY (`bottom_id`))
ENGINE = InnoDB;

INSERT INTO `cupcake`.`bottom`
(`name`,
`price`)
VALUES
("Chocolate", 5),
("Vanilla", 5),
("Nutmeg", 5),
("Pistacio", 6),
("Almond", 7),
("Svaneke", 10),
("Raisin", 6);


-- -----------------------------------------------------
-- Table `cupcake`.`orderline`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cupcake`.`orderline` ;

CREATE TABLE IF NOT EXISTS `cupcake`.`orderline` (
  `orderline_id` INT NOT NULL AUTO_INCREMENT,
  `amount` INT NOT NULL,
  `topping_id` INT NOT NULL,
  `bottom_id` INT NOT NULL,
  `order_id` INT NOT NULL,
  PRIMARY KEY (`orderline_id`),
  INDEX `fk_orderline_topping1_idx` (`topping_id` ASC) VISIBLE,
  INDEX `fk_orderline_bottom1_idx` (`bottom_id` ASC) VISIBLE,
  INDEX `fk_orderline_order1_idx` (`order_id` ASC) VISIBLE,
  CONSTRAINT `fk_orderline_topping1`
    FOREIGN KEY (`topping_id`)
    REFERENCES `cupcake`.`topping` (`topping_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_orderline_bottom1`
    FOREIGN KEY (`bottom_id`)
    REFERENCES `cupcake`.`bottom` (`bottom_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_orderline_order1`
    FOREIGN KEY (`order_id`)
    REFERENCES `cupcake`.`order` (`order_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

INSERT INTO `cupcake`.`orderline`
(`amount`,
`topping_id`,
`bottom_id`,
`order_id`)
VALUES
(3, 1, 2, 1),
(5, 4, 7, 1),
(1, 11, 1, 1),
(10, 8, 6, 2),
(2, 1, 1, 3),
(4, 9, 3, 4);


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

CREATE 
    ALGORITHM = UNDEFINED 
    DEFINER = `root`@`localhost` 
    SQL SECURITY DEFINER
VIEW `cupcake`.`customer_view` AS
    SELECT 
        `c`.`userrole_id` AS `userrole_id`,
        `a`.`zipcode` AS `zipcode`,
        `c`.`address_id` AS `address_id`,
        `c`.`customer_id` AS `customer_id`,
        `c`.`name` AS `name`,
        `c`.`email` AS `email`,
        `c`.`password` AS `password`,
        `c`.`balance` AS `balance`,
        `a`.`street` AS `street`,
        `a`.`street_number` AS `street_number`,
        `z`.`city` AS `city`,
        `u`.`role_name` AS `role_name`
    FROM
        (((`cupcake`.`customer` `c`
        JOIN `cupcake`.`address` `a` ON ((`c`.`address_id` = `a`.`address_id`)))
        JOIN `cupcake`.`zip` `z` ON ((`a`.`zipcode` = `z`.`zipcode`)))
        JOIN `cupcake`.`userrole` `u` ON ((`c`.`userrole_id` = `u`.`userrole_id`)))
;

CREATE 
    ALGORITHM = UNDEFINED 
    DEFINER = `root`@`localhost` 
    SQL SECURITY DEFINER
VIEW `order_view_with_total` AS
    SELECT 
		`o`.`order_id` AS `order_id`,
        `o`.`customer_id` AS `customer_id`,
        `c`.`name` AS `customer_name`,
        `o`.`date` AS `date`,
        `ol`.`orderline_id` AS `orderline_id`,
        `b`.`bottom_id` AS `bottom_id`,
        `b`.`name` AS `b_name`,
        `b`.`price` AS `b_price`,
        `t`.`topping_id` AS `topping_id`,
        `t`.`name` AS `t_name`,
        `t`.`price` AS `t_price`,
        ((`t`.`price` + `b`.`price`) * `ol`.`amount`) AS `total`
    FROM
        ((((`order` `o`
        JOIN `orderline` `ol` ON ((`o`.`order_id` = `ol`.`order_id`)))
        JOIN `topping` `t` ON ((`ol`.`topping_id` = `t`.`topping_id`)))
        JOIN `bottom` `b` ON ((`ol`.`bottom_id` = `b`.`bottom_id`)))
        JOIN `customer` `c` ON ((`o`.`customer_id` = `c`.`customer_id`)))
;

CREATE 
    ALGORITHM = UNDEFINED 
    DEFINER = `cupcake`@`%` 
    SQL SECURITY DEFINER
VIEW `orderline_view_with_total` AS
    SELECT 
        `orderline`.`orderline_id` AS `orderline_id`,
        `orderline`.`order_id` AS `order_id`,
        `orderline`.`amount` AS `amount`,
        `t`.`name` AS `t_name`,
        `b`.`name` AS `b_name`,
        `t`.`price` AS `t_price`,
        `b`.`price` AS `b_price`,
        ((`orderline`.`amount` * `b`.`price`) + (`orderline`.`amount` * `t`.`price`)) AS `total`
    FROM
        ((`orderline`
        JOIN `topping` `t` ON ((`orderline`.`topping_id` = `t`.`topping_id`)))
        JOIN `bottom` `b` ON ((`orderline`.`bottom_id` = `b`.`bottom_id`)))
        ;
