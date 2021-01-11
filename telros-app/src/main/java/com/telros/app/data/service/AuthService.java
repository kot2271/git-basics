package com.telros.app.data.service;

import com.telros.app.data.entity.Role;
import com.telros.app.data.entity.User;
import com.telros.app.views.admin.AdminView;
import com.telros.app.views.home.HomeView;
import com.telros.app.views.logout.LogoutView;
import com.telros.app.views.main.MainView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthService {

  public record AuthorizedRoute(String route, String name, Class<? extends Component> view) {}

  public class AuthException extends Exception {}

  private final UserRepository userRepository;

  public AuthService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public void authenticate(String username, String password) throws AuthException {
    User user = userRepository.getByUsername(username);
    if (user != null && user.checkPassword(password)) {
      VaadinSession.getCurrent().setAttribute(User.class, user);
      createRoutes(user.getRole());
    } else {
      throw new AuthException();
    }
  }

  private void createRoutes(Role role) {
    getAuthorizedRoutes(role).stream()
        .forEach(
            route ->
                RouteConfiguration.forSessionScope()
                    .setRoute(route.route, route.view, MainView.class));
  }

  public List<AuthorizedRoute> getAuthorizedRoutes(Role role) {
    ArrayList<AuthorizedRoute> routes = new ArrayList<>();

    if (role.equals(Role.USER)) {
      routes.add(new AuthorizedRoute("home", "Home", HomeView.class));
      routes.add(new AuthorizedRoute("logout", "Выход", LogoutView.class));

    } else if (role.equals(Role.ADMIN)) {
      routes.add(new AuthorizedRoute("home", "Home", HomeView.class));
      routes.add(new AuthorizedRoute("admin", "Admin", AdminView.class));
      routes.add(new AuthorizedRoute("logout", "Выход", LogoutView.class));
    }
    return routes;
  }
}
