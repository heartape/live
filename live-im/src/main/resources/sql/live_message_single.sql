-- ----------------------------
-- Table structure for live_message_single
-- ----------------------------
DROP TABLE IF EXISTS `live_message_single`;
CREATE TABLE `live_message_single`
(
    `id`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `uid`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `receiver_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `type`        varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `timestamp`   datetime                                                     NOT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `uid_index` (`uid` ASC) USING BTREE,
    INDEX `receiver_id_index` (`receiver_id` ASC) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for live_message_single_file
-- ----------------------------
DROP TABLE IF EXISTS `live_message_single_file`;
CREATE TABLE `live_message_single_file`
(
    `id`         varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `message_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `filename`   varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `url`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `size`       int                                                           NOT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `message_id_index` (`message_id` ASC) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for live_message_single_greeting
-- ----------------------------
DROP TABLE IF EXISTS `live_message_single_greeting`;
CREATE TABLE `live_message_single_greeting`
(
    `id`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NOT NULL,
    `message_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NOT NULL,
    `greetings`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `message_id_index` (`message_id` ASC) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for live_message_single_image
-- ----------------------------
DROP TABLE IF EXISTS `live_message_single_image`;
CREATE TABLE `live_message_single_image`
(
    `id`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `message_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `name`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `format`     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `message_id_index` (`message_id` ASC) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for live_message_single_image_copy
-- ----------------------------
DROP TABLE IF EXISTS `live_message_single_image_copy`;
CREATE TABLE `live_message_single_image_copy`
(
    `id`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NOT NULL,
    `image_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NOT NULL,
    `url`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `height`   int                                                           NOT NULL,
    `width`    int                                                           NOT NULL,
    `size`     int                                                           NOT NULL,
    `type`     int                                                           NOT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `image_id_index` (`image_id` ASC) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for live_message_single_location
-- ----------------------------
DROP TABLE IF EXISTS `live_message_single_location`;
CREATE TABLE `live_message_single_location`
(
    `id`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NOT NULL,
    `message_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NOT NULL,
    `point`      point                                                         NOT NULL,
    `desc`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `message_id_index` (`message_id` ASC) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for live_message_single_sound
-- ----------------------------
DROP TABLE IF EXISTS `live_message_single_sound`;
CREATE TABLE `live_message_single_sound`
(
    `id`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NOT NULL,
    `message_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NOT NULL,
    `url`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `size`       int                                                           NOT NULL,
    `second`     int                                                           NOT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `message_id_index` (`message_id` ASC) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for live_message_single_text
-- ----------------------------
DROP TABLE IF EXISTS `live_message_single_text`;
CREATE TABLE `live_message_single_text`
(
    `id`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci    NOT NULL,
    `message_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci    NOT NULL,
    `text`       varchar(10000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `message_id_index` (`message_id` ASC) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for live_message_single_video
-- ----------------------------
DROP TABLE IF EXISTS `live_message_single_video`;
CREATE TABLE `live_message_single_video`
(
    `id`           varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NOT NULL,
    `message_id`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NOT NULL,
    `url`          varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `format`       varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NOT NULL,
    `size`         int                                                           NOT NULL,
    `second`       int                                                           NOT NULL,
    `thumb_id`     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NOT NULL,
    `thumb_format` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NOT NULL,
    `thumb_url`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `thumb_width`  int                                                           NOT NULL,
    `thumb_height` int                                                           NOT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `message_id_index` (`message_id` ASC) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = Dynamic;

