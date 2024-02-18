package org.vaadin.example.views.register;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.inject.Inject;

// TODO: Add css styling
@CssImport("./themes/my-theme/register.css")
@Route("register")
public class RegisterView extends VerticalLayout {

    @Inject
    public RegisterView(RegisterForm registerForm) {
        setSizeFull();
        RegisterBinder registrationFormBinder = new RegisterBinder(registerForm);
        registrationFormBinder.addBindingAndValidation();
        registerForm.setBinder(registrationFormBinder.getBinder());
        registerForm.setClassName("register-form");
        add(registerForm);
    }
}