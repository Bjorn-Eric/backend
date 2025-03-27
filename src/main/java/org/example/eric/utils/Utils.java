package org.example.eric.utils;

import org.example.eric.dto.TaskDTO;
import org.example.eric.model.Task;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static List<TaskDTO> convertTaskListToTaskDTOList(List<Task> tasks) {
        List<TaskDTO> taskDTOList = new ArrayList<>();
        for (Task task : tasks) {
            taskDTOList.add(new TaskDTO(task));
        }
        return taskDTOList;
    }

    public static TaskDTO convertTaskToTaskDTO(Task task) {
        return new TaskDTO(task);
    }
}
