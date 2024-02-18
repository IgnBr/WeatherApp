package org.vaadin.example.views.register;

import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.ValueContext;
import lombok.Getter;
import org.vaadin.example.views.register.dtos.RegisterDto;

public class RegisterBinder {

    private final RegisterForm registerForm;

    private boolean enablePasswordValidation;

    @Getter
    private BeanValidationBinder<RegisterDto> binder;

    public RegisterBinder(RegisterForm registerForm) {
        this.registerForm = registerForm;
    }

    public void addBindingAndValidation() {
        binder = new BeanValidationBinder<>(RegisterDto.class);
        binder.bindInstanceFields(registerForm);

        binder.forField(registerForm.getPassword())
                .withValidator(this::passwordValidator).bind("password");

        registerForm.getPasswordConfirm().addValueChangeListener(e -> {
            enablePasswordValidation = true;
            binder.validate();
        });

        binder.setStatusLabel(registerForm.getErrorMessageField());
    }

    private ValidationResult passwordValidator(String pass1, ValueContext ctx) {
        if (pass1 == null || pass1.length() < 8) {
            return ValidationResult.error("Password should be at least 8 characters long");
        }

        if (!enablePasswordValidation) {
            enablePasswordValidation = true;
            return ValidationResult.ok();
        }

        String pass2 = registerForm.getPasswordConfirm().getValue();

        if (pass1.equals(pass2)) {
            return ValidationResult.ok();
        }

        return ValidationResult.error("Passwords do not match");
    }

}