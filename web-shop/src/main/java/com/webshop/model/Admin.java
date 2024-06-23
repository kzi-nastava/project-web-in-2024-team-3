package com.webshop.model;

import com.webshop.dtos.RegisterDto;
import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_role",discriminatorType = DiscriminatorType.STRING)


@DiscriminatorValue(value = "ADMIN")
public class Admin extends  Korisnik{

    public Admin() {}

    public Admin(RegisterDto registerDto) {
        super(registerDto);
    }
}
