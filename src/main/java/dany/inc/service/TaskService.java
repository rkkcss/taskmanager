package dany.inc.service;

import dany.inc.domain.Task;
import dany.inc.domain.User;
import dany.inc.repository.TaskRepository;
import dany.inc.security.SecurityUtils;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;

    public TaskService(TaskRepository taskRepository, UserService userService) {
        this.taskRepository = taskRepository;
        this.userService = userService;
    }

    public ResponseEntity<?> findTaskByUserId() {
        Optional<User> user = userService.getUserWithAuthorities();
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Can't fetch tasks because user is not present!");
        }
        List<Task> result = taskRepository.findByTasksByUserId(user.get().getId());
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    public ResponseEntity<?> createTask(Task task) {
        Optional<User> user = userService.getUserWithAuthorities();
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Can't create task because user is not present!");
        }
        if (task.getId() != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A new task cannot already has an ID: " + task.getId());
        }
        task.setUser(user.get());
        Task result = taskRepository.save(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
}
