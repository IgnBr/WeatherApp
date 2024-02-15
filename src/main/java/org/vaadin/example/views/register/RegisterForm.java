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
import jakarta.inject.Inject;
import lombok.Getter;
import org.vaadin.example.entity.User;
import org.vaadin.example.service.UserService;

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

    // TODO: Fix bug where registration is allowed when binder shows errors
    private Button createSubmitButton() {
        Button submitButton = new Button("Sign Up", event -> {
            if (Objects.equals(username.getValue(), "") || Objects.equals(password.getValue(), "")) return;
            User user = new User(username.getValue(), password.getValue());
            try {
                userService.save(user);
                handleSuccessfulRegistration();
            } catch (Exception e) {
                handleRegistrationFailure();
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