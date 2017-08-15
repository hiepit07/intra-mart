package jp.co.daitoku_sh.dfcm.common.controller.impl;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.ThrowableAnalyzer;
import org.springframework.security.web.util.ThrowableCauseExtractor;
import org.springframework.web.filter.GenericFilterBean;

/**
 * セッションに関する処理
 * 調査中
 * 
 */
public class SessionExpirationFilter extends GenericFilterBean {

  private ThrowableAnalyzer throwableAnalyzer = new DefaultThrowableAnalyzer();
  private AuthenticationTrustResolver authenticationTrustResolver = 
      new AuthenticationTrustResolverImpl();

  @Override
  public void doFilter(ServletRequest request, ServletResponse response,
      FilterChain chain) throws IOException, ServletException {
    try {
      chain.doFilter(request, response);
    } catch (IOException ex) {
      throw ex;
    } catch (Exception ex) {
      Throwable[] causeChain = throwableAnalyzer.determineCauseChain(ex);
      RuntimeException ase = (AuthenticationException) throwableAnalyzer
          .getFirstThrowableOfType(AuthenticationException.class, causeChain);

      if (ase == null) {
        ase = (AccessDeniedException) throwableAnalyzer.getFirstThrowableOfType(
            AccessDeniedException.class, causeChain);
      }

      if (ase != null) {
        if (ase instanceof AuthenticationException) {
          throw ase;
        } else if (ase instanceof AccessDeniedException) {
          if (authenticationTrustResolver.isAnonymous(SecurityContextHolder
              .getContext().getAuthentication())) {
            String ajaxHeader = ((HttpServletRequest) request).getHeader(
                "X-Requested-With");

            if ("XMLHttpRequest".equals(ajaxHeader)) {
              // 普通ではないの場合：AJAXはセッションタイムアウト
              HttpServletResponse resp = (HttpServletResponse) response;
              resp.getWriter().write("login:-1,j_username:-1,j_password:-1");
            } else {
              // 普通の場合：Submitはセッションタイムアウト
              String url = ((HttpServletRequest) request).getRequestURL()
                  .toString();
              // url = url.substring(0, url.indexOf("sec")) + "session";
              url = "common/com0101d01";
              HttpServletResponse resp = (HttpServletResponse) response;
              resp.sendRedirect(resp.encodeRedirectURL(url));
            }
          } else {
            // 普通の場合：Submitはセッションタイムアウト
            String url = ((HttpServletRequest) request).getRequestURL()
                .toString();
            // url = url.substring(0, url.indexOf("sec")) + "session";
            url = "common/com0101d01";
            HttpServletResponse resp = (HttpServletResponse) response;
            resp.sendRedirect(resp.encodeRedirectURL(url));
          }
        }
      }
    }
  }

  private static final class DefaultThrowableAnalyzer extends
      ThrowableAnalyzer {

    protected void initExtractorMap() {
      super.initExtractorMap();

      registerExtractor(ServletException.class, new ThrowableCauseExtractor() {

        public Throwable extractCause(Throwable throwable) {
          ThrowableAnalyzer.verifyThrowableHierarchy(throwable,
              ServletException.class);
          return ((ServletException) throwable).getRootCause();
        }
      });
    }
  }

  public void setCustomSessionExpiredErrorCode(
      int customSessionExpiredErrorCode) {
  }
}
