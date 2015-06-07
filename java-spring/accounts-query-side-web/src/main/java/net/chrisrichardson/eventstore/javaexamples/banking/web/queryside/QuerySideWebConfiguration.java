package net.chrisrichardson.eventstore.javaexamples.banking.web.queryside;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import net.chrisrichardson.eventstore.javaexamples.banking.backend.queryside.accounts.QuerySideAccountConfiguration;
import net.chrisrichardson.eventstore.javaexamples.banking.web.util.ObservableReturnValueHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

@Configuration
@Import({QuerySideAccountConfiguration.class})
@ComponentScan
public class QuerySideWebConfiguration extends WebMvcConfigurerAdapter {

	@Autowired
	private RequestMappingHandlerAdapter adapter;

	@PostConstruct
	public void init() {
		// https://jira.spring.io/browse/SPR-13083
		List<HandlerMethodReturnValueHandler> handlers = new ArrayList<HandlerMethodReturnValueHandler>(adapter.getReturnValueHandlers());
		handlers.add(0, new ObservableReturnValueHandler());
		adapter.setReturnValueHandlers(handlers);
	}

}
