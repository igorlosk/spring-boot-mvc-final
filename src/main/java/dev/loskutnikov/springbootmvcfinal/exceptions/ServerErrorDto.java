package dev.loskutnikov.springbootmvcfinal.exceptions;

import java.time.LocalDateTime;

public record ServerErrorDto(
        String message,
        String detailMessage,
        LocalDateTime dateTime

) {
}
