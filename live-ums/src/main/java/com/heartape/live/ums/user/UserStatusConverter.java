package com.heartape.live.ums.user;

import com.heartape.user.UserStatus;
import jakarta.persistence.AttributeConverter;

public class UserStatusConverter implements AttributeConverter<UserStatus, String> {

    @Override
    public String convertToDatabaseColumn(UserStatus attribute) {
        return attribute == null ? null : attribute.toString();
    }

    @Override
    public UserStatus convertToEntityAttribute(String dbData) {
        return UserStatus.valueOf(dbData);
    }
}
