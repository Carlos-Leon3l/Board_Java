package com.example.board_java.br.com.persistance.converter;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class OffSetDateTimeConverter {

    public static OffsetDateTime toOffSetDateTime(final Timestamp value){

        return Objects.nonNull(value) ? OffsetDateTime.ofInstant(value.toInstant(), ZoneOffset.UTC) : null;

    }
    public static Timestamp toTimestamp(final OffsetDateTime value){
        return Objects.nonNull(value) ? Timestamp.valueOf(value.atZoneSameInstant(ZoneOffset.UTC).toLocalDateTime()) : null;
    }

}
