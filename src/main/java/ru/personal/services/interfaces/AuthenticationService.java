package ru.personal.services.interfaces;

import ru.personal.form.UserForm;
import ru.personal.models.Token;

/**
 * Date 03.07.2018
 *
 * @author Hursanov Sulaymon
 * @version v1.0
 **/
public interface AuthenticationService {
    Token login(UserForm userForm);
}
