package top.sleepnano.edusys.eduadminsys.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.sleepnano.edusys.eduadminsys.entity.Class;
import top.sleepnano.edusys.eduadminsys.entity.Grade;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultGrade {
    private Grade grade;
    private List<Class> classes;
}
