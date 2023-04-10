package top.sleepnano.edusys.eduadminsys.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import top.sleepnano.edusys.eduadminsys.util.StatusCodeUtil;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    private String status;
    private StatusCodeUtil statusCode;
    private String message;
    private Object data;
}
