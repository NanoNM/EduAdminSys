package top.sleepnano.edusys.eduadminsys.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostRegUser {
    private String name;
    private String passwd;
    private String employeeID;
    private String role;
}
