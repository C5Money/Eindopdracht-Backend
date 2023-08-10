package com.example.sparkle.services;

import com.example.sparkle.dtos.inputDto.UserInputDto;
import com.example.sparkle.dtos.outputDto.UserOutputDto;
import com.example.sparkle.exceptions.ResourceNotFoundException;
import com.example.sparkle.models.CustomerCard;
import com.example.sparkle.models.User;
import com.example.sparkle.repositories.CustomerCardRepository;
import com.example.sparkle.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
//    Instance Variables
    private final UserRepository userRepository;
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
    public Long createUser(UserInputDto userInputDto){
//        Optional<User> optionalUser = userRepository.findById(userInputDto.userId);
//        if(optionalUser.isPresent()){
//            throw new ResourceNotFoundException("User id: " + userInputDto.userId + " already exists.");
//        }
        User newUserEntity = inputDtoToEntity(userInputDto);
        userRepository.save(newUserEntity);
        return newUserEntity.getId();
    }
//    ----------------------------------------------------------------------
//    Read
//    ----------------------------------------------------------------------
    public UserOutputDto readOneUserById(Long id){
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isEmpty()){
            throw new ResourceNotFoundException("User-id: " + id + " is invalid or doesn't exist.");
        }
        return entityToOutputDto(optionalUser.get());
    }

    public UserOutputDto readOneUserByUserName(String userName){
        User foundUser = userRepository.findByUserNameContainingIgnoreCase(userName).orElseThrow(()-> new ResourceNotFoundException("User not found."));
        return entityToOutputDto(foundUser);
    }

    public List<UserOutputDto> readAllUsers(){
        List<User> userList = userRepository.findAll();
        List<UserOutputDto> userOutputDtoList = new ArrayList<>();
        if(userList.isEmpty()){
            throw new ResourceNotFoundException("Users not found.");
        } else {
            for(User userEntity : userList){
                userOutputDtoList.add(entityToOutputDto(userEntity));
            }
        }
        return userOutputDtoList;
    }
//    ----------------------------------------------------------------------
//    Update
//    ----------------------------------------------------------------------
    public UserOutputDto updateOneUser(UserInputDto userInputDto, Long id){
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent()){
            User updatableUser = optionalUser.get();
            User updatedUser = updateInputDtoToEntity(userInputDto, updatableUser);

            userRepository.save(updatedUser);
            return entityToOutputDto(updatedUser);
        } else {
            throw new ResourceNotFoundException("User-id: " + id + " does not exist.");
        }
    }

    public String assignCustomerCardToUser(Long id, Long cardNumber){
        Optional<User> optionalUser = userRepository.findById(id);
        Optional<CustomerCard> optionalCustomerCard = customerCardRepository.findById(cardNumber);

        if(optionalUser.isEmpty() && optionalCustomerCard.isEmpty()) {
            throw new ResourceNotFoundException("User with id: " + id + " or customer card with cardnumber: " + cardNumber + " do not exist.");
        }
        User updatableUser = optionalUser.get();
        CustomerCard updatableCustomerCard = optionalCustomerCard.get();
        updatableUser.setCustomerCard(updatableCustomerCard);
        User updatedUser = userRepository.save(updatableUser);
        return "Customercard with cardnumber: " + cardNumber + " has successfully been assigned to user-id: " + id + ".";
    }
//    ----------------------------------------------------------------------
//    Delete
//    ----------------------------------------------------------------------
    public void deleteOneUserById(Long id){
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isEmpty()){
            throw new ResourceNotFoundException("This inventory item's id: " + id + " is already deleted or doesn't exist.");
        }
        User foundUser = optionalUser.get();
        userRepository.delete(foundUser);
    }
//    MAPPERS:
//    ----------------------------------------------------------------------
//    InputDto to Entity
//    ----------------------------------------------------------------------
    public User inputDtoToEntity(UserInputDto userInputDto){
        User userEntity = new User();
        userEntity.setId(userInputDto.userId);
        userEntity.setUserName(userInputDto.userName);
        userEntity.setEmail(userInputDto.email);
        userEntity.setPassword(userInputDto.password);
        userEntity.setFirstName(userInputDto.firstName);
        userEntity.setLastName(userInputDto.lastName);
        userEntity.setZipCode(userInputDto.zipCode);
        userEntity.setAddress(userInputDto.address);
        userEntity.setPhoneNumber(userInputDto.phoneNumber);
        return userEntity;
    }

    public User updateInputDtoToEntity(UserInputDto userInputDto, User userEntity){
//        userEntity.setId(userInputDto.userId);
        userEntity.setUserName(userInputDto.userName);
        userEntity.setEmail(userInputDto.email);
        userEntity.setPassword(userInputDto.password);
        userEntity.setFirstName(userInputDto.firstName);
        userEntity.setLastName(userInputDto.lastName);
        userEntity.setZipCode(userInputDto.zipCode);
        userEntity.setAddress(userInputDto.address);
        userEntity.setPhoneNumber(userInputDto.phoneNumber);
        return userEntity;
    }

//    ----------------------------------------------------------------------
//    Entity to OutputDto
//    ----------------------------------------------------------------------
    public UserOutputDto entityToOutputDto(User user){
        UserOutputDto userOutputDto = new UserOutputDto();
        userOutputDto.id = user.getId();
        userOutputDto.userName = user.getUserName();
        userOutputDto.firstName = user.getFirstName();
        userOutputDto.lastName = user.getLastName();
        userOutputDto.zipCode = user.getZipCode();
        userOutputDto.address = user.getAddress();
        userOutputDto.phoneNumber = user.getPhoneNumber();
        userOutputDto.customerCard = user.getCustomerCard();
        userOutputDto.workSchedules = user.getWorkSchedules();
        return userOutputDto;
    }



}
