package mission_2.crud.repository;

import mission_2.crud.jpa.entity.UserEntity;
import mission_2.crud.model.UserDto;
import mission_2.crud.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class JpaUserService implements UserService {
    private final UserRepository userRepository;

    public JpaUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto create(UserDto dto) {
        UserEntity userEntity=new UserEntity();
        userEntity.setUsername(dto.getUsername());
        userEntity.setPassword(dto.getPassword());
        userEntity=this.userRepository.save(userEntity);
        return new UserDto(
                userEntity.getId(),
                userEntity.getUsername(),
                userEntity.getPassword()
        );
    }

    @Override
    public UserDto read(Long id) {
        Optional<UserEntity> userEntityOptional= this.userRepository.findById(id);
        if(userEntityOptional.isEmpty()) throw  new ResponseStatusException(HttpStatus.NOT_FOUND);
        UserEntity userEntity= userEntityOptional.get();
        return new UserDto(
                userEntity.getId(),
                userEntity.getUsername(),
                userEntity.getPassword()
        );
    }

    @Override
    public Collection<UserDto> readAll() {
        List<UserDto> userDtoList= new ArrayList<>();
        this.userRepository.findAll().forEach(userEntity -> userDtoList.add(new UserDto(
                userEntity.getId(),
                userEntity.getUsername(),
                userEntity.getPassword()
        )));
        return userDtoList;
    }

    @Override
    public boolean update(Long id, UserDto dto) {
        Optional<UserEntity> userEntityOptional= this.userRepository.findById(id);
        if(userEntityOptional.isEmpty()) throw  new ResponseStatusException(HttpStatus.NOT_FOUND);
        UserEntity userEntity= userEntityOptional.get();
        userEntity.setPassword(
                dto.getPassword()==null ? userEntity.getPassword() : dto.getPassword()
        );
        this.userRepository.save(userEntity);
        return true;
    }

    @Override
    public boolean delete(Long id) {
        Optional<UserEntity> userEntityOptional= this.userRepository.findById(id);
        if(userEntityOptional.isEmpty()) throw  new ResponseStatusException(HttpStatus.NOT_FOUND);
        UserEntity userEntity= userEntityOptional.get();
        this.userRepository.delete(userEntity);
        return true;
    }
}
