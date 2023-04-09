package top.sleepnano.edusys.eduadminsys.dto;

import lombok.Data;

@Data
public class PostLoginStudent {
    private String name;
    private String passwd;
    private String role;
}
