package es.security.example.springsecuritydemo.domain.valueobjects;

import es.security.example.springsecuritydemo.domain.exceptions.InvalidArgumentsException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

@Getter
@EqualsAndHashCode
public class IdValueObject implements ValueObject{
    private final String id;

    public IdValueObject() {
        this.id = UUID.randomUUID().toString();
    }

    public IdValueObject(String id) throws InvalidArgumentsException {

        if (StringUtils.isBlank(id))
            throw new InvalidArgumentsException("Invalid id");

        try {
            this.id = UUID.fromString(id).toString();
        }
        catch(Exception ex) {
            throw new InvalidArgumentsException("Invalid id");
        }
    }
}
