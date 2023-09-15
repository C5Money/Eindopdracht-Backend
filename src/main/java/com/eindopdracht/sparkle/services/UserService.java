package com.eindopdracht.sparkle.services;

import com.eindopdracht.sparkle.dtos.inputDto.UserInputDto;
import com.eindopdracht.sparkle.dtos.outputDto.UserOutputDto;
import com.eindopdracht.sparkle.exceptions.ResourceNotFoundException;
import com.eindopdracht.sparkle.exceptions.UsernameNotFoundException;
import com.eindopdracht.sparkle.models.CustomerCard;
import com.eindopdracht.sparkle.repositories.CustomerCardRepository;
import com.eindopdracht.sparkle.repositories.UserRepository;
import com.eindopdracht.sparkle.utils.RandomStringGenerator;
import com.eindopdracht.sparkle.models.Authority;
import com.eindopdracht.sparkle.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
//    Instance Variables
    private final UserRepository userRepository;
    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;
    private final CustomerCardRepository customerCardRepository;
//    Constructor

    public UserService(UserRepository userRepository, CustomerCardRepository customerCardRepository) {
        this.userRepository = userRepository;
        this.customerCardRepository = customerCardRepository;
    }

//    CRUD:
//    ----------------------------------------------------------------------
//    Create
//    ----------------------------------------------------------------------
    public String createUser(UserInputDto userInputDto) {
        String randomString = RandomStringGenerator.generateAlphaNumeric(20);
        userInputDto.setApikey(randomString);

        User newUser = userRepository.save(inputDtoToEntity(userInputDto));
        return newUser.getUsername();
    }
//    ----------------------------------------------------------------------
//    Read
//    ----------------------------------------------------------------------
    public UserOutputDto readUser(String username) {
        Optional<User> optionalUser = userRepository.findById(username);
        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("Username: " + username + " is invalid or doesn't exist.");
        }
        return entityToOutputDto(optionalUser.get());
    }

    public List<UserOutputDto> readUsers() {
        List<UserOutputDto> userOutputDtoList = new ArrayList<>();
        List<User> userList = userRepository.findAll();
        if(userList.isEmpty()){
            throw new UsernameNotFoundException("Users not found.");
        } else {
            for(User userEntity : userList){
                userOutputDtoList.add(entityToOutputDto(userEntity));
            }
        }
        return userOutputDtoList;
    }

    public boolean userExists(String username) {
        return userRepository.existsById(username);
    }
//    ----------------------------------------------------------------------
//    Update
//    ----------------------------------------------------------------------
    public UserOutputDto updateUser(String username, UserInputDto newUser) {
        if (!userRepository.existsById(username)) throw new UsernameNotFoundException("Username: " + username + " does not exist.");
        User updatableUser = userRepository.findById(username).get();
        User updatedUser = updateInputDtoToEntity( newUser, updatableUser);
        updatableUser.setPassword(newUser.getPassword());
        userRepository.save(updatableUser);
        return entityToOutputDto(updatedUser);
    }

    public String assignCustomerCardToUser(String username, Long cardNumber){
        Optional<User> optionalUser = userRepository.findById(username);
        Optional<CustomerCard> optionalCustomerCard = customerCardRepository.findById(cardNumber);

        if(optionalUser.isEmpty() || optionalCustomerCard.isEmpty()) {
            throw new ResourceNotFoundException("Username: " + username + " or customercard with cardnumber: " + cardNumber + " do not exist.");
        }
        User updatableUser = optionalUser.get();
        CustomerCard updatableCustomerCard = optionalCustomerCard.get();
        updatableUser.setCustomerCard(updatableCustomerCard);
        userRepository.save(updatableUser);
        return "Customercard with cardnumber: " + cardNumber + " has successfully been assigned to username: " + username + ".";
    }
//    ----------------------------------------------------------------------
//    Delete
//    ----------------------------------------------------------------------
    public void deleteUser(String username) {
        userRepository.deleteById(username);
    }
//    ----------------------------------------------------------------------
//    Authority Mappers
//    ----------------------------------------------------------------------
    public Set<Authority> getAuthorities(String username) {
        if (!userRepository.existsById(username))throw new UsernameNotFoundException("Username: " + username + " does not exist.");
        User user = userRepository.findById(username).get();
        UserOutputDto userDto = entityToOutputDto(user);
        return userDto.getAuthorities();
    }

    public void addAuthority(String username, String authority) {
        if (!userRepository.existsById(username))throw new UsernameNotFoundException("Username: " + username + " does not exist.");
        User user = userRepository.findById(username).get();
        user.addAuthority(new Authority(username, authority));
        userRepository.save(user);
    }

    public void removeAuthority(String username, String authority) {
        if (!userRepository.existsById(username))throw new UsernameNotFoundException("Username: " + username + " does not exist.");
        User user = userRepository.findById(username).get();
        Authority authorityToRemove = user.getAuthorities().stream().filter((a) -> a.getAuthority().equalsIgnoreCase(authority)).findAny().get();
        user.removeAuthority(authorityToRemove);
        userRepository.save(user);
    }

//    MAPPERS:
//    ----------------------------------------------------------------------
//    InputDto to Entity
//    ----------------------------------------------------------------------
    public User inputDtoToEntity(UserInputDto userInputDto) {
        User userEntity = new User();
        userEntity.setUsername(userInputDto.username);
        userEntity.setPassword(passwordEncoder.encode(userInputDto.password));
        userEntity.setEnabled(userInputDto.enabled);
        userEntity.setApikey(userInputDto.apikey);
        userEntity.setEmail(userInputDto.email);
        return userEntity;
    }

    public User updateInputDtoToEntity(UserInputDto userInputDto, User userEntity){
        userEntity.setUsername(userInputDto.username);
        userEntity.setPassword(passwordEncoder.encode(userInputDto.password));
        userEntity.setEnabled(userInputDto.enabled);
        userEntity.setApikey(userInputDto.apikey);
        userEntity.setEmail(userInputDto.email);
        return userEntity;
    }
//    ----------------------------------------------------------------------
//    Entity to OutputDto
//    ----------------------------------------------------------------------
    public static UserOutputDto entityToOutputDto(User user) {
        UserOutputDto userOutputDto = new UserOutputDto();
        userOutputDto.username = user.getUsername();
        userOutputDto.password = user.getPassword();
        userOutputDto.apikey = user.getApikey();
        userOutputDto.email = user.getEmail();
        userOutputDto.authorities = user.getAuthorities();
        userOutputDto.customerCard = user.getCustomerCard();
        userOutputDto.workSchedule = user.getWorkSchedule();
        return userOutputDto;
    }
}
