package me.erickren.reggie.filter;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import me.erickren.reggie.common.R;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录检查
 */
@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        // 获取请求URI
        String requestURI = request.getRequestURI();
        // 放行
        if (check(requestURI)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 检查登录状态
        if (request.getSession().getAttribute("employee") != null) {
            filterChain.doFilter(request, response);
            Long id = (Long) request.getSession().getAttribute("employee");
            log.info("登录放行，用户ID{}", id);
            return;
        }

        // 拦截，输出流响应
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        log.info("拦截了{}", requestURI);
    }

    /**
     * 检查本次匹配是否放行
     *
     * @param requestURI URI
     * @return result
     */
    public boolean check(String requestURI) {
        // 不需要处理的请求路径
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/favicon.ico"
        };
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestURI);
            if (match) {
                return true;
            }
        }
        return false;
    }



}
