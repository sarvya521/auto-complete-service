CREATE TABLE `mst_city` (
    `id` INT(11) PRIMARY KEY,
    `name` VARCHAR(30) UNIQUE
);
CREATE TABLE `mst_state` (
    `id` INT(11) PRIMARY KEY,
    `name` VARCHAR(30) UNIQUE
);