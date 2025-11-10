package com.userservice.service.implementations;

import com.core.exceptions.password.BadLengthException;
import com.core.exceptions.password.FewerCapitalsThanRequiredException;
import com.core.exceptions.password.FewerNumbersThanRequiredException;
import com.core.exceptions.password.FewerSpecialsThanRequiredException;
import com.userservice.service.interfaces.PasswordStrengthChecker;
import com.userservice.service.interfaces.QuadConsumer;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;


@Component
public class PasswordStrengthCheckerImpl implements PasswordStrengthChecker {
    private static final int MIN_LENGTH = 8, MAX_LENGTH = 24;
    private static final List<Character> SPECIALS = Arrays.asList('#', '@', '$', '%', '&', '(', ')', '*', '+', '^', '-', '/', '\\', ':', ';'
    , '.', ',', '<', '>', '=', '?', '[', ']', '_');
    private static final int MIN_SPECIALS = 2;
    private static final int MIN_CAPITALS = 1;
    private static final int MIN_NUMBERS = 3;

    @Override
    public void check(String password){
        QuadConsumer<String, Function<String, Long>, Integer, Class<? extends RuntimeException>> consumer =
                (pass, func, num, ex) -> {
                    if(func.apply(pass) < num) throwing(ex);
                };
        consumer.accept(password, PasswordStrengthCheckerImpl::countNumbers, MIN_NUMBERS, FewerNumbersThanRequiredException.class);
        consumer.accept(password, PasswordStrengthCheckerImpl::countSpecials, MIN_SPECIALS, FewerSpecialsThanRequiredException.class);
        consumer.accept(password, PasswordStrengthCheckerImpl::countCapitals, MIN_CAPITALS, FewerCapitalsThanRequiredException.class);
        checkLength(password);
    }


    private static long countNumbers(String password){
        return password.chars().filter(Character::isDigit).count();
    }

    private static long countSpecials(String password){
        return password.chars().filter(SPECIALS::contains).count();
    }

    private static long countCapitals(String password){
        return password.chars().filter(Character::isUpperCase).count();
    }

    private static void checkLength(String password){
        if(password.length() < MIN_LENGTH) throw new BadLengthException("Length of your password is less, than required");
        if(password.length() > MAX_LENGTH) throw new BadLengthException("Length of your password is more, than required");
    }

    private void throwing(Class<? extends RuntimeException> exception){
        try{
            throw exception.getDeclaredConstructor().newInstance();
        }catch(ReflectiveOperationException ex){
            throw new RuntimeException("Could not instantiate exception: " + exception.getName(), ex);
        }
    }

    @Override
    public List<Character> showSpecials() {
        return SPECIALS;
    }
}
