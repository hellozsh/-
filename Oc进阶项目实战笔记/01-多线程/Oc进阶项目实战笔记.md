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

GCD 在后端管理着一个[线程池](http://en.wikipedia.org/wiki/Thread_pool_pattern)。GCD 不仅决定着你的代码块将在哪个线程被执行，它还根据可用的系统资源对这些线程进行管理

### Dispatch Queue

执行处理的等待队列。应用程序编程人员通过dispatch_async函数等API，在Block语法中记述想执行的处理并将其追加到Dispatch Queue中，Dispatch Queue按照追加的顺序(先进先出FIFO)执行处理。一个队列一次只能调用一个block，但是独立的队列可以彼此同时调用它们的块。

线程池来负责处理队列以及调度提交给队列的block，

通过集中的管理线程，来缓解大量线程被创建的问题。

GCD 公开有 5 个不同的队列：运行在主线程中的 main queue，3 个不同优先级的后台队列，以及一个优先级更低的后台队列（用于 I/O）。 另外，开发者可以创建自定义队列：串行或者并行队列。自定义队列非常强大，在自定义队列中被调度的所有 block 最终都将被放入到<font color=#FF0000>系统的全局队列中和线程池</font>中。

全局队列的底层是一个线程池，向全局队列中提交的 block，都会被放到这个线程池中执行，如果线程池已满，后续再提交 block 就不会再重新创建线程，

因为整个 APP 是在共享一个全局队列的线程池，那么如果 APP 把线程池沾满了，甚至线程池长时间占满且不结束，那么 AFNetworking 就自然不能再执行任务了，所以我们看到，即使是只会创建一条常驻线程， AFNetworking 依然采用了 NSThread 的方式而非 GCD 全局队列这种方式来创建常驻线程。

![队列和线程池](/Oc进阶项目实战笔记/队列和线程池.png)

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

## Run Loops

实际上，Run loop并不像 GCD 或者操作队列那样是一种并发机制，因为它并不能并行执行任务。不过在主 dispatch/operation 队列中， run loop 将直接配合任务的执行，它提供了一种<font color=#FF0000>异步执行代码</font>的机制。

Run loop 比起操作队列或者 GCD 来说容易使用得多，因为通过 run loop ，你不必处理并发中的复杂情况，就能异步地执行任务。

一个 run loop 总是绑定到某个特定的线程中。main run loop 是与主线程相关的，在每一个 Cocoa 和 CocoaTouch 程序中，这个 main run loop 都扮演了一个核心角色，它负责处理 UI 事件、计时器，以及其它内核相关事件。无论你什么时候设置计时器、使用 `NSURLConnection` 或者调用 `performSelector:withObject:afterDelay:`，其实<font color=#FF0000>背后都是 run loop 在处理这些异步任务</font>。

关于这点，在 iOS 中非常典型的一个示例就是滚动。在进行滚动时，run loop 并不是运行在默认模式中的，因此， run loop 此时并不会响应比如滚动前设置的计时器。一旦滚动停止了，run loop 会回到默认模式，并执行添加到队列中的相关事件。如果在滚动时，希望计时器能被触发，需要将其设为 `NSRunLoopCommonModes` 的模式，并添加到 run loop 中。

主线程一般来说都已经配置好了 main run loop。然而其他线程默认情况下都没有设置 run loop。你也可以自行为其他线程设置 run loop, 如果你真需要在别的线程中添加一个 run loop ，那么不要忘记在 run loop 中至少添加一个 input source 。如果 run loop 中没有设置好的 input source，那么每次运行这个 run loop ，它都会立即退出。

### 线程和Runloop的关系

1. runloop与线程是一一对应的，一个runloop对应一个核心的线程，为什么说是核心的，是因为runloop是可以嵌套的，但是核心的只能有一个，他们的关系保存在一个全局的字典里。
2. runloop是来管理线程的，当线程的runloop被开启后，线程会在执行完任务后进入休眠状态，有了任务就会被唤醒去执行任务。
3. runloop在第一次获取时被创建，在线程结束时被销毁。
4. 对于主线程来说，runloop在程序一启动就默认创建好了。
5. 对于子线程来说，runloop是懒加载的，只有当我们使用的时候才会创建，所以在子线程用定时器要注意：确保子线程的runloop被创建，不然定时器不会回调。

runloop = dict[key 线程指针]，runloop的执行是建立在线程上面的，线程的执行和runloop没有关系，runloop没建立，线程也可以执行，runloop可以保证线程不退出，常驻线程，而time计时器需要依赖runloop，所以在子线程使用定时器时，需要先确保子线程的runloop被创建

创建线程[ [NSRunLoop currentRunLoop] run]

## 并发编程中面临的挑战

使用并发编程会带来许多陷阱。只要一旦你做的事情超过了最基本的情况，对于并发执行的多任务之间的相互影响的不同状态的监视就会变得异常困难。 问题往往发生在一些不确定性（不可预见性）的地方，这使得在调试相关并发代码时更加困难。

### 资源共享

并发编程中许多问题的根源就是在多线程中访问共享资源。资源可以是一个属性、一个对象，通用的内存、网络设备或者一个文件等等。在多线程中任何一个共享的资源都可能是一个潜在的冲突点，你必须精心设计以防止这种冲突的发生。

我们举一个关于资源的简单示例：比如仅仅用一个整型值来做计数器。在程序运行过程中，我们有两个并行线程 A 和 B，这两个线程都尝试着同时增加计数器的值。问题来了，你通过 C 语言或 Objective-C 写的代码大多数情况下对于 CPU 来说不会仅仅是一条机器指令。要想增加计数器的值，当前的必须被从内存中读出，然后增加计数器的值，最后还需要将这个增加后的值写回内存中。

我们可以试着想一下，如果两个线程同时做上面涉及到的操作，会发生怎样的偶然。例如，线程 A 和 B 都从内存中读取出了计数器的值，假设为 `17` ，然后线程A将计数器的值加1，并将结果 `18` 写回到内存中。同时，线程B也将计数器的值加 1 ，并将结果 `18` 写回到内存中。实际上，此时计数器的值已经被破坏掉了，因为计数器的值 `17` 被加 1 了两次，而它的值却是 `18`。

这个问题被叫做[竞态条件](http://en.wikipedia.org/wiki/Race_conditions#Software)，在多线程里面访问一个共享的资源，如果没有一种机制来确保在线程 A 结束访问一个共享资源之前，线程 B 就不会开始访问该共享资源的话，资源竞争的问题就总是会发生。如果你所写入内存的并不是一个简单的整数，而是一个更复杂的数据结构，可能会发生这样的现象：当第一个线程正在写入这个数据结构时，第二个线程却尝试读取这个数据结构，那么获取到的数据可能是新旧参半或者没有初始化。为了防止出现这样的问题，多线程需要一种互斥的机制来访问共享资源。

在实际的开发中，情况甚至要比上面介绍的更加复杂，因为现代 CPU 为了优化目的，往往会改变向内存读写数据的顺序（[乱序执行](http://en.wikipedia.org/wiki/Out-of-order_execution)）。

### 互斥锁

[互斥](http://en.wikipedia.org/wiki/Mutex)访问的意思就是同一时刻，只允许一个线程访问某个特定资源。为了保证这一点，每个希望访问共享资源的线程，首先需要获得一个共享资源的[互斥锁](http://en.wikipedia.org/wiki/Lock_(computer_science))，一旦某个线程对资源完成了操作，就释放掉这个互斥锁，这样别的线程就有机会访问该共享资源了。

除了确保互斥访问，还需要解决代码无序执行所带来的问题。如果不能确保 CPU 访问内存的顺序跟编程时的代码指令一样，那么仅仅依靠互斥访问是不够的。为了解决由 CPU 的优化策略引起的副作用，还需要引入[内存屏障](http://en.wikipedia.org/wiki/Memory_barrier)。通过设置内存屏障，来确保没有无序执行的指令能跨过屏障而执行。

当然，互斥锁自身的实现是需要没有竞争条件的。将一个属性声明为 atomic 就能支持互斥锁表示每次访问该属性都会进行隐式的加锁和解锁操作

在资源上的加锁会引发一定的性能代价。<font color=#FF0000>获取锁和释放锁的操作本身也需要没有竞态条件，这在多核系统中是很重要的</font>。

在这里有一个东西需要进行权衡：获取和释放锁所是要带来开销的，因此你需要确保你不会频繁地进入和退出[临界区段](http://en.wikipedia.org/wiki/Critical_section)（比如获取和释放锁）。同时，如果你获取锁之后要执行一大段代码，这将带来锁竞争的风险：其它线程可能必须等待获取资源锁而无法工作([锁的竞争](http://en.wikipedia.org/wiki/Lock_(computer_science)#Granularity)是这样产生的：当一个或者多个线程尝试获取一个已经被别的线程获取过了的锁)。

### 死锁

互斥锁解决了竞态条件的问题，但很不幸同时这也引入了一些[其他问题](http://en.wikipedia.org/wiki/Lock_(computer_science)#The_problems_with_locks)，其中一个就是[死锁](http://en.wikipedia.org/wiki/Deadlock)。当多个线程在相互等待着对方的结束时，就会发生死锁，这时程序可能会被卡住。

![死锁](/Oc进阶项目实战笔记/死锁.png)

此时程序可能会由于死锁而被终止。线程 1 获得了 X 的一个锁，线程 2 获得了 Y 的一个锁。 接着它们会同时等待另外一把锁，但是永远都不会获得。

再说一次，你在线程之间共享的资源越多，你使用的锁也就越多，同时程序被死锁的概率也会变大。这也是为什么我们需要尽量减少线程间资源共享，并确保共享的资源尽量简单的原因之一

### 资源饥饿（Starvation）

当你认为已经足够了解并发编程面临的问题时，又出现了一个新的问题。锁定的共享资源会引起[读写问题](http://en.wikipedia.org/wiki/Readers-writers_problem)。大多数情况下，限制资源一次只能有一个线程进行读取访问其实是非常浪费的。因此，在资源上没有写入锁的时候，持有一个读取锁是被允许的。这种情况下，如果一个持有读取锁的线程在等待获取写入锁的时候，其他希望读取资源的线程则因为无法获得这个读取锁而导致[资源饥饿](http://en.wikipedia.org/wiki/Resource_starvation)的发生。

### 优先级反转

<font color=#FF0000>优先级反转是指程序在运行时低优先级的任务阻塞了高优先级的任务，有效的反转了任务的优先级</font>。

高优先级和低优先级的任务之间共享资源时，就可能发生优先级反转。当低优先级的任务获得了共享资源的锁时，该任务应该迅速完成，并释放掉锁，这样高优先级的任务就可以在没有明显延时的情况下继续执行。然而高优先级任务会在低优先级的任务持有锁的期间被阻塞。如果这时候有一个中优先级的任务(该任务不需要那个共享资源)，那么它就有可能会抢占低优先级任务而被执行，因为此时高优先级任务是被阻塞的，所以中优先级任务是目前所有可运行任务中优先级最高的。此时，中优先级任务就会阻塞着低优先级任务，导致低优先级任务不能释放掉锁，这也就会引起高优先级任务一直在等待锁的释放。

遇到优先级反转时，一般没那么严重。解决这个问题的方法，通常就是不要使用不同的优先级。通常最后你都会以让高优先级的代码等待低优先级的代码来解决问题，如果你在编程中，遇到高优先级的任务突然没理由地卡住了，可能你会想起本文

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

不论使用 `pthread` 还是 `NSThread` 来直接对线程操作，都是相对糟糕的编程体验，这种方式并不适合我们以写出良好代码为目标的编码精神。

直接使用线程可能会引发的一个问题是，如果你的代码和所基于的框架代码都创建自己的线程时，那么活动的线程数量有可能以指数级增长。这在大型工程中是一个常见问题。例如，在 8 核 CPU 中，你创建了 8 个线程来完全发挥 CPU 性能。然而在这些线程中你的代码所调用的框架代码也做了同样事情（因为它并不知道你已经创建的这些线程），这样会很快产生成成百上千的线程。代码的每个部分自身都没有问题，然而最后却还是导致了问题。使用线程并不是没有代价的，每个线程都会消耗一些内存和内核资源。

接下来，我们将介绍两个基于队列的并发编程 API ：GCD 和 operation queue 。它们通过集中管理一个被大家协同使用的[线程池](http://en.wikipedia.org/wiki/Thread_pool_pattern)，来解决上面遇到的问题。

## 锁

### 自旋锁

转转-忙等-用于代码较小耗时较少

### 互斥锁

[互斥](http://en.wikipedia.org/wiki/Mutex)访问的意思就是同一时刻，只允许一个线程访问某个特定资源

为了保证这一点，每个希望访问共享资源的线程，首先需要获得一个共享资源的[互斥锁](http://en.wikipedia.org/wiki/Lock_(computer_science))，一旦某个线程对资源完成了操作，就释放掉这个互斥锁，这样别的线程就有机会访问该共享资源了。

从语言层面来说，在 Objective-C 中将属性以 atomic 的形式来声明，就能支持互斥锁了。事实上在默认情况下，属性就是 atomic 的。将一个属性声明为 atomic 表示每次访问该属性都会进行隐式的加锁和解锁操作。虽然最把稳的做法就是将所有的属性都声明为 atomic，但是加解锁这也会付出一定的代价。



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





##异步渲染

## 优先级反转

https://www.jianshu.com/p/c557308c0ec5

http://zenonhuang.me/2018/03/08/technology/2018-03-01-LockForiOS/

新版 iOS 中，系统维护了 5 个不同的**线程优先级**/QoS: background，utility，default，user-initiated，user-interactive。高优先级线程始终会在低优先级线程前执行，一个线程不会受到比它更低优先级线程的干扰。这种线程调度算法会产生潜在的**优先级反转**问题，从而破坏了 spin lock(自旋锁)

