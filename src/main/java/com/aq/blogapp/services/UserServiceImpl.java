package com.aq.blogapp.services;

import com.aq.blogapp.DTO.UserDTO;
import com.aq.blogapp.exceptions.ResourceNotFoundException;
import com.aq.blogapp.mappers.UserMapper;
import com.aq.blogapp.model.User;
import com.aq.blogapp.respositories.UserRepository;
import com.aq.blogapp.utils.AppUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;




@Service
public class UserServiceImpl implements UserService {


    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public UserServiceImpl(UserMapper userMapper, UserRepository userRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }


    @Override
    public List<UserDTO> getAllUsers() {

        List<UserDTO> userDTOList = new ArrayList<>();

            userDTOList = userRepository.findAll()
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
                        .orElseThrow( ()-> new ResourceNotFoundException("User", "Id", id));
            }
        }catch (NoSuchElementException ex){
            throw new ResourceNotFoundException("User", "userId", id);
        }

        return userDTOById;
    }


    @Override
    public UserDTO createUser(UserDTO userDTO) {
        UserDTO newUserDTO = new UserDTO();

//        try {
            if (!AppUtils.anyEmpty(userDTO)) {
                newUserDTO = saveAndReturnUserDTO(userMapper.userDtoToUser(userDTO));
            }
//        } catch (NullPointerException npe) {
//            npe.getMessage();
//        } catch (Exception ex) {
//            ex.getMessage();
//            newUserDTO = null;
//        }
        return newUserDTO;
    }


    @Override
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        UserDTO updatedUser = new UserDTO();
        try {
            User existingUser = userRepository
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
        }
        catch (EmptyResultDataAccessException ERDAE){
            System.out.println( ERDAE.getMessage());
            System.out.println(ERDAE.getCause());
            throw new ResourceNotFoundException("User", "id", id);
        }


    }


    //  PRIVATE methods
    private UserDTO saveAndReturnUserDTO(User user) {
        UserDTO returnedDto = new UserDTO();

            if (user != null) {
                User savedUser = userRepository.save(user);
                returnedDto = userMapper.userToUserDto(savedUser);
            }

        return returnedDto;
    }
}
