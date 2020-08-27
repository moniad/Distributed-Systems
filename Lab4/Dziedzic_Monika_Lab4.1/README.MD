# gRPC

#### SERVER
###### How to compile and run:
1) `cd java/grpc`
2) `mvn install -q` lub po pierwszym wykonaniu tej komendy poprzez IDE (IntelliJ)

Important info:
* https://github.com/grpc/grpc-java/blob/master/README.md
* https://www.baeldung.com/grpc-introduction

#### CLIENT
###### How to compile and run:
1) `cd python/gRPC/client`
2) `python -m grpc_tools.protoc -I=protos --python_out=src --grpc_python_out=src protos/*`
3) `python src/client.py` lub poprzez IDE (PyCharma)
