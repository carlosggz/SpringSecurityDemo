package es.security.example.springsecuritydemo.domain.valueobjects;

import es.security.example.springsecuritydemo.domain.exceptions.InvalidArgumentsException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
@EqualsAndHashCode
public class TitleValueObject implements ValueObject {
    private final String title;

    public TitleValueObject(String title) throws InvalidArgumentsException {

        if (StringUtils.isBlank(title))
            throw new InvalidArgumentsException("Invalid title");

        this.title = title;
    }
}
