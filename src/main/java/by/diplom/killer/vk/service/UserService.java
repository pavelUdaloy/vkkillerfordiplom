package by.diplom.killer.vk.service;

import by.diplom.killer.vk.controller.http.entity.RegisterUserDto;
import by.diplom.killer.vk.controller.http.entity.UserDto;
import by.diplom.killer.vk.entity.User;
import by.diplom.killer.vk.exception.BaseKillerException;
import by.diplom.killer.vk.exception.NotFoundException;
import by.diplom.killer.vk.repository.UserRepository;
import by.diplom.killer.vk.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Transactional
    public UserDto registerUser(RegisterUserDto registerUserDto) {
        Optional<User> userByEmail = userRepository.findByEmail(registerUserDto.getEmail());
        if (userByEmail.isPresent()) {
            return null;
        }

        User user = userMapper.mapRegisterUserDtoToUser(registerUserDto);

        userRepository.save(user);

        return userMapper.mapUserToRegisteredUserDto(user);
    }

    @Transactional(readOnly = true)
    public User getUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isEmpty()) {
            throw new NotFoundException(String.format("User with email={%s}", email));
        }

        return user.get();
    }

    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) {
            throw new NotFoundException(String.format("User with id={%s}", id));
        }

        return user.get();
    }

    public User verifyUser(String email, String password) {
        User user = getUserByEmail(email);

        if (!password.equals(user.getPassword())) {
            throw new BaseKillerException("Sorry", "Wrong password");
        }

        return user;
    }
}
