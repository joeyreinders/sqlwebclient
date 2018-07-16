package net.sqlwebclient.jetty.custom;

import org.eclipse.jetty.util.component.AbstractLifeCycle;
import org.eclipse.jetty.util.component.LifeCycle;

import java.util.*;

public final class JettyLifecycleWrapper extends AbstractLifeCycle.AbstractLifeCycleListener
        implements JettyLifecycleListener {
    public enum State {
        FAILURE,
        STARTED,
        STARTING,
        STOPPED,
        STOPPING
    }

    private final Map<State, List<JettyLifecycleListener>> listeners = new HashMap<State, List<JettyLifecycleListener>>();

    private JettyLifecycleWrapper() {
        //noop
    }

    private List<JettyLifecycleListener> getListeners(final State state) {
        final List<JettyLifecycleListener> res = listeners.get(state);
        if(res != null) {
            return res;
        }

        final List<JettyLifecycleListener> newList = new ArrayList<JettyLifecycleListener>();
        listeners.put(state, newList);
        return newList;
    }

    @Override
    public void onState(final State state) {
        System.out.println(new Date() + " state: " + state.name());
        final List<JettyLifecycleListener> listenerList = getListeners(state);
        if(listenerList == null) {
            return;
        }

        for(JettyLifecycleListener listener: listenerList) {
            listener.onState(state);
        }
    }

    public JettyLifecycleWrapper add(final JettyLifecycleListener listener) {
        for(State state: State.values()) {
            add(state, listener);
        }

        return this;
    }

    public JettyLifecycleWrapper add(final State state, final JettyLifecycleListener listener) {
        getListeners(state).add(listener);
        return this;
    }

    public void lifeCycleFailure(final LifeCycle event, final Throwable cause) {
        onState(State.FAILURE);
    }

    public void lifeCycleStarted(final LifeCycle event) {
        onState(State.STARTED);
    }

    public void lifeCycleStarting(final LifeCycle event) {
        onState(State.STARTING);
    }

    public void lifeCycleStopped(final LifeCycle event) {
        onState(State.STOPPED);
    }

    public void lifeCycleStopping(final LifeCycle event) {
        onState(State.STOPPING);
    }

    public static JettyLifecycleWrapper newInstance() {
        return new JettyLifecycleWrapper();
    }
}
