package com.aq.blogapp.services.impl;

import com.aq.blogapp.DTO.UserDTO;
import com.aq.blogapp.constants.AppConstants;
import com.aq.blogapp.exceptions.ResourceNotFoundException;
import com.aq.blogapp.mappers.UserMapper;
import com.aq.blogapp.model.Role;
import com.aq.blogapp.model.User;
import com.aq.blogapp.respositories.RoleRepository;
import com.aq.blogapp.respositories.UserRepository;
import com.aq.blogapp.services.UserService;
import com.aq.blogapp.utils.AppUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public UserServiceImpl(UserMapper userMapper, UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }


    @Override
    public List<UserDTO> getAllUsers() {

        List<UserDTO> userDTOList = new ArrayList<>();

        userDTOList = userRepository
                .findAll()
                .stream()
                .map(userMapper::userToUserDto)
                .collect(Collectors.toList());

        return userDTOList;
    }


    @Override
    public UserDTO getUserById(Long id) {
        UserDTO userDTOById = null;

        try {
            if (id != null) {
                userDTOById = userRepository
                        .findById(id)
                        .map(userMapper::userToUserDto)
                        .orElseThrow(() -> new ResourceNotFoundException("User", "Id", id));
            }
        } catch (NoSuchElementException ex) {
            throw new ResourceNotFoundException("User", "userId", id);
        }

        return userDTOById;
    }


    @Override
    public UserDTO createUser(UserDTO userDTO) {
        UserDTO newUserDTO = new UserDTO();


        if (!AppUtils.anyEmpty(userDTO)) {
            newUserDTO = saveAndReturnDTO(userMapper.userDtoToUser(userDTO));
        }

        return newUserDTO;
    }


    @Override
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        UserDTO updatedUser = new UserDTO();
        User existingUser = new User();
        try {
            existingUser = userRepository
                    .findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

            existingUser.setFirstName(userDTO.getFirstName());
            existingUser.setLastName(userDTO.getLastName());
            existingUser.setEmail(userDTO.getEmail());
            existingUser.setPassword(userDTO.getPassword());
            existingUser.setAbout(userDTO.getAbout());

            updatedUser = userMapper.userToUserDto(userRepository.save(existingUser));

        } catch (ResourceNotFoundException RNFE) {
            RNFE.getMessage();
        }

        return updatedUser;
    }


    @Override
    public void deleteUser(Long id) {

        try {
            userRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ERDAE) {
            System.out.println(ERDAE.getMessage());
            System.out.println(ERDAE.getCause());
            throw new ResourceNotFoundException("User", "id", id);
        }

    }


    @Override
    public UserDTO registerUser(UserDTO userDTO) {

        User newUser = userMapper.userDtoToUser(userDTO);

//      encoding the password
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));

//        roles
        Role role = roleRepository.findById(AppConstants.NORMAL_USER).get();
        System.out.println("find role by id: " + role.toString());
        newUser.getRoles().add(role);

        User registeredUser = userRepository.save(newUser);

        return userMapper.userToUserDto(registeredUser);
    }


    //  PRIVATE methods
    private UserDTO saveAndReturnDTO(User user) {
        UserDTO returnedDto = new UserDTO();
        User savedUser = new User();

        if (user != null) {
            savedUser = userRepository.save(user);
            returnedDto = userMapper.userToUserDto(savedUser);
        }

        return returnedDto;
    }


}
