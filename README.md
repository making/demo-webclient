## Sync

![image](https://user-images.githubusercontent.com/106908/55135657-aa2ff480-516f-11e9-9614-3fb248ee4476.png)


```
$ wrk -t16 -c100 -d30s --latency --timeout 30s http://localhost:8080/sync 
Running 30s test @ http://localhost:8080/sync
  16 threads and 100 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency    11.54s     5.28s   28.44s    86.36%
    Req/Sec     3.24      4.50    20.00     83.13%
  Latency Distribution
     50%    9.56s 
     75%   10.27s 
     90%   20.13s 
     99%   28.44s 
  88 requests in 30.09s, 71.24KB read
Requests/sec:      2.92
Transfer/sec:      2.37KB
```

* All request got timeout ...

### Async + Blocking (`Future`)

![image](https://user-images.githubusercontent.com/106908/55135762-ecf1cc80-516f-11e9-9a9b-c7f8982a2cd6.png)

```
$ wrk -t16 -c100 -d30s --latency --timeout 30s http://localhost:8080/future  
Running 30s test @ http://localhost:8080/future
  16 threads and 100 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     4.60s     4.32s   29.34s    85.61%
    Req/Sec     4.22      7.37    50.00     95.31%
  Latency Distribution
     50%    3.63s 
     75%    7.09s 
     90%   10.85s 
     99%   17.11s 
  519 requests in 30.55s, 386.49KB read
  Socket errors: connect 0, read 0, write 0, timeout 1
  Non-2xx or 3xx responses: 147
Requests/sec:     16.99
Transfer/sec:     12.65KB
```

* too many threads
* slow latency

### Async + NonBlocking (`CompletableFuture`) + Thread Pool Execturoe

![image](https://user-images.githubusercontent.com/106908/55135915-4bb74600-5170-11e9-98cc-f7dce9914594.png)

```
$ wrk -t16 -c100 -d30s --latency --timeout 30s http://localhost:8080/completablefuture
Running 30s test @ http://localhost:8080/completablefuture
  16 threads and 100 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency   630.80ms    2.13s   23.74s    96.38%
    Req/Sec    34.55     19.19   121.00     66.42%
  Latency Distribution
     50%  136.58ms
     75%  221.28ms
     90%    1.23s 
     99%   13.88s 
  10541 requests in 30.09s, 5.36MB read
  Non-2xx or 3xx responses: 10204
Requests/sec:    350.36
Transfer/sec:    182.56KB
```

* too many threads
* too many errors

### Async + NonBlocking (Reactor)

![image](https://user-images.githubusercontent.com/106908/55136006-8faa4b00-5170-11e9-89f3-0a74c5410656.png)

```
$ wrk -t16 -c100 -d30s --latency --timeout 30s http://localhost:8080/mono
Running 30s test @ http://localhost:8080/mono
  16 threads and 100 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     1.58s     2.62s   28.03s    94.75%
    Req/Sec     7.46      5.93    40.00     77.57%
  Latency Distribution
     50%  988.61ms
     75%    1.50s 
     90%    2.88s 
     99%   17.95s 
  2106 requests in 30.09s, 1.66MB read
  Non-2xx or 3xx responses: 3
Requests/sec:     70.00
Transfer/sec:     56.62KB
```

* some 503 errors from origin server
