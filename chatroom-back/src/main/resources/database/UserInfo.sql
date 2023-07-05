CREATE TABLE `userinfo`  (
    `uid` int NOT NULL AUTO_INCREMENT,
    `account` varchar(255) NOT NULL UNIQUE,
    `password` varchar(255) NOT NULL,
    PRIMARY KEY (`uid`)
);
INSERT INTO `userinfo`(`uid`, `account`, `password`) VALUES (1, 'jhon', '123456');
INSERT INTO `userinfo`(`uid`, `account`, `password`) VALUES (2, 'jack', '123456');
INSERT INTO `userinfo`(`uid`, `account`, `password`) VALUES (3, 'mike', '123456');
INSERT INTO `userinfo`(`uid`, `account`, `password`) VALUES (4, 'tom', '123456');