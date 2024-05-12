package roomescape.infrastructure.auth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import roomescape.application.AuthService;
import roomescape.application.dto.LoginMember;
import roomescape.application.dto.MemberResponse;
import roomescape.exception.RoomescapeErrorCode;
import roomescape.exception.RoomescapeException;

@Component
public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {
    private final AuthService authService;

    public LoginMemberArgumentResolver(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthenticationPrincipal.class);
    }

    @Override
    public LoginMember resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        if (request == null) {
            throw new RoomescapeException(RoomescapeErrorCode.UNAUTHORIZED);
        }
        String token = extractTokenFrom(request);
        MemberResponse response = authService.findMemberByToken(token);
        return new LoginMember(response.id(), response.name(), response.email(), response.role().name());
    }

    private String extractTokenFrom(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            throw new RoomescapeException(RoomescapeErrorCode.UNAUTHORIZED);
        }
        return Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals("token"))
                .findAny()
                .map(Cookie::getValue)
                .orElse("");
    }
}