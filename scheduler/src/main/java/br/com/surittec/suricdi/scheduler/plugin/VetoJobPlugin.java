package br.com.surittec.suricdi.scheduler.plugin;

import java.util.Arrays;

import org.apache.deltaspike.core.api.projectstage.ProjectStage;
import org.apache.deltaspike.core.api.provider.BeanProvider;
import org.quartz.JobExecutionContext;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.matchers.EverythingMatcher;
import org.quartz.listeners.TriggerListenerSupport;
import org.quartz.spi.ClassLoadHelper;
import org.quartz.spi.SchedulerPlugin;

import br.com.surittec.suricdi.scheduler.annotation.JobVetoable;

public class VetoJobPlugin extends TriggerListenerSupport implements SchedulerPlugin {

	private String name;

	/*
	 * Public Methods
	 */
	
	@Override
	public void initialize(String name, Scheduler scheduler, ClassLoadHelper loadHelper) throws SchedulerException {
		this.name = name;
		scheduler.getListenerManager().addTriggerListener(this,  EverythingMatcher.allTriggers());
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
//		ProjectStage projectStage = BeanProvider.getContextualReference(ProjectStage.class);
		ProjectStage projectStage = BeanProvider.getDependent(ProjectStage.class).get();
		JobVetoable vetoAnnotation = context.getJobDetail().getJobClass().getAnnotation(JobVetoable.class);
		if (vetoAnnotation != null) {
			return !Arrays.asList(vetoAnnotation.runnableProjectStages()).contains(projectStage.getClass().getSimpleName());
		}
		return false;
//		return context.getJobDetail().getJobClass().isAnnotationPresent(br.com.poupex.govcorp.batch.interceptor.JobVetoable.class);
	}
	
	@Override
	public void start() {
		getLog().info(String.format("Starting %s: %s", this.getClass().getName(), name));
	}

	@Override
	public void shutdown() {
		getLog().info(String.format("Stoping %s: %s", this.getClass().getName(), name));
	}

}
