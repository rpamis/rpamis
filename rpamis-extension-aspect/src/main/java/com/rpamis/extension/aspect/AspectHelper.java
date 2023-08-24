package com.rpamis.extension.aspect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Objects;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * 切面Helper
 *
 * @author benym
 * @date 2023/8/24 16:05
 */
public class AspectHelper {

  private static final SpelExpressionParser EXPRESSION_PARSER = new SpelExpressionParser();

  /**
   * 通过ProceedingJoinPoint和自定义注解Class获取切点注解信息
   *
   * @param joinPoint       joinPoint
   * @param annotationClass 自定义注解Class
   * @param <T>             <T>
   * @return 自定义注解类
   */
  public static <T extends Annotation> T getPointCutAnnotation(ProceedingJoinPoint joinPoint,
      Class<T> annotationClass) {
    Signature signature = joinPoint.getSignature();
    MethodSignature methodSignature = (MethodSignature) signature;
    Method method = methodSignature.getMethod();
    return method.getAnnotation(annotationClass);
  }

  /**
   * 解析SpEl表达式
   *
   * @param joinPoint        joinPoint
   * @param expressionString SpEl表达式
   * @return String
   */
  public static String parseSpEl(ProceedingJoinPoint joinPoint, String expressionString) {
    StandardEvaluationContext evaluationContext = createEvaluationContext(joinPoint);
    Expression expression = EXPRESSION_PARSER.parseExpression(expressionString);
    return expression.getValue(evaluationContext, String.class);
  }

  /**
   * 创建解析上下文，填入入参的变量名称到Context
   *
   * @param joinPoint joinPoint
   * @return StandardEvaluationContext
   */
  private static StandardEvaluationContext createEvaluationContext(ProceedingJoinPoint joinPoint) {
    Object[] args = joinPoint.getArgs();
    StandardEvaluationContext evaluationContext = new StandardEvaluationContext();
    MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
    Method targetMethod = methodSignature.getMethod();
    LocalVariableTableParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
    String[] parametersName = parameterNameDiscoverer.getParameterNames(targetMethod);
    if (args == null || args.length == 0) {
      return evaluationContext;
    }
    for (int i = 0; i < args.length; i++) {
      evaluationContext.setVariable(Objects.requireNonNull(parametersName)[i], args[i]);
    }
    return evaluationContext;
  }
}
