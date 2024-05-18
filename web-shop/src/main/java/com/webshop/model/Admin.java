package com.webshop.model;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_role",discriminatorType = DiscriminatorType.STRING)


@DiscriminatorValue(value = "ADMINISTRATOR")
public class Admin extends  Korisnik{
}
