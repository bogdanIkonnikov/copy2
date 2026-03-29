package tbank.copy2.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;


@Data
@NoArgsConstructor
public class TestFileModel {
    private String name;
    private String description;
    private MultipartFile file;
    private Long userId;
}
