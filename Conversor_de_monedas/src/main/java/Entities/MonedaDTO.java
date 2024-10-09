package Entities;

import lombok.NonNull;
import lombok.With;

@With
public record MonedaDTO(
        @NonNull
        String conversion_result
) {
}
