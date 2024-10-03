package st.backend.stocks;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.46.0)",
    comments = "Source: stock.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class StockServiceGrpc {

  private StockServiceGrpc() {}

  public static final String SERVICE_NAME = "StockService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<st.backend.stocks.StockServiceProto.StockRequest1,
      st.backend.stocks.StockServiceProto.StockResponse1> getGetStock1Method;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetStock1",
      requestType = st.backend.stocks.StockServiceProto.StockRequest1.class,
      responseType = st.backend.stocks.StockServiceProto.StockResponse1.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<st.backend.stocks.StockServiceProto.StockRequest1,
      st.backend.stocks.StockServiceProto.StockResponse1> getGetStock1Method() {
    io.grpc.MethodDescriptor<st.backend.stocks.StockServiceProto.StockRequest1, st.backend.stocks.StockServiceProto.StockResponse1> getGetStock1Method;
    if ((getGetStock1Method = StockServiceGrpc.getGetStock1Method) == null) {
      synchronized (StockServiceGrpc.class) {
        if ((getGetStock1Method = StockServiceGrpc.getGetStock1Method) == null) {
          StockServiceGrpc.getGetStock1Method = getGetStock1Method =
              io.grpc.MethodDescriptor.<st.backend.stocks.StockServiceProto.StockRequest1, st.backend.stocks.StockServiceProto.StockResponse1>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetStock1"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  st.backend.stocks.StockServiceProto.StockRequest1.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  st.backend.stocks.StockServiceProto.StockResponse1.getDefaultInstance()))
              .setSchemaDescriptor(new StockServiceMethodDescriptorSupplier("GetStock1"))
              .build();
        }
      }
    }
    return getGetStock1Method;
  }

  private static volatile io.grpc.MethodDescriptor<st.backend.stocks.StockServiceProto.StockRequest2,
      st.backend.stocks.StockServiceProto.StockResponse2> getGetStock2Method;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetStock2",
      requestType = st.backend.stocks.StockServiceProto.StockRequest2.class,
      responseType = st.backend.stocks.StockServiceProto.StockResponse2.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<st.backend.stocks.StockServiceProto.StockRequest2,
      st.backend.stocks.StockServiceProto.StockResponse2> getGetStock2Method() {
    io.grpc.MethodDescriptor<st.backend.stocks.StockServiceProto.StockRequest2, st.backend.stocks.StockServiceProto.StockResponse2> getGetStock2Method;
    if ((getGetStock2Method = StockServiceGrpc.getGetStock2Method) == null) {
      synchronized (StockServiceGrpc.class) {
        if ((getGetStock2Method = StockServiceGrpc.getGetStock2Method) == null) {
          StockServiceGrpc.getGetStock2Method = getGetStock2Method =
              io.grpc.MethodDescriptor.<st.backend.stocks.StockServiceProto.StockRequest2, st.backend.stocks.StockServiceProto.StockResponse2>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetStock2"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  st.backend.stocks.StockServiceProto.StockRequest2.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  st.backend.stocks.StockServiceProto.StockResponse2.getDefaultInstance()))
              .setSchemaDescriptor(new StockServiceMethodDescriptorSupplier("GetStock2"))
              .build();
        }
      }
    }
    return getGetStock2Method;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static StockServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<StockServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<StockServiceStub>() {
        @java.lang.Override
        public StockServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new StockServiceStub(channel, callOptions);
        }
      };
    return StockServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static StockServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<StockServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<StockServiceBlockingStub>() {
        @java.lang.Override
        public StockServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new StockServiceBlockingStub(channel, callOptions);
        }
      };
    return StockServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static StockServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<StockServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<StockServiceFutureStub>() {
        @java.lang.Override
        public StockServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new StockServiceFutureStub(channel, callOptions);
        }
      };
    return StockServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class StockServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void getStock1(st.backend.stocks.StockServiceProto.StockRequest1 request,
        io.grpc.stub.StreamObserver<st.backend.stocks.StockServiceProto.StockResponse1> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetStock1Method(), responseObserver);
    }

    /**
     */
    public void getStock2(st.backend.stocks.StockServiceProto.StockRequest2 request,
        io.grpc.stub.StreamObserver<st.backend.stocks.StockServiceProto.StockResponse2> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetStock2Method(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetStock1Method(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                st.backend.stocks.StockServiceProto.StockRequest1,
                st.backend.stocks.StockServiceProto.StockResponse1>(
                  this, METHODID_GET_STOCK1)))
          .addMethod(
            getGetStock2Method(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                st.backend.stocks.StockServiceProto.StockRequest2,
                st.backend.stocks.StockServiceProto.StockResponse2>(
                  this, METHODID_GET_STOCK2)))
          .build();
    }
  }

  /**
   */
  public static final class StockServiceStub extends io.grpc.stub.AbstractAsyncStub<StockServiceStub> {
    private StockServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected StockServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new StockServiceStub(channel, callOptions);
    }

    /**
     */
    public void getStock1(st.backend.stocks.StockServiceProto.StockRequest1 request,
        io.grpc.stub.StreamObserver<st.backend.stocks.StockServiceProto.StockResponse1> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetStock1Method(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getStock2(st.backend.stocks.StockServiceProto.StockRequest2 request,
        io.grpc.stub.StreamObserver<st.backend.stocks.StockServiceProto.StockResponse2> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetStock2Method(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class StockServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<StockServiceBlockingStub> {
    private StockServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected StockServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new StockServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public st.backend.stocks.StockServiceProto.StockResponse1 getStock1(st.backend.stocks.StockServiceProto.StockRequest1 request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetStock1Method(), getCallOptions(), request);
    }

    /**
     */
    public st.backend.stocks.StockServiceProto.StockResponse2 getStock2(st.backend.stocks.StockServiceProto.StockRequest2 request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetStock2Method(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class StockServiceFutureStub extends io.grpc.stub.AbstractFutureStub<StockServiceFutureStub> {
    private StockServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected StockServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new StockServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<st.backend.stocks.StockServiceProto.StockResponse1> getStock1(
        st.backend.stocks.StockServiceProto.StockRequest1 request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetStock1Method(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<st.backend.stocks.StockServiceProto.StockResponse2> getStock2(
        st.backend.stocks.StockServiceProto.StockRequest2 request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetStock2Method(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_STOCK1 = 0;
  private static final int METHODID_GET_STOCK2 = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final StockServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(StockServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_STOCK1:
          serviceImpl.getStock1((st.backend.stocks.StockServiceProto.StockRequest1) request,
              (io.grpc.stub.StreamObserver<st.backend.stocks.StockServiceProto.StockResponse1>) responseObserver);
          break;
        case METHODID_GET_STOCK2:
          serviceImpl.getStock2((st.backend.stocks.StockServiceProto.StockRequest2) request,
              (io.grpc.stub.StreamObserver<st.backend.stocks.StockServiceProto.StockResponse2>) responseObserver);
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

  private static abstract class StockServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    StockServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return st.backend.stocks.StockServiceProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("StockService");
    }
  }

  private static final class StockServiceFileDescriptorSupplier
      extends StockServiceBaseDescriptorSupplier {
    StockServiceFileDescriptorSupplier() {}
  }

  private static final class StockServiceMethodDescriptorSupplier
      extends StockServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    StockServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (StockServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new StockServiceFileDescriptorSupplier())
              .addMethod(getGetStock1Method())
              .addMethod(getGetStock2Method())
              .build();
        }
      }
    }
    return result;
  }
}
