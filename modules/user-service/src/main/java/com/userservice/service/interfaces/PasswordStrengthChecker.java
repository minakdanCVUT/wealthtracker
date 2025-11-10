package com.userservice.service.interfaces;

import java.util.List;

public interface PasswordStrengthChecker {
    void check(String password);
    List<Character> showSpecials();
}
