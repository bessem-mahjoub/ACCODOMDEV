package accodom.dom.auth;



import accodom.dom.Config.JwtService;
import accodom.dom.Entities.Role;
import accodom.dom.Entities.Token;
import accodom.dom.Entities.TokenType;
import accodom.dom.Entities.User;
import accodom.dom.Repository.TokenRepository;
import accodom.dom.Repository.UserRepo;
import accodom.dom.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final UserRepo repository;
  @Autowired
  private final UserService repository1;

  private final TokenRepository tokenRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public AuthenticationResponse register(User request) {
    User in=new User();
    PasswordEncoder pass = new BCryptPasswordEncoder();
    request.setPassword(pass.encode(request.getPassword()));
    in=request;
    in.setRole(Role.CLIENT);
    var savedUser = repository.save(in);
    var jwtToken = jwtService.generateToken((UserDetails) in);
    var refreshToken = jwtService.generateRefreshToken((UserDetails)in);

    saveUserToken(savedUser, jwtToken);
    return AuthenticationResponse.builder()
        .token(jwtToken)
            .refreshToken(refreshToken)
        .build();
  }

  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getEmail(),
            request.getPassword()
        )
    );
    var user = repository1.findUserByEmail(request.getEmail());
    var role =user.getRole().name();
    System.out.println(role);
    var jwtToken = jwtService.generateToken((UserDetails) user);
    revokeAllUserTokens(user);
    saveUserToken(user, jwtToken);
    return AuthenticationResponse.builder()
        .token(jwtToken).Role(role)
        .build();
  }


  private void saveUserToken(User user, String jwtToken) {
    var token = Token.builder()
        .user(user)
        .token(jwtToken)
        .tokenType(TokenType.BEARER)
        .expired(false)
        .revoked(false)
        .build();
    tokenRepository.save(token);
  }

  private void revokeAllUserTokens(User user) {
    var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
    if (validUserTokens.isEmpty())
      return;
    validUserTokens.forEach(token -> {
      token.setExpired(true);
      token.setRevoked(true);
    });
    tokenRepository.saveAll(validUserTokens);
  }


}
