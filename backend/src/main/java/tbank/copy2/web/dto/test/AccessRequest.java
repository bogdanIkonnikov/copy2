package tbank.copy2.web.dto.test;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import tbank.copy2.common.enums.AccessMode;

@Data
@NoArgsConstructor
@Schema(description = "Запрос для изменения режима доступа к тесту")
public class AccessRequest {
    private AccessMode accessMode;
}
