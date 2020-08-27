package gen;

import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.4.0)",
    comments = "Source: DueTasksProvider.proto")
public final class ProviderGrpc {

  private ProviderGrpc() {}

  public static final String SERVICE_NAME = "Provider";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<gen.DueTasksProviderProto.Subscription,
      gen.DueTasksProviderProto.SubscriptionResponse> METHOD_SUBSCRIBE =
      io.grpc.MethodDescriptor.<gen.DueTasksProviderProto.Subscription, gen.DueTasksProviderProto.SubscriptionResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "Provider", "subscribe"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              gen.DueTasksProviderProto.Subscription.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              gen.DueTasksProviderProto.SubscriptionResponse.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<gen.DueTasksProviderProto.SubscriptionRequest,
      gen.DueTasksProviderProto.DueTasksResponse> METHOD_LISTEN =
      io.grpc.MethodDescriptor.<gen.DueTasksProviderProto.SubscriptionRequest, gen.DueTasksProviderProto.DueTasksResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
          .setFullMethodName(generateFullMethodName(
              "Provider", "listen"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              gen.DueTasksProviderProto.SubscriptionRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              gen.DueTasksProviderProto.DueTasksResponse.getDefaultInstance()))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ProviderStub newStub(io.grpc.Channel channel) {
    return new ProviderStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ProviderBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new ProviderBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ProviderFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new ProviderFutureStub(channel);
  }

  /**
   */
  public static abstract class ProviderImplBase implements io.grpc.BindableService {

    /**
     */
    public void subscribe(gen.DueTasksProviderProto.Subscription request,
        io.grpc.stub.StreamObserver<gen.DueTasksProviderProto.SubscriptionResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SUBSCRIBE, responseObserver);
    }

    /**
     */
    public void listen(gen.DueTasksProviderProto.SubscriptionRequest request,
        io.grpc.stub.StreamObserver<gen.DueTasksProviderProto.DueTasksResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_LISTEN, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_SUBSCRIBE,
            asyncUnaryCall(
              new MethodHandlers<
                gen.DueTasksProviderProto.Subscription,
                gen.DueTasksProviderProto.SubscriptionResponse>(
                  this, METHODID_SUBSCRIBE)))
          .addMethod(
            METHOD_LISTEN,
            asyncServerStreamingCall(
              new MethodHandlers<
                gen.DueTasksProviderProto.SubscriptionRequest,
                gen.DueTasksProviderProto.DueTasksResponse>(
                  this, METHODID_LISTEN)))
          .build();
    }
  }

  /**
   */
  public static final class ProviderStub extends io.grpc.stub.AbstractStub<ProviderStub> {
    private ProviderStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ProviderStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ProviderStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ProviderStub(channel, callOptions);
    }

    /**
     */
    public void subscribe(gen.DueTasksProviderProto.Subscription request,
        io.grpc.stub.StreamObserver<gen.DueTasksProviderProto.SubscriptionResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SUBSCRIBE, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void listen(gen.DueTasksProviderProto.SubscriptionRequest request,
        io.grpc.stub.StreamObserver<gen.DueTasksProviderProto.DueTasksResponse> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(METHOD_LISTEN, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class ProviderBlockingStub extends io.grpc.stub.AbstractStub<ProviderBlockingStub> {
    private ProviderBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ProviderBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ProviderBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ProviderBlockingStub(channel, callOptions);
    }

    /**
     */
    public gen.DueTasksProviderProto.SubscriptionResponse subscribe(gen.DueTasksProviderProto.Subscription request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SUBSCRIBE, getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<gen.DueTasksProviderProto.DueTasksResponse> listen(
        gen.DueTasksProviderProto.SubscriptionRequest request) {
      return blockingServerStreamingCall(
          getChannel(), METHOD_LISTEN, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class ProviderFutureStub extends io.grpc.stub.AbstractStub<ProviderFutureStub> {
    private ProviderFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ProviderFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ProviderFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ProviderFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<gen.DueTasksProviderProto.SubscriptionResponse> subscribe(
        gen.DueTasksProviderProto.Subscription request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SUBSCRIBE, getCallOptions()), request);
    }
  }

  private static final int METHODID_SUBSCRIBE = 0;
  private static final int METHODID_LISTEN = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ProviderImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(ProviderImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_SUBSCRIBE:
          serviceImpl.subscribe((gen.DueTasksProviderProto.Subscription) request,
              (io.grpc.stub.StreamObserver<gen.DueTasksProviderProto.SubscriptionResponse>) responseObserver);
          break;
        case METHODID_LISTEN:
          serviceImpl.listen((gen.DueTasksProviderProto.SubscriptionRequest) request,
              (io.grpc.stub.StreamObserver<gen.DueTasksProviderProto.DueTasksResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static final class ProviderDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return gen.DueTasksProviderProto.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (ProviderGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ProviderDescriptorSupplier())
              .addMethod(METHOD_SUBSCRIBE)
              .addMethod(METHOD_LISTEN)
              .build();
        }
      }
    }
    return result;
  }
}
