package server;


import io.grpc.stub.ServerCallStreamObserver;
import io.grpc.stub.StreamObserver;
import gen.ProviderGrpc.ProviderImplBase;
import gen.DueTasksProviderProto.*;

public class ProviderImpl extends ProviderImplBase {
    private final DueTasksProvider provider;

    public ProviderImpl() {
        this.provider = new DueTasksProvider();
    }

    @Override
    public void subscribe(Subscription request, StreamObserver<SubscriptionResponse> responseObserver) {
        final SubscriptionResponse.Builder response = SubscriptionResponse.newBuilder();
        if (provider.addSubscriber(request)) {
            response.setStatus(SubscriptionStatus.SUCCESSFUL);
        } else {
            response.setDueTask("No tasks exist for such subject")
                    .setStatus(SubscriptionStatus.ERROR);
        }
        responseObserver.onNext(response.build());
        responseObserver.onCompleted();
    }

    @Override
    public void listen(SubscriptionRequest request, StreamObserver<DueTasksResponse> responseObserver) {
        String userName = request.getUserName();

        ServerCallStreamObserver<DueTasksResponse> properObserver = (ServerCallStreamObserver<DueTasksResponse>) responseObserver;
        if (!this.provider.addListener(userName, properObserver)) { // checks if such user exists
            responseObserver.onNext(DueTasksResponse.newBuilder().setDueTaskMessage("User not subscribed").build());
            responseObserver.onCompleted();
        }
    }
}
