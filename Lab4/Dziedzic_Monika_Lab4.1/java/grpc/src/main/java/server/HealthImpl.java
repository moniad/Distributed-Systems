package server;

import io.grpc.stub.StreamObserver;
import gen.ConnectionHealthProto;
import gen.HealthGrpc.HealthImplBase;

import static gen.ConnectionHealthProto.PingRequest;

public class HealthImpl extends HealthImplBase {

    @Override
    public void ping(PingRequest request, StreamObserver<ConnectionHealthProto.PingReply> responseObserver) { //client calls it
        String message = "Connection retrieved";
        ConnectionHealthProto.PingReply response = ConnectionHealthProto.PingReply.newBuilder()
                .setMessage(message)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
