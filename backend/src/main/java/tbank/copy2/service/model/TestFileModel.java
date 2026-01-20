package tbank.copy2.service.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Data
@NoArgsConstructor
public class TestFileModel {
    private String name;
    private String description;
    private MultipartFile file;
}
