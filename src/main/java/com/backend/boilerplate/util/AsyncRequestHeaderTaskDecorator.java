package com.backend.boilerplate.util;

//
//import java.util.Map;
//import org.slf4j.MDC;
//import org.springframework.core.task.TaskDecorator;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.context.request.RequestAttributes;
//import org.springframework.web.context.request.RequestContextHolder;
//
///**
// * @author sarvesh
// * @version 0.0.2
// * @since 0.0.2
// */
//public class AsyncRequestHeaderTaskDecorator implements TaskDecorator {
//
//  @Override
//  public Runnable decorate(Runnable runnable) {
//    Map<String, String> contextMap = MDC.getCopyOfContextMap();
//    RequestAttributes context = RequestContextHolder.currentRequestAttributes();
//    final Authentication a = SecurityContextHolder.getContext().getAuthentication();
//    return () -> {
//      try {
//        MDC.setContextMap(contextMap);
//        RequestContextHolder.setRequestAttributes(context);
//        SecurityContext ctx = SecurityContextHolder.createEmptyContext();
//        ctx.setAuthentication(a);
//        SecurityContextHolder.setContext(ctx);
//        runnable.run();
//      } finally {
//        MDC.clear();
//        RequestContextHolder.resetRequestAttributes();
//        SecurityContextHolder.clearContext();
//      }
//    };
//  }
//}
public class AsyncRequestHeaderTaskDecorator {
}