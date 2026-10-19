package com.ecommerce.user.services;


import com.ecommerce.user.dtos.AddressDTO;
import com.ecommerce.user.dtos.UserRequest;
import com.ecommerce.user.dtos.UserResponse;
import com.ecommerce.user.models.Address;
import com.ecommerce.user.models.User;
import com.ecommerce.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor //generates a constructor that takes as parameters all fields that are final or annotated with @NonNull. Useful for constructor injection in Spring (no @Autowired needed).
public class UserService {


    private final UserRepository userRepository;
    //private List<User> userList=new ArrayList<>();

    public List<UserResponse> getAllUsers() {
         List<User> userList=userRepository.findAll();
         return  userRepository.findAll().stream()
                 .map(this::mapUserToUserResponse)
                 .toList();
    }

    public Optional<UserResponse> getUserById(String id) {

        //Method 1: Without Stream API
//        for(User user:userList){
//            if(user.getId().equals(id)){
//               return user;
//            }
//        }
//        return null;

        //Method 2: With Stream API
//        return userList.stream()
//                .filter(user -> user.getId().equals(id))
//                .findFirst();

        return userRepository.findById(id)
                .map(this::mapUserToUserResponse);
    }
    public UserResponse createUser( UserRequest userRequest) {
        User user = new User();
        updateUserFromRequest(user,userRequest);
        User savedUser = userRepository.save(user);
        return mapUserToUserResponse(savedUser);
    }

    //boolean - either true or false
    //Boolean - can be null too
    //Optional<Boolean> - can be true,false or null
    //Difference between Optional<Boolean> and Boolean is that Optional<Boolean> explicitly indicates the possibility of absence of a value, while Boolean can be null but doesn't convey that meaning as clearly.
    //Difference between OrElse and OrElseGet -> OrElse always evaluates the argument, even if it's not needed.
    //OrElseGet only evaluates the argument if the Optional is empty.

    public boolean updateUser(String id, UserRequest updatedUserRequest) {
//             return userList.stream()
//              .filter(u->u.getId().equals(id))
//              .findFirst()
//              .map(existingUser -> {
//                    existingUser.setFirstName(updatedUser.getFirstName());
//                    existingUser.setLastName(updatedUser.getLastName());
//                  return true;
//              }).orElse(false);

//        return  userRepository.findById(id)
//                .map(existingUser -> {
//                    existingUser.setFirstName(updatedUser.getFirstName());
//                    existingUser.setLastName(updatedUser.getLastName());
//                    userRepository.save(existingUser);
//                    return true;
//                }).orElse(false);

        return  userRepository.findById(id)
                .map(existingUser -> {
                    updateUserFromRequest(existingUser,updatedUserRequest);
                    userRepository.save(existingUser);
                    return true;
                }).orElse(false);
    }

    private UserResponse mapUserToUserResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(String.valueOf(user.getId()));
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setEmail(user.getEmail());
        userResponse.setPhone(user.getPhone());
        userResponse.setRole(user.getRole().name());
        // Map Address to AddressDTO
        if (user.getAddress() != null) {
            AddressDTO addressDTO = new AddressDTO();
            addressDTO.setStreet(user.getAddress().getStreet());
            addressDTO.setCity(user.getAddress().getCity());
            addressDTO.setState(user.getAddress().getState());
            addressDTO.setCountry(user.getAddress().getCountry());
            addressDTO.setZipCode(user.getAddress().getZipCode());
            userResponse.setAddress(addressDTO);
        }
        return userResponse;
    }

    private void updateUserFromRequest(User user, UserRequest userRequest) {

        if(userRequest.getFirstName()!=null){
            user.setFirstName(userRequest.getFirstName());
        }
        if(userRequest.getLastName()!=null){
            user.setLastName(userRequest.getLastName());
        }

        if(userRequest.getEmail()!=null){
            user.setEmail(userRequest.getEmail());
        }
        if(userRequest.getPhone()!=null){
            user.setPhone(userRequest.getPhone());
        }

        // Set Address from AddressDTO
        if (userRequest.getAddress() != null) {
            AddressDTO addressDTO = userRequest.getAddress();
            Address address = new Address();
            address.setStreet(addressDTO.getStreet());
            address.setCity(addressDTO.getCity());
            address.setState(addressDTO.getState());
            address.setCountry(addressDTO.getCountry());
            address.setZipCode(addressDTO.getZipCode());
            user.setAddress(address);
        }
    }
}
