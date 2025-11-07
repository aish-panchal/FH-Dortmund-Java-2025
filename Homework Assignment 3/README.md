For Homework Assignment 3, the division of the work was done as follows: 

1. Establishing concurrency with respect to task and storage management systems as well as the charging stations - Girsy Guzman and Abdel Rehman
2. Creation of a basic GUI using JavaFX that can also carry out the same operations of concurrency using a non-cli environment and answering the questions posed in the assignment - Aishwary Panchal

This is the second last part of the capstone project looking to establish concurrency in the project where the charging of the AGVs can be done in a concurrent manner dependent on the available resources i.e. the charging stations. Here is the output of one of the charging stations as seen in the log file "2025-11-07 11-02-26-525ChargingStation": 

```
Log event: 
Log event: 2025-11-07 10:58:49-826: Vehicle avg.3 is charging.
Log event: 2025-11-07 10:58:49-826: Vehicle avg.0 is charging.
Log event: 2025-11-07 10:58:49-826: Vehicle avg.13 is charging.
Log event: 2025-11-07 10:58:49-826: Vehicle avg.10 is charging.
Log event: 2025-11-07 10:58:49-826: Vehicle avg.3 is charged.
Log event: 2025-11-07 10:58:50-835: Vehicle avg.0 is charging.
Log event: 2025-11-07 10:58:50-835: Vehicle avg.13 is charging.
Log event: 2025-11-07 10:58:50-835: Vehicle avg.0 is charged.
Log event: 2025-11-07 10:58:50-835: Vehicle avg.10 is charged.
Log event: 2025-11-07 10:58:51-844: Vehicle avg.13 is charging.
Log event: 2025-11-07 10:58:51-844: Vehicle avg.13 is charged.
Log event: 2025-11-07 10:58:53-862: Vehicle avg.7 is charged.
Log event: 2025-11-07 10:59:10-998: Vehicle avg.3 is charged.
Log event: 2025-11-07 10:59:12-005: Vehicle avg.0 is charged.
```

Pay attention to how different AVGs take different times to charge depending on their remaining balance and when they entered the queue to be charged. Here is the output of the other charging station "2025-11-07 11-02-26-525ChargingStation": 

```
Log event: 
Log event: 2025-11-07 11:02:28-539: Vehicle avg.8 is charging.
Log event: 2025-11-07 11:02:28-539: Vehicle avg.4 is charging.
Log event: 2025-11-07 11:02:28-539: Vehicle avg.0 is charging.
Log event: 2025-11-07 11:02:28-539: Vehicle avg.23 is charging.
Log event: 2025-11-07 11:02:28-539: Vehicle avg.21 is charging.
Log event: 2025-11-07 11:02:28-539: Vehicle avg.19 is charging.
Log event: 2025-11-07 11:02:28-539: Vehicle avg.17 is charging.
Log event: 2025-11-07 11:02:28-539: Vehicle avg.8 is charged.
Log event: 2025-11-07 11:02:28-539: Vehicle avg.0 is charged.
Log event: 2025-11-07 11:02:29-567: Vehicle avg.4 is charging.
Log event: 2025-11-07 11:02:29-567: Vehicle avg.23 is charging.
Log event: 2025-11-07 11:02:29-567: Vehicle avg.21 is charging.
Log event: 2025-11-07 11:02:29-567: Vehicle avg.4 is charged.
Log event: 2025-11-07 11:02:29-567: Vehicle avg.21 is charged.
Log event: 2025-11-07 11:02:29-567: Vehicle avg.17 is charged.
Log event: 2025-11-07 11:02:30-585: Vehicle avg.23 is charging.
Log event: 2025-11-07 11:02:30-585: Vehicle avg.19 is charging.
Log event: 2025-11-07 11:02:30-585: Vehicle avg.19 is charged.
Log event: 2025-11-07 11:02:30-585: Vehicle avg.13 is charged.
Log event: 2025-11-07 11:02:31-588: Vehicle avg.23 is charging.
Log event: 2025-11-07 11:02:31-588: Vehicle avg.23 is charged.
Log event: 2025-11-07 11:02:31-588: Vehicle avg.11 is charged.
Log event: 2025-11-07 11:02:31-588: Vehicle avg.5 is charged.
Log event: 2025-11-07 11:02:32-597: Vehicle avg.9 is charged.
Log event: 2025-11-07 11:02:33-603: Vehicle avg.15 is charged.
Log event: 2025-11-07 11:02:35-635: Vehicle avg.1 is charged.
```

For the questions posed in the assignment: 

- Comparison of concurrency models: 

  1. Parallel Workers:
    - Pros:
        - Easy to understand.
        - to increase parallelisation level, just add more workers.
    - Cons:
        - Shared state can get complex: workers might need to access some shared data which can cause issues if the local memory of the workers is not updated correctly in line with the shared memory or database. (basically avoiding race conditions)
        - Stateless workers: this relates to cache coherence. Reading shared data needs constant snooping by all workers and if there is no local version of the state but just a constant reread every time, such a worker is called stateless
        - Non deterministic job ordering: In such a model, there is no way to determine the job execution order since it is done by the OS scheduler. It doesn't guarantee the task finishing order either.
     
  2. Assembly Line:
     - Pros:
         - No shared state: Concurrency is not an issue here since each worker has their own independent state which is not shared with the others. Essentially, a single threaded implementation.
         - Stateful workers: Each worker has their own state since there is no shared data so there is no risk of them being stateless.
         - Better hardware conformity: Single threaded implementation better conforms with how the underlying hardware works. Code is written in a manner where it works really well with the hardware leading to faster data access.
         - Job ordering is possible: implementing job ordering makes it easier to reason about the state of a system. The information can also be stored in a log making it easier to rebuild the system state from scratch in case of a system failure.
      - Cons:
          - Compared to parallel workers, the main disadvantage here is that the execution of the job is spread over multiple workers and multiple classes. It can thus become harder to see exactly where code is being executed for a given job.
          - It can also be harder to write the code for it due to a lot of nested callback handlers so the trace can also be difficult to keep track of.
       

- Differences between concurrency and parallelism: 

  1. Basic definition: Both terms deal with multiple tasks at once but the main difference is that with concurrency, it might not be necessarily at the same time whereas in parallelism, the tasks are executed at the same time.
  2. In concurrency, the tasks could be switched between one another but not in parallelism since tasks there are executed at the same time.
  3. Concurrency can be established on a single core system but parallelism requires a multi-core system to be implemented.
 

- Differences between Blocking vs Non-blocking Concurrency Algorithms:

    1. Blocking concurrency algorithms are where the threads are blocked when they wait for a resource to become free (eg. Mutex) while in a non-blocking concurrency algorithm, threads never block and just continue working or retry later when resources get free.
    2. Blocking currency is not as efficient since the execution time is slower due to idle threads when a resource is locked but non-blocking currency is more efficient since there is no thread waiting or blocking.
    3. Blocking currency algorithms are great for tasks where well defined execution sequence is needed. This is useful for File IO, GUI applications, multithreaded applications. On the other hand, non blocking currency algorithms are great for tasks that need high performance and scalability. This is particularly useful in real time systems, multi-core systems, and distributed systems. 
