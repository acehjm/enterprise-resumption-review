package cn.dyoon.review.manage.auth.filter;

import cn.dyoon.review.common.enums.UserTypeEnum;
import cn.dyoon.review.manage.auth.constant.UserSession;
import cn.dyoon.review.manage.auth.constant.UserSessionHolder;
import cn.dyoon.review.manage.auth.jwt.JwtTokenUtil;
import cn.dyoon.review.manage.auth.jwt.JwtUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * JWT Token 过滤器
 * - 这里解析Token并转为User对象，将User保存在SecurityContext中
 *
 * @author majhdk
 * @date 2019-05-25
 */
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationTokenFilter.class);

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String AUTHORIZATION_TOKEN = "access_token";

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        String token = this.getRequestToken(request);
        //验证JWT是否正确
        if (StringUtils.hasText(token) && JwtTokenUtil.validateToken(token)) {
            JwtUser user = JwtTokenUtil.parseToken(token, JwtUser.class);

            //获取用户认证信息
            List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(user.getUserType()));

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(user.getUsername(), null, authorities);
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            //将用户保存到SecurityContext
            SecurityContextHolder.getContext().setAuthentication(authentication);
            logger.info("用户 {} 通过授权校验", user.getUsername());

            // 添加到全局
            UserSessionHolder.userSessionThreadLocal.set(this.getUserSession(user));
        }
        filterChain.doFilter(request, response);
    }

    /**
     * 解析用户token
     */
    private String getRequestToken(HttpServletRequest request) {
        //从HTTP头部获取TOKEN
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            //返回Token字符串，去除Bearer
            return bearerToken.substring(7);
        }
        //从请求参数中获取TOKEN
        String accessToken = request.getParameter(AUTHORIZATION_TOKEN);
        if (StringUtils.hasText(accessToken)) {
            return accessToken;
        }
        return null;
    }

    /**
     * 获取用户会话
     *
     * @param user
     * @return
     */
    private UserSession getUserSession(JwtUser user) {
        UserSession userSession = new UserSession();
        userSession.setUsername(user.getUsername());
        userSession.setUserType(user.getUserType());
        userSession.setRole(user.getRole());
        return userSession;
    }
}
