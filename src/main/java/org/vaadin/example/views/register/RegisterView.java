package org.vaadin.example.views.register;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.inject.Inject;

// TODO: Add css styling
@Route("register")
public class RegisterView extends VerticalLayout {

    @Inject
    public RegisterView(RegisterForm registerForm) {
        setSizeFull();
        setHorizontalComponentAlignment(Alignment.CENTER, registerForm);
        RegisterBinder registrationFormBinder = new RegisterBinder(registerForm);
        registrationFormBinder.addBindingAndValidation();
        add(registerForm);
    }
}