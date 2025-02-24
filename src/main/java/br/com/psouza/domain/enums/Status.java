package main.java.br.com.psouza.domain.enums;

import java.util.Arrays;
import java.util.Optional;

public enum Status {
    INITIATED, COMPLETED, CANCELLED;

    public static Optional<Status> getByName(String value) {
        return Arrays.stream(Status.values())
                .filter(status -> status.name().equalsIgnoreCase(value))
                .findFirst();
    }
}
