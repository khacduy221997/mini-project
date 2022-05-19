package grass.micro.apps.service.auth;

import java.util.List;

import grass.micro.apps.model.auth.User;
import grass.micro.apps.service.GenericService;

public interface UserService extends GenericService<User, Integer> {

    
    /**
     * Get {@link User} by given id.
     * @param id given id.
     * @return {@link User} instance.
     */
    User getById(int id);
    
    /**
     * Get all {@link User}.
     * @return {@link List} of {@link User} instance.
     */
    List<User> getAll();
    
    /**
     * Get all available {@link User} (VALID and DRAFT status).
     * @return {@link List} of {@link User} instance.
     */
    List<User> getAllAvailable();

    /**
     * Get an {@link User} by given account id.
     * @return {@link List} of {@link User} instance.
     */
    User getByAccountId(int id);
}
