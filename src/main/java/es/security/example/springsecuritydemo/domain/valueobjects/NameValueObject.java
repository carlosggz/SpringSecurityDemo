package es.security.example.springsecuritydemo.domain.valueobjects;

import es.security.example.springsecuritydemo.domain.exceptions.InvalidArgumentsException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
@EqualsAndHashCode
public class NameValueObject implements ValueObject {
    private final String firstName;
    private final String lastName;

    public NameValueObject(String firstName, String lastName) throws InvalidArgumentsException {

        if (StringUtils.isBlank(firstName))
            throw new InvalidArgumentsException("Invalid first name");

        if (StringUtils.isBlank(lastName))
            throw new InvalidArgumentsException("Invalid last name");

        this.firstName = firstName;
        this.lastName = lastName;
    }
}
