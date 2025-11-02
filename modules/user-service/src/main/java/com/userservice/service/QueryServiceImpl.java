package com.userservice.service;

import com.core.exceptions.NullableViolation;
import com.core.exceptions.ServerException;
import com.core.exceptions.UniquenessViolation;
import com.core.exceptions.UserAlreadyExistsException;
import com.userservice.model.User;
import com.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueryServiceImpl implements QueryService{
    private final UserRepository repository;

    private final String PostgreSQLUniquenessViolation = "23505";
    private final String PostgreSQLNullableViolation = "23502";


    @Override
    public void saveUser(User user) {
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
}
