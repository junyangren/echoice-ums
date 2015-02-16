package org.echoice.ums.plugins;

public interface Command<T,P> {
	public T execute(P p);
}
