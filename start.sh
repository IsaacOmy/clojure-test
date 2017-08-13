#!/bin/bash
lein trampoline run &
echo $! > running.pid
