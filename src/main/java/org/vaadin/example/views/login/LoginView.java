package org.vaadin.example.views.login;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.login.AbstractLogin;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.inject.Inject;
import org.vaadin.example.service.AuthService;

@Route("login")
public class LoginView extends VerticalLayout {

    private final AuthService authService;
    private final LoginForm loginForm = createLoginForm();

    @Inject
    public LoginView(AuthService authService) {
        this.authService = authService;
        setLayout();
        setupLoginForm();
        addLoginFormAndRegisterLink();
    }

    private void setLayout() {
        addClassName("login-view");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
    }

    private LoginForm createLoginForm() {
        LoginForm loginForm = new LoginForm();
        loginForm.setForgotPasswordButtonVisible(false);
        loginForm.addLoginListener(this::authenticateUser);
        return loginForm;
    }

    private void authenticateUser(AbstractLogin.LoginEvent event) {
        try {
            authService.authenticate(event.getUsername(), event.getPassword());
            UI.getCurrent().navigate("");
        } catch (AuthService.AuthException e) {
            handleAuthenticationFailure();
        }
    }

    private void handleAuthenticationFailure() {
        Notification.show("Invalid credentials!")
                .addThemeVariants(NotificationVariant.LUMO_ERROR);
        loginForm.setError(true);
    }

    private void addLoginFormAndRegisterLink() {
        Anchor registerLink = new Anchor("register", new Span("Don't have an account? Register"));
        add(loginForm, registerLink);
    }

    private void setupLoginForm() {
        loginForm.setForgotPasswordButtonVisible(false);
    }
}