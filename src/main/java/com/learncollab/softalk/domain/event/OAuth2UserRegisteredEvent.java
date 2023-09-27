package com.learncollab.softalk.domain.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;

@Getter
@Setter
public class OAuth2UserRegisteredEvent extends ApplicationEvent {
    private final String email;
    private final String name;
    private final String registrationId;

    public OAuth2UserRegisteredEvent(Object source, String email, String name, String registrationId) {
        super(source);
        this.email = email;
        this.name = name;
        this.registrationId = registrationId;
    }
}
