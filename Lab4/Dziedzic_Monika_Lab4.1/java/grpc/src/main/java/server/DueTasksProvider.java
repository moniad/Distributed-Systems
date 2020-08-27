package server;

import io.grpc.stub.ServerCallStreamObserver;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static gen.DueTasksProviderProto.*;
import static gen.DueTasksProviderProto.Subject.*;

public class DueTasksProvider {

    private static final ExecutorService executor = Executors.newCachedThreadPool();
    private static final int MAX_LAB_NUMBER = 6;
    private static final int MIN_LAB_NUMBER = 1;
    private static final int MAX_CONSUMED_HOURS = 40;
    private static final int MIN_CONSUMED_HOURS = 5;
    private static final int MAX_DIFFICULTY_LEVEL = 100;
    private static final int MIN_DIFFICULTY_LEVEL = 10;

    private final ConcurrentHashMap<String, User> users;  // multiple clients might subscribe at once
    private final ArrayList<String> subjects;


    public DueTasksProvider() {
        this.users = new ConcurrentHashMap<>();
        this.subjects = getSubjects();
        this.runGenerator();
    }

    public boolean addSubscriber(Subscription request) {
        if (!isSubscriptionParamsValid(request)) {
            return false;
        }

        String userName = request.getUserName();
        User user = this.users.getOrDefault(userName, null);
        if (user == null) {
            user = new User(userName);
            user.subscribe(request);
            this.users.put(userName, user);
        } else {
            user.subscribe(request);
        }

        System.out.println("\n[SUBSCRIBED] New subscription: " + request);
        return true;
    }

    public boolean addListener(String userName, ServerCallStreamObserver<DueTasksResponse> streamObserver) {
        User userEntry = this.users.getOrDefault(userName, null);
        if (userEntry == null) {
            return false;
        }
        streamObserver.setOnCancelHandler(() -> { // when user disconnects
            System.out.println("[INFO] User disconnected. Removing: " + userName);
            this.users.remove(userName);
        });
        userEntry.setStreamObserver(streamObserver);
        return true;
    }

    @SuppressWarnings("InfiniteLoopStatement")
    private void generateEvents() {
        while (true) {

            String subject = subjects.get(getBoundedRandomInt(0, this.subjects.size() - 1));
            int eventNumber = getBoundedRandomInt(0, 2);
            EventType type = EventType.forNumber(eventNumber);

            if (type != null) {

                final DueTasksResponse dueTasksResponse = createDueTasksResponse(subject, type);
                System.out.println("[PRODUCED] " + dueTasksResponse.getSubject() + " " + dueTasksResponse.getType() + " " +
                        dueTasksResponse.getDueTaskMessage());

                this.users.values().forEach((user) -> {
                    if (user.getTaskTypesForSubject(subject).contains(type) && user.getStreamObserver() != null) {
                        user.getStreamObserver().onNext(dueTasksResponse);
                    }
                });

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    System.err.println(e.getMessage());
                }
            }
        }
    }

    private DueTasksResponse createDueTasksResponse(String subject, EventType type) {
        String dueTaskMessage = "Unavailable";
        switch (type) {
            case ASSIGNMENT:
                dueTaskMessage = getAssignments();
                break;
            case TEST:
                dueTaskMessage = getTests();
                break;
            case PREPARATION_FOR_CLASSES:
                dueTaskMessage = getPreparationTasks();
                break;
        }
        return DueTasksResponse.newBuilder()
                .setSubject(subject)
                .setType(type)
                .setDueTaskMessage(dueTaskMessage)
                .build();
    }

    private ArrayList<String> getSubjects() {
        return Stream.of(DISTRIBUTED_SYSTEMS.name(), KERNEL_PROGRAMMING.name(), COMPILERS.name())
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private void runGenerator() {
        System.out.println("[INFO] Producing due tasks...");
        executor.submit(this::generateEvents);
    }

    private static int getBoundedRandomInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    private boolean isSubscriptionParamsValid(Subscription request) {
        if (!subjects.contains(request.getSubject())) {
            System.out.println("[ERROR] No such subject available: " + request.getSubject());
            return false;
        }
        return true;
    }

    public static String getPreparationTasks() {
        return "You need to prepare for lab no. " + getBoundedRandomInt(MIN_LAB_NUMBER, MAX_LAB_NUMBER) + " / " + MAX_LAB_NUMBER;
    }

    public static String getTests() {
        return "This test's difficulty level is " + getBoundedRandomInt(MIN_DIFFICULTY_LEVEL, MAX_DIFFICULTY_LEVEL) + " / " + MAX_DIFFICULTY_LEVEL;
    }

    public static String getAssignments() {
        return "This task will consume " + getBoundedRandomInt(MIN_CONSUMED_HOURS, MAX_CONSUMED_HOURS) + " h";
    }
}
