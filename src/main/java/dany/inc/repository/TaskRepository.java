package dany.inc.repository;

import dany.inc.domain.Task;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Task entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query("select task from Task task where task.user.login = ?#{principal.username}")
    List<Task> findByUserIsCurrentUser();

    @Query("select t from Task t where t.user.id = ?1")
    List<Task> findByTasksByUserId(long userId);
}
