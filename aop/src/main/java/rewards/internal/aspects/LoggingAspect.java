package rewards.internal.aspects;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import rewards.internal.monitor.Monitor;
import rewards.internal.monitor.MonitorFactory;

@Aspect
@Component
public class LoggingAspect {

	private Logger logger = Logger.getLogger(getClass());
	private MonitorFactory monitorFactory;

	@Autowired
	public LoggingAspect(MonitorFactory monitorFactory) {
		super();
		this.monitorFactory = monitorFactory;
	}

	

	@Before(value = "execution(public * rewards.internal.*.*Repository.find*(..))")
	public void implLogging(JoinPoint joinPoint) {
		logger.info("'Before' advice implementation - " + joinPoint.getTarget().getClass() + //
				"; Executing before " + joinPoint.getSignature().getName() + //
				"() method");
	}
	
	
	@Around(value = "execution(public * rewards.internal.*.*Repository.update*(..))")
	public Object monitor(ProceedingJoinPoint repositoryMethod) throws Throwable {
		String name = createJoinPointTraceName(repositoryMethod);
		Monitor monitor = monitorFactory.start(name);
		try {
			// Invoke repository method ...
			
			return repositoryMethod.proceed();

		} finally {
			monitor.stop();
			logger.info("'Around' advice implementation - " + monitor);
		}
	}
		
	private String createJoinPointTraceName(JoinPoint joinPoint) {
		Signature signature = joinPoint.getSignature();
		StringBuilder sb = new StringBuilder();
		sb.append(signature.getDeclaringType().getSimpleName());
		sb.append('.').append(signature.getName());
		return sb.toString();
	} 
}