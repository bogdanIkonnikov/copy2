package tbank.copy2.web.dto.test;

import lombok.Data;
import lombok.NoArgsConstructor;
import tbank.copy2.common.enums.AccessMode;

@Data
@NoArgsConstructor
public class AccessResponse {
    private AccessMode accessMode;
    private String shareToken;
}
