package platform.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotNull;

@Data
//@Builder
public class CodeDto {
    @Schema(description = "Фрагмент кода в виде строки", example = "System.out.println(500)")
    @NotNull
    String code;
}
