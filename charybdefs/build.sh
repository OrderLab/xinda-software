#!/bin/bash

thrift -r --gen cpp server.thrift
thrift -r --gen py server.thrift
cmake CMakeLists.txt
make -j8
