package com.backend.boilerplate.service;

import com.backend.boilerplate.repository.RoleRepository;
import com.backend.boilerplate.repository.UserHistoryRepository;
import com.backend.boilerplate.repository.UserRepository;
import com.backend.boilerplate.repository.UserRoleRepository;
import com.backend.boilerplate.dto.CreateUserDto;
import com.backend.boilerplate.dto.UpdateUserDto;
import com.backend.boilerplate.dto.UserDto;
import com.backend.boilerplate.dto.UserRoleDto;
import com.backend.boilerplate.entity.Role;
import com.backend.boilerplate.entity.Status;
import com.backend.boilerplate.entity.User;
import com.backend.boilerplate.entity.UserHistory;
import com.backend.boilerplate.entity.UserRole;
import com.backend.boilerplate.exception.RoleNotFoundException;
import com.backend.boilerplate.exception.UserNotFoundException;
import com.backend.boilerplate.modelmapper.RoleMapper;
import com.backend.boilerplate.modelmapper.UserMapper;
import com.backend.boilerplate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.backend.boilerplate.constant.Role.DEFAULT;

/**
 * Implementation of {@link UserService}.
 *
 * @author sarvesh
 * @version 0.0.2
 * @since 0.0.1
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserHistoryRepository userHistoryRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    //    @Autowired
    //    private IAuthenticationFacade authenticationFacade;

    private final TransactionTemplate transactionTemplate;

    public UserServiceImpl(PlatformTransactionManager transactionManager) {
        this.transactionTemplate = new TransactionTemplate(transactionManager);
        //this.transactionTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_SERIALIZABLE);
    }

    @Override
    public UserDto prepareUserDto(User user) {
        UserDto userDto = userMapper.convertToDto(user);

        List<Role> roles = user.getUserRoles().stream()
            .filter(userRole -> !userRole.getRole().getName().equals(DEFAULT.getName()))
            .map(UserRole::getRole)
            .collect(Collectors.toList());
        List<UserRoleDto> roleDtos = roleMapper.convertToUserRoleDtos(roles);
        userDto.setRoles(roleDtos);

        return userDto;
    }

    /**
     * @param uuid uuid of user
     * @return UserDto
     */
    @Override
    @Transactional
    public UserDto getUserByUuid(UUID uuid) {
        Optional<User> userOptional = userRepository.findByUuid(uuid);
        User user = userOptional.orElseThrow(UserNotFoundException::new);
        return prepareUserDto(user);
    }

    @Override
    @Transactional
    public UserDto createUser(CreateUserDto createUserDto) {
        Long createdBy = -1l;
        //        userRepository.findIdByUuid(authenticationFacade.getUserId())
        //            .orElseThrow(InvalidUserException::new);

        UserDto userDto = userMapper.convertToDto(createUserDto);

        User user = userMapper.convertToEntity(userDto);
        user.setStatus(Status.CREATED);

        List<UserRole> userRoles = createUserDto.getRoles().stream()
            .map(roleUuid -> roleRepository.findByUuid(roleUuid).orElseThrow(RoleNotFoundException::new))
            .map(role -> new UserRole(user, role, createdBy))
            .collect(Collectors.toList());

        Role defaultRole = roleRepository.findByNameIgnoreCase(DEFAULT.getName())
            .orElseThrow(RoleNotFoundException::new);
        userRoles.add(new UserRole(user, defaultRole, createdBy));

        user.getUserRoles().addAll(userRoles);

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                User userPersisted = userRepository.saveAndFlush(user);
                UserHistory userHistory = UserHistory.builder()
                    .userId(user.getId())
                    .uuid(user.getUuid())
                    .email(user.getEmail())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .status(user.getStatus())
                    .performedBy(createdBy)
                    .build();
                userHistoryRepository.save(userHistory);

                //                userRoles.forEach(userRole -> userRole.setUser(userPersisted));
                //                userRoleRepository.saveAll(userRoles);
            }
        });
        return prepareUserDto(user);
    }

    @Override
    @Transactional
    public UserDto updateUser(UpdateUserDto updateUserDto) {
        Long updatedBy = -1l;
        //        userRepository.findIdByUuid(authenticationFacade.getUserId())
        //            .orElseThrow(InvalidUserException::new);

        UUID uuid = updateUserDto.getUuid();
        User user = userRepository.findByUuid(uuid)
            .orElseThrow(UserNotFoundException::new);

        UserDto userDto = userMapper.convertToDto(updateUserDto);
        userMapper.mergeToEntity(userDto, user);
        user.setStatus(Status.UPDATED);

        List<UserRole> existingUserRoles = user.getUserRoles();
        Set<UserRole> newUserRoles = updateUserDto.getRoles().stream()
            .map(roleUuid -> roleRepository.findByUuid(roleUuid).orElseThrow(RoleNotFoundException::new))
            .map(role -> new UserRole(user, role, updatedBy))
            .collect(Collectors.toSet());

        user.getUserRoles().stream()
            .filter(userRole -> userRole.getRole().getName().equals(DEFAULT.getName()))
            .findFirst()
            .ifPresent(newUserRoles::add);

        //        List<UserRole> existingUserRolesToDelete = new ArrayList<>(existingUserRoles);
        //        existingUserRolesToDelete.removeAll(newUserRoles);
        //        newUserRoles.removeAll(existingUserRoles);

        user.getUserRoles().clear();
        user.getUserRoles().addAll(newUserRoles);

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                userRepository.saveAndFlush(user);
                UserHistory userHistory = UserHistory.builder()
                    .userId(user.getId())
                    .uuid(user.getUuid())
                    .email(user.getEmail())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .status(user.getStatus())
                    .performedBy(updatedBy)
                    .build();
                userHistoryRepository.save(userHistory);

                //                if (!existingUserRolesToDelete.isEmpty()) {
                //                    userRoleRepository.deleteAll(existingUserRolesToDelete);
                //                }
                //                if (!newUserRoles.isEmpty()) {
                //                    userRoleRepository.saveAll(newUserRoles);
                //                }
            }
        });
        return prepareUserDto(user);
    }

    @Override
    @Transactional
    public void deleteUser(UUID uuid) {
        Long deletedBy = -1l;
        //        userRepository.findIdByUuid(authenticationFacade.getUserId())
        //            .orElseThrow(InvalidUserException::new);

        User user = userRepository.findByUuid(uuid)
            .orElseThrow(UserNotFoundException::new);

        user.setStatus(Status.DELETED);

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                //userRoleRepository.deleteAll(user.getUserRoles());
                userRepository.delete(user);
                UserHistory userHistory = UserHistory.builder()
                    .userId(user.getId())
                    .uuid(user.getUuid())
                    .email(user.getEmail())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .status(user.getStatus())
                    .performedBy(deletedBy)
                    .build();
                userHistoryRepository.save(userHistory);
            }
        });
    }
}
