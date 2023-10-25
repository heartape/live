CREATE DATABASE `live-ums` CHARACTER SET 'utf8mb4';

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
                         `id` bigint NOT NULL COMMENT '主键',
                         `avatar` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '头像url',
                         `nickname` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '昵称',
                         `username` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '登陆账号',
                         `password` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '登陆密码',
                         `phone` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                         `email` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                         `phone_verified` tinyint(1) NOT NULL COMMENT '手机号验证',
                         `email_verified` tinyint(1) NOT NULL COMMENT '邮箱验证',
                         `status` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '账户状态',
                         `create_time` datetime NOT NULL COMMENT '创建时间',
                         `update_time` datetime NOT NULL COMMENT '更新时间',
                         PRIMARY KEY (`id`) USING BTREE,
                         UNIQUE INDEX `username_index`(`username` ASC) USING BTREE,
                         UNIQUE INDEX `phone_index`(`phone` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

INSERT INTO `user` VALUES (1, 'https://github.githubassets.com/images/modules/logos_page/GitHub-Mark.png', '睡过头', '1111', '{noop}1111', NULL, NULL, 0, 0, 'NORMAL', '2023-10-25 14:55:51', '2023-10-25 14:55:54');
INSERT INTO `user` VALUES (2, 'https://github.githubassets.com/images/modules/logos_page/GitHub-Mark.png', '胖子', '2222', '{noop}2222', NULL, NULL, 0, 0, 'NORMAL', '2023-10-25 14:55:51', '2023-10-25 14:55:54');
INSERT INTO `user` VALUES (3, 'https://github.githubassets.com/images/modules/logos_page/GitHub-Mark.png', '小男孩', '3333', '{noop}3333', NULL, NULL, 0, 0, 'NORMAL', '2023-10-25 14:55:51', '2023-10-25 14:55:54');