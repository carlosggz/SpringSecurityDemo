package es.security.example.springsecuritydemo.domain.valueobjects;

import es.security.example.springsecuritydemo.domain.exceptions.InvalidArgumentsException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

@Getter
@EqualsAndHashCode
public class EmailValueObject implements ValueObject {
    private static  final String EMAIL_PATTERN = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    private final String email;

    public EmailValueObject(String email) throws InvalidArgumentsException {

        if (StringUtils.isEmpty(email) || !pattern.matcher(email).matches())
            throw new InvalidArgumentsException("Invalid email address");

        this.email = email;
    }
}
