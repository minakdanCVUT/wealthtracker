package com.userservice.service.implementations;

import com.core.exceptions.NullableViolation;
import com.core.exceptions.ServerException;
import com.core.exceptions.UniquenessViolation;
import com.userservice.model.User;
import com.userservice.repository.UserRepository;
import com.userservice.service.interfaces.QueryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueryServiceImpl implements QueryService {
    private final UserRepository repository;

    private final String PostgreSQLUniquenessViolation = "23505";
    private final String PostgreSQLNullableViolation = "23502";


    @Override
    public void saveUser(User user) throws UniquenessViolation, NullableViolation, ServerException{
        try{
            repository.save(user);
        }catch (DataIntegrityViolationException exception){
            Throwable cause = exception.getCause();
            if(cause instanceof ConstraintViolationException cve){
                String sqlState = cve.getSQLState();
                if(sqlState.equals(PostgreSQLUniquenessViolation)){
                    String constraintName = cve.getConstraintName();
                    throw new UniquenessViolation(constraintName);
                }else if(sqlState.equals(PostgreSQLNullableViolation)){
                    throw new NullableViolation();
                }
            }else{
                throw new ServerException();
            }
        }
    }

    @Override
    public User findById(Long userId) throws EntityNotFoundException{
        return repository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User with this id " + userId + " not found"));
    }
}
