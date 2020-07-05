---
typora-root-url: ../../StudyNote
---

# Runtime



# 多线程

## 面试题

1. 你理解的多线程？
2. iOS的多线程方案有哪几种？你更倾向于哪一种？
3. 你在项目中用过 GCD 吗？GCD 的队列类型
4. 说一下 OperationQueue 和 GCD 的区别，以及各自的优势
5. 线程安全的处理手段有哪些？
6. OC你了解的锁有哪些？在你回答基础上进行二次提问；
   1. 追问一：自旋和互斥对比？
   2. 追问二：使用以上锁需要注意哪些？
   3. 追问三：用C/OC/C++，任选其一，实现自旋或互斥？口述即可！

## 什么是多线程

OS X和iOS的核心XNU内核在发生操作系统事件时(如每隔一段时间，唤起系统调用等情况)会切换执行路径。执行中路径的状态，例如CPU的寄存器等信息保存到各自路径专用的内存块中，从切换目标路径专用的内存块中，复原CPU寄存器等信息，继续执行切换路径的CPU命令行。这就被称为"上下文切换"

​	由于使用多线程的程序可以在某个线程和其他线程之间反复多次进行上下文切换，因此看上去就好像1个CPU核能够并列地执行多个线程一样。而且在具有多个CPU核的情况下，就不是"看上去像"了，而是真的提供了多个CPU核并行执行多个线程的技术

​	这种利用多线程编程的技术就被称为“多线程编程”。

​	如果过多使用多线程，就会消耗大量内存，引起大量的上下文切换，大幅度降低系统的响应性能

## GCD

### Dispatch Queue

执行处理的等待队列。应用程序编程人员通过dispatch_async函数等API，在Block语法中记述想执行的处理并将其追加到Dispatch Queue中，Dispatch Queue按照追加的顺序(先进先出FIFO)执行处理。

#### 串行队列(Serial Dispatch Queue)

因为要等待现在执行中的处理结束，所以首先执行blk0，blk0执行结束后，接着执行blk1，blk1结束后再开始执行blk2，如此重复。同时执行的处理数只能有1个。

​	只在为了避免多线程编程问题之一——多个线程更新相同资源导致数据竞争时使用Serial Dispatch Queue

​	Serial Dispatch Queue的生成个数应当仅限所必需的数量，例如更新数据库时一个表生成一个Serial Dispatch Queue，更新文件时一个文件或是可以分割的1个文件块生成1个Serial Dispatch Queue。绝不能激动之下大量生成Serial Dispatch Queue

#### 并发队列(Concurrent Dispatch Queue)

因为不用等待现在执行中的处理结束，所以首先执行blk0，不管blk0的执行是否结束，都开始执行后面的blk1，不管blk1的执行是否结束，都开始执行后面的blk2，如此重复循环

​	这样虽然不用等待处理结束，可以并行执行多个处理，但并行执行的处理数量取决于当前系统的状态。即iOS和OS X基于Dispatch Queue中的处理数，CPU核数以及CPU负荷等当前系统的状态来决定并发队列中并行执行的处理数

​	iOS和OS X的核心——XNU内核决定应当使用的线程数，并只生成所需的线程执行处理。另外，当处理结束，应当执行的处理数减少时，XNU内核会结束不再需要的线程。

​	假设准备4个Concurrent Dispatch Queue用线程，首先blk0再线程0中开始执行，接着blk1在线程1中、blk2在线程2中、blk3在线程3中开始执行。线程0中blk0执行结束后开始执行blk4，由于线程1中blk1的执行没有结束，因此线程2中blk2执行结束后开始执行blk5，就这样循环往复

#### dispatch_queue_create

dispatch_queue_create可生成Dispatch Queue，虽然Serial Dispatch Queue和Concurrent Dispatch Queue受到系统资源的限制，但用dispatch_queue_create函数可生成任意多个Dispatch Queue

​		当生成多个Serial Dispatch Queue时，各个Serial Dispatch Queue将并行执行。虽然在1个Serial Dispatch Queue中同时只能执行一个追加处理，但如果将处理分别追加到4个Serial Dispatch Queue中，各个Serial Dispatch Queue执行1个，即为同时执行4个处理。

​      对于Concurrent Dispatch Queue来说，不管生成多少，由于XNU内核只使用有效管理的线程，因此不会发生Serial Dispatch Queue的那些问题

​	在iOS6.0以下通过dispatch_queue_create生成的Dispatch Queue在使用结束后通过dispatch_release函数释放，iOS6.0以及以上ARC会自动管理

#### 系统提供的Dispatch Queue

​	Main Dispatch Queue是在主线程执行的Dispatch Queue，因为主线程只有一个，所以Main Dispatch Queue自然就是Serial Dispatch Queue，追加到Main Dispatch Queue的处理在主线程的RunLoop中执行。

​	Global Dispatch Queue有四个执行优先级,4个全局队列，分别是高优先级(High Priority)、默认优先级(Default Priority)、低优先级(Low Priority)和后台优先级(Background Priority)。通过XNU内核管理的用于Global Dispatch Queue的线程，将各自使用的Global Dispatch Queue的执行优先级作为线程的执行优先级使用。

#### 为创建的Dispatch Queue创建优先级

​	自己创建的Dispatch Queue优先级都使用与默认优先级Global Dispatch Queue相同执行优先级的线程。而变更自己创建的Dispatch Queue执行优先级要使用dispatch_set_target_queue函数

```objective-c
dispatch_queue_t mySerialDispatchQueue = dispatch_queue_create("com.example.gcd.MySerialDispatchQueue",NULL);
dispatch_queue_ t globalDispatchQueueBackground = dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_BACKGROUND,0);
dispatch_set_target_queue(mySerialDispatchQueue, globalDispatchQueueBackground);
```

iOS 8之后建议用dispatch_queue_attr_t设置优先级

```objective-c
//iOS 8以上 
dispatch_queue_attr_t attr = dispatch_queue_attr_make_with_qos_class(DISPATCH_QUEUE_SERIAL, QOS_CLASS_USER_INITIATED, 0);
dispatch_queue_t queues = dispatch_queue_create("com.yh.render", attr);
```

#### 延迟执行(dispatch_after)

​	想在指定时间后执行处理的情况，可使用dispatch_after函数来实现

​	需要注意的是, dispatch_after函数并不是在指定时间后执行处理，而只是在指定时间追加处理到Dispatch Queue。此源代码与在3秒后用dispatch_async函数追加Block到Main Dispatch Queue的相同。

​	因为Main Dispatch Queue在主线程的RunLoop中执行，所以在比如每隔1/60秒执行的RunLoop中，Block最快在3秒后执行，最慢在3秒+1/60秒后执行，并且在Main Dispatch Queue有大量处理追加或主线程的处理本身有延迟时，这个时间会更长

```objective-c
// 从第一个参数中指定的时间开始，到第二个参数指定的毫微秒单位时间后的时间
    // ull 是C语言的数值字面量，是显式表明类型时使用的字符串("unsigned long long")
//    dispatch_time_t time = dispatch_time(DISPATCH_TIME_NOW, 3ull*NSEC_PER_SEC);
   
   // 第一个参数是指定时间用的dispatch_time_t类型值,该值使用dispatch_time函数或dispatch_walltime函数作成
    // dispatch_walltime用于计算绝对时间
    NSTimeInterval interval = [[NSDate date] timeIntervalSince1970];
    double second;
    double subsecond = modf(interval, &second);
    
    struct timespec time;
    time.tv_sec = second;
    time.tv_nsec = subsecond * NSEC_PER_SEC;
    dispatch_time_t miletone = dispatch_walltime(&time, 0);
   
    dispatch_after(miletone, dispatch_get_main_queue(), ^{
       
        NSLog(@"waited at least three seconds");
    });
```

#### Dispatch Group

​	1. 在追加到Dispatch Queue中的多个处理全部结束后想执行结束处理。无论向什么样的Dispatch Queue中追加处理，使用Dispatch Group都可监视这些处理执行的结束。一旦检测到所有处理执行结束，就可将结束的处理追加到Dispatch Queue中

在追加到Dispatch Group中的处理全部执行结束时，该源代码中使用的dispatch_group_notify函数会将执行的Block追加到Dispatch Queue中，将第一个参数指定为要监视的Dispatch Group。在追加到该Dispatch Group的全部处理执行结束时，将第三个参数的Block追加到第二个参数的Dispatch Queue中。

​	2. 也可以使用dispatch_group_wait函数仅等待全部处理执行结束

dispatch_group_wait函数的第二个参数指定为等待的时间(超时)。它属于dispatch_time_t类型的值。一旦调用dispatch_group_wait函数，该函数就处于调用的状态而不返回。即执行dispatch_group_wait函数的现在的线程(当前线程)停止。

```objective-c
dispatch_queue_t queue = dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0);
    dispatch_group_t group = dispatch_group_create();
    
    dispatch_group_async(group, queue, ^{
        sleep(2);
        NSLog(@"blk0");
    });
    dispatch_group_async(group, queue, ^{
        sleep(2);
        NSLog(@"blk1");
    });
    dispatch_group_async(group, queue, ^{
        sleep(6);
        NSLog(@"blk2");
    });
    // 不会阻碍主线程
    dispatch_group_notify(group, dispatch_get_main_queue(), ^{
       NSLog(@"done");
    });
    // 会让dispatch_group_wait的当前线程停止(这里是写在主线程上)；
//    long result = dispatch_group_wait(group, DISPATCH_TIME_FOREVER);
//    if (result == 0) {
//       NSLog(@"done");
//    }
```

##### dispatch_group_enter、dispatch_group_leave

```objective-c
dispatch_queue_t queue = dispatch_get_global_queue(0, 0);
    dispatch_group_t group = dispatch_group_create();
    
    dispatch_group_enter(group);
    dispatch_async(queue, ^{
        NSLog(@"第一个走完了");
        dispatch_group_leave(group);
    });
    
    dispatch_group_enter(group);
    dispatch_async(queue, ^{
        NSLog(@"第二个走完了");
        dispatch_group_leave(group);
    });
    dispatch_group_notify(group, dispatch_get_main_queue(), ^{
        NSLog(@"所有任务完成，可以更新UI");
    });
```

Dispatch Group的本质是一个初始value为LONG_MAX的semaphore，通过信号量来实现一组任务的管理

`dispatch_group_enter`的逻辑是将`dispatch_group_t`转换成`dispatch_semaphore_t`后将`dsema_value`的值减一。

`dispatch_group_leave`的逻辑是将`dispatch_group_t`转换成`dispatch_semaphore_t`后将`dsema_value`的值加一。

当调用了`dispatch_group_enter`而没有调用`dispatch_group_leave`时，会造成value值不等于LONG_MAX而不会走到唤醒逻辑，`dispatch_group_notify`函数的block无法执行或者`dispatch_group_wait`收不到`semaphore_signal`信号而卡住线程。

当`dispatch_group_leave`比`dispatch_group_enter`多调用了一次时，dispatch_semaphore_t的value会等于LONGMAX+1（2147483647+1）,即long的负数最小值LONG_MIN(–2147483648)。因为此时value小于0，所以会出现"Unbalanced call to dispatch_group_leave()"的崩溃

#### dispatch_barrier_async

多读单写问题：为了高效率地进行访问，读取处理可以并行执行，写入处理不可以与其他写入处理以及包含读取处理并行执行

​	dispatch_barrier_async，该函数同dispatch_queue_create函数生成的Concurrent Dispatch Queue一起使用(全局队列使用无效)

​	dispatch_barrier_async函数会等待追加到Concurrent Dispatch Queue上的并行执行的处理全部结束之后，再将指定的处理追加到该Concurrent Dispatch Queue中。然后再由dispatch_barrier_async函数追加的处理执行完毕后，Concurrent Dispatch Queue才恢复为一般的动作，追加到该Concurrent Dispatch Queue的处理又开始并行执行。

```objective-c
dispatch_queue_t queue = dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0);
    dispatch_group_t group = dispatch_group_create();
    
    dispatch_group_async(group, queue, ^{
        NSLog(@"blk0");
    });
    dispatch_group_async(group, queue, ^{
        NSLog(@"blk1");
    });
    dispatch_group_async(group, queue, ^{
        NSLog(@"blk2");
    });
    dispatch_async(dispatch_get_global_queue(0, 0), ^{
        
        dispatch_group_notify(group, dispatch_get_main_queue(), ^{
            NSLog(@"donw");
        });
        NSLog(@"dispatch_group_wait 结束");
    });
```

#### dispatch_sync

dispatch_async：不做任何等待，将指定的Block"非同步"地追加到指定的Dispatch Queue中

dispatch_sync: 等待处理执行结束, 将指定的Block"同步"地追加到指定的Dispatch Queue中,如dispatch_group_wait说明:"等待"意味着当前线程停止

```objective-c
dispatch_queue_t queue = dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0);
    dispatch_sync(queue, ^{

        NSLog(@"test --- sync start");
        sleep(5);
        NSLog(@"test --- sync end");
    });
    NSLog(@"哈哈哈哈哈哈哈");
    // test --- sync start    test --- sync end    哈哈哈哈哈哈哈
    
    dispatch_queue_t queue = dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0);
    dispatch_async(queue, ^{

        NSLog(@"test --- sync start");
        sleep(5);
        NSLog(@"test --- sync end");
    });
    NSLog(@"哈哈哈哈哈哈哈");
    // test --- sync start  哈哈哈哈哈哈哈   test --- sync end    
```

##### 使用场景: 

​	执行Main Dispatch Queue时，使用另外的线程Global Dispatch Queue进行处理，处理结束后立即使用所得到的结果。在这种情况下就要使用dispatch_sync函数

​	dispatch_sync使用简单，也容易引起死锁

​	如在主线程执行以下源代码就会死锁: 该源代码在主线程执行的Block，并等待其执行结束，而其实在主线程正在执行这些源代码，所以无法执行追加到主线程的Block

```objective-c
dispatch_queue_t queue = dispatch_get_main_queue（）；
dispatch_sync(queue, ^{NSLog(@"Hello?")}); 
```

#### dispatch_apply

dispatch_apply是dispatch_sync和Dispatch Queue的关联API，该函数按指定的次数将指定的Block追加到指定的Dispatch Queue中，并等待全部处理执行结束

​	输出结果中最后的done必定在最后的位置上。这是因为dispatch_apply函数会等待全部处理执行结束。

```objective-c
dispatch_queue_t queue = dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0);
    dispatch_apply(10, queue, ^(size_t index) {
        NSLog(@"%zu",index);
    });
    NSLog(@"done");

// 推荐用法
dispatch_asynce(queue, ^{
  dispatch_apply(10, queue,....
  
});
```

由于dispatch_apply函数会等待全部处理执行结束，所以推荐在dispatch_async函数中"非同步"地执行dispatch_apply函数

#### dispatch_suspend/dispatch_resume

dispatch_suspend： 挂起指定的dispatch Queue

dispatch_resume: 恢复指定的dispatch queue

挂起后，追加到dispatch queue中但尚未执行的处理在此之后停止执行，而恢复则使得这些处理能够继续执行。

#### Dispatch Semaphore

Dispatch Semaphore是持有计数的信号

dispatch_semaphore_wait函数等待Dispatch Semaphore的计数值达到大于或等于1

dispatch_semaphore_signal将Dispatch Semaphore的计数值+1

```objective-c
dispatch_queue_t queue = dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0);
    dispatch_semaphore_t semaphore = dispatch_semaphore_create(1);
    
   
    dispatch_async(queue, ^{
       
         dispatch_semaphore_wait(semaphore, DISPATCH_TIME_FOREVER);
        NSLog(@"test --- async start");
        sleep(5);
        NSLog(@"test --- async end");
        dispatch_semaphore_signal(semaphore);
    });
     // 会让当前线程停止
    dispatch_semaphore_wait(semaphore, DISPATCH_TIME_FOREVER);
    dispatch_async(queue, ^{
       
        NSLog(@"111test --- async start");
        sleep(5);
        NSLog(@"111test --- async end");
        dispatch_semaphore_signal(semaphore);
    });
```

#### dispatch_once

#### dispatch I/O

如想提高文件读取速度，可以尝试使用Dispatch I/O

## 线程的定义

+ <font color=#FF0000 >线程是进程的基本执行单元</font>，一个进程的所有任务都在线程中执行
+ 进程要想执行任务，必须得有线程，进程至少要有一条线程
+ <font color=#FF0000 >程序启动会默认开启一条线程</font>，这条线程被称为主线程或UI线程

## 进程的定义

+ 进程是指在系统中正在运行的一个应用程序
+ 每个进程之间是独立的，每个进程均运行在其专用的且受保护的内存

## 进程与线程的关系

+ 地址空间：同一进程的线程共享本进程的地址空间，而进程之间则是独立的地址空间
+ 资源拥有：同一进程内的线程共享本进程的资源如内存、I/O、cpu等，但是进程之间的资源是独立的。
+ 执行过程：每个独立的进程有一个程序运行的入口、顺序执行序列和程序入口。但是线程不能独立执行，必须依存在应用程序中，由应用程序提供多个线程执行控制。
+ 线程是处理器调度的基本单位，但是进程不是。

## 多线程的意义

+ 优点

  + 能适当提高程序的执行效率
  + 能适当提高资源的利用率（CPU，内存）
  + 线程上的任务执行完成后，线程会自动销毁

+ 缺点

  + 开启线程需要占用一定的内存空间（默认情况下，每一个线程都占 512 KB）[^表现]

  + 如果开启大量的线程，会占用大量的内存空间，降低程序的性能

  + 线程越多，CPU 在调用线程上的开销就越大

  + 程序设计更加复杂，比如线程间的通信、多线程的数据共享

    [^表现]:主线程开一个线程里输出一个打印，再在主线程输出一个打印，先调用的是后面主线程的这个打印，因为开线程有一定的耗时

## 多线程的原理

+ 多线程指的是多个线程"同时"执行[^同时]

+ Oc项目启动时，系统会自己开辟好几个线程，用于他的操作，我们可以给线程设置名字用于区分功能

  [^同时]: 1个CPU核一次能执行的CPU命令始终为1，使用多线程的程序可以在某个线程和其他线程之间反复多次进行上下文切换，因此看上去就好像1个CPU核能够并列地执行多个线程一样

### 多线程技术方案

| 方案        | 简介                                                         | 语言 | 线程生命周期 | 使用频率 |
| ----------- | ------------------------------------------------------------ | ---- | ------------ | -------- |
| pthread     | 1.一套通用的多线程API<br/>2.适用于Unix\Linux\Windows等系统<br/>3.跨平台\可移植<br/>4.使用难度大 | C    | 程序员管理   | 几乎不用 |
| NSThread    | 1.使用更加面向对象<br/>2.简单易用，可直接操作线程对象        | OC   | 程序员管理   | 偶尔使用 |
| GCD         | 1.旨在替代NSThread等线程技术<br/>2.充分利用设备的多核        | C    | 自动管理     | 经常使用 |
| NSOperation | 1.基于GCD<br/>2.比GCD多了一些更简单实用的功能<br/>3.使用更加面向对象 | OC   | 自动管理     | 经常使用 |

NSThread、GCD、NSOperation底层都是用的pthread

###多线程生命周期![多线程生命周期](/Oc进阶项目实战笔记/多线程生命周期.png)

### 线程池原理

![饱和策略](/Oc进阶项目实战笔记/饱和策略.png)

### GCD的常用函数

+ GCD中有2个用来执行任务的函数

  +  用同步的方式执行任务

    dispatch_<font color=#FF0000 >sync</font>(dispatch_queue_t queue, dispatch_block_t block);

    queue： 队列 

    Block: 任务

  + 用异步的方式执行任务

    dispatch_<font color=#FF0000 >async</font>(dispatch_queue_t queue, dispatch_block_t block); 

### 锁

#### 自旋锁

转转-忙等-用于代码较小耗时较少

#### 互斥锁

打盹-睡觉-

##递归非递归

#### 读写锁

多读单写

写影响读，读不影响写

### atomic与nonatomic 的区别

+ nonatomic 非原子属性， 非线程安全，适合内存小的移动设备
  + 保证同一时间只有一个线程能够写入(但是同一个时间多个线程都可以取值)
  + atomic 本身就有一把锁(自旋锁)
  + 单写多读：单个线程写入，多个线程可以读取 
+ atomic 原子属性(线程安全)，针对多线程设计的，默认值， 线程安全，需要消耗大量的资源

### 线程和Runloop的关系

1. runloop与线程是一一对应的，一个runloop对应一个核心的线程，为什么说是核心的，是因为runloop是可以嵌套的，但是核心的只能有一个，他们的关系保存在一个全局的字典里。
2. runloop是来管理线程的，当线程的runloop被开启后，线程会在执行完任务后进入休眠状态，有了任务就会被唤醒去执行任务。
3. runloop在第一次获取时被创建，在线程结束时被销毁。
4. 对于主线程来说，runloop在程序一启动就默认创建好了。
5. 对于子线程来说，runloop是懒加载的，只有当我们使用的时候才会创建，所以在子线程用定时器要注意：确保子线程的runloop被创建，不然定时器不会回调。

runloop = dict[key 线程指针]，runloop的执行是建立在线程上面的，线程的执行和runloop没有关系，runloop没建立，线程也可以执行，runloop可以保证线程不退出，常驻线程，而time计时器需要依赖runloop，所以在子线程使用定时器时，需要先确保子线程的runloop被创建

创建线程[ [NSRunLoop currentRunLoop] run]



##等source



##异步渲染

## 优先级反转

https://www.jianshu.com/p/c557308c0ec5

http://zenonhuang.me/2018/03/08/technology/2018-03-01-LockForiOS/