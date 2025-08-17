package mkn.api.my_registry_api.services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import mkn.api.my_registry_api.entities.User;
import mkn.api.my_registry_api.entities.dtos.UserPatchRequest;
import mkn.api.my_registry_api.entities.enums.UserRole;
import mkn.api.my_registry_api.repositories.UserRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository repository;


    public List<User> findAll() {
        return repository.findAll();
    }

    public User findById(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id)); //!-1
    }

    public User findByEmail(String email) {
        return repository.findUserByEmail(email);
    }

    public User insert(User user) {
        return repository.save(user);
    }

    public void deleteByEmail(String email) {
       User userToDelete = findByEmail(email);
       repository.deleteById(userToDelete.getId());

    }

    public User partialUpdate(String email, UserPatchRequest patchRequest) {
        User existingUser = repository.findUserByEmail(email);
        if(existingUser == null) {
            throw new EntityNotFoundException();
        }

        if (StringUtils.hasText(patchRequest.getName())) {
            existingUser.setName(patchRequest.getName());
        }

        if (StringUtils.hasText(patchRequest.getEmail())) {
            existingUser.setEmail(patchRequest.getEmail());
        }

        if (StringUtils.hasText(patchRequest.getPassword())) {
            existingUser.setPassword(patchRequest.getPassword());
        }

        if (StringUtils.hasText(patchRequest.getCpf())) {
            existingUser.setCpf(patchRequest.getCpf());
        }
        try {
            if (UserRole.valueOf(patchRequest.getRole()) == null)
                throw new BadRequestException();
            else {
                existingUser.setRoleStatus(UserRole.valueOf(patchRequest.getRole()));
            }
        } catch (BadRequestException e) {
            return null;
//            e.printStackTrace();

        }

        return repository.save(existingUser);
    }

    public void delete(long id) {
        repository.deleteById(id);
    }


}
