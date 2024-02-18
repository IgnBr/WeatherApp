package org.vaadin.example.views.register;

import com.vaadin.flow.component.HasValueAndElement;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import jakarta.inject.Inject;
import lombok.Getter;
import lombok.Setter;
import org.vaadin.example.entity.User;
import org.vaadin.example.service.UserService;
import org.vaadin.example.views.register.dtos.RegisterDto;

import java.util.Objects;
import java.util.stream.Stream;

public class RegisterForm extends VerticalLayout {

    private final UserService userService;

    private final H2 title = new H2("Sign Up");

    private final TextField username = new TextField("Username");

    @Getter
    private final PasswordField password = new PasswordField("Password");

    @Getter
    private final PasswordField passwordConfirm = new PasswordField("Confirm password");

    @Getter
    private final Span errorMessageField = new Span();

    @Getter
    private final Button submitButton = createSubmitButton();

    @Getter @Setter
    private BeanValidationBinder<RegisterDto> binder;

    @Inject
    public RegisterForm(UserService userService) {
        this.userService = userService;
        setLayout();
        setRequiredIndicatorsVisible(username, password, passwordConfirm);
        addComponents();
    }

    private void setLayout() {
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
    }

    private Button createSubmitButton() {
        Button submitButton = new Button("Sign Up", event -> {
            if (binder.isValid()) {
                try {
                    RegisterDto registerDto = new RegisterDto();
                    binder.writeBean(registerDto);
                    User user = new User(registerDto.getUsername(), registerDto.getPassword());
                    userService.save(user);
                    handleSuccessfulRegistration();
                } catch (ValidationException e) {
                    handleRegistrationFailure();
                }
            }
        });
        submitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        return submitButton;
    }

    private void handleSuccessfulRegistration() {
        Notification.show("Successfully registered!")
                .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        UI.getCurrent().getPage().setLocation("login");
    }

    private void handleRegistrationFailure() {
        Notification.show("Registration failed!")
                .addThemeVariants(NotificationVariant.LUMO_ERROR);
    }

    private void setRequiredIndicatorsVisible(HasValueAndElement<?, ?>... components) {
        Stream.of(components).forEach(comp -> comp.setRequiredIndicatorVisible(true));
    }

    private void addComponents() {
        Anchor loginLink = new Anchor("login", new Span("Already have an account? Login"));
        add(title, username, password, passwordConfirm, errorMessageField, submitButton, loginLink);
    }
}