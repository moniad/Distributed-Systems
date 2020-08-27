package server;

import io.grpc.stub.ServerCallStreamObserver;
import io.grpc.stub.StreamObserver;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static gen.DueTasksProviderProto.*;

public class User {

    private ServerCallStreamObserver<DueTasksResponse> streamObserver;
    private final HashMap<String, Set<EventType>> subscriptions;
    private final String name;

    public User(String userName) {
        this.name = userName;
        this.streamObserver = null;
        this.subscriptions = new HashMap<>();
    }

    public void setStreamObserver(ServerCallStreamObserver<DueTasksResponse> streamObserver) {
        if (this.streamObserver == null) {
            this.streamObserver = streamObserver;
        }
    }

    public void subscribe(Subscription subscription) {
        Set<EventType> subcribedEvents = subscriptions.getOrDefault(subscription.getSubject(), new HashSet<>());
        subcribedEvents.addAll(subscription.getTypeList());
        this.subscriptions.put(subscription.getSubject(), subcribedEvents);
    }

    public StreamObserver<DueTasksResponse> getStreamObserver() {
        return this.streamObserver;
    }

    public Set<EventType> getTaskTypesForSubject(String subject) {
        return this.subscriptions.getOrDefault(subject, new HashSet<>());
    }
}
