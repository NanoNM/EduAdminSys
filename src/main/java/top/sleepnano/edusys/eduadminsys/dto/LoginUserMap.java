package top.sleepnano.edusys.eduadminsys.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@Component
public class LoginUserMap {
    private LoginUser loginUser;
    private Long timestamp;
}
