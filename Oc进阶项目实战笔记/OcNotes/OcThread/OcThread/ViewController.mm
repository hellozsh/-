//
//  ViewController.m
//  OcThread
//
//  Created by 周素华 on 2020/6/7.
//  Copyright © 2020 周素华. All rights reserved.
//

#import "ViewController.h"
#import "Person.h"

@interface ViewController ()

{
    CGFloat _progress;
    UILabel *_titleLbl;
}
@property (nonatomic, strong) Person *p;

@property (nonatomic, strong) NSThread *t;



@end

#define ___safe_cast_to_long(x) \
({ _Static_assert(sizeof(typeof(x)) <= sizeof(long), \
        "__builtin_expect doesn't support types wider than long"); \
        (long)(x); })
#define __slowpath(x) ((typeof(x))__builtin_expect(___safe_cast_to_long(x), 0l))

#define DISPATCH_QUEUE_ATTR_OVERCOMMIT2IDX(overcommit) \
        ((overcommit) == _dispatch_queue_attr_overcommit_disabled ? \
        DQA_INDEX_NON_OVERCOMMIT : \
        ((overcommit) == _dispatch_queue_attr_overcommit_enabled ? \
        DQA_INDEX_OVERCOMMIT : DQA_INDEX_UNSPECIFIED_OVERCOMMIT))

#define DISPATCH_QUEUE_ATTR_CONCURRENT2IDX(concurrent) \
        ((concurrent) ? DQA_INDEX_CONCURRENT : DQA_INDEX_SERIAL)

#define DISPATCH_QUEUE_ATTR_INACTIVE2IDX(inactive) \
        ((inactive) ? DQA_INDEX_INACTIVE : DQA_INDEX_ACTIVE)

#define DISPATCH_QUEUE_ATTR_AUTORELEASE_FREQUENCY2IDX(frequency) \
        (frequency)

#define DISPATCH_QUEUE_ATTR_PRIO2IDX(prio) (-(prio))

#define DISPATCH_QUEUE_ATTR_QOS2IDX(qos) (qos)

@implementation ViewController

- (void)touchesBegan:(NSSet<UITouch *> *)touches withEvent:(UIEvent *)event {
    
    _progress += 0.1;
    
    CGFloat currentAlpha = MAX(1-_progress*1.1,0);
    CGFloat duration = 0.3;
    
    CABasicAnimation *opacity = [CABasicAnimation animationWithKeyPath:@"opacity"];
    opacity.duration = duration;
    opacity.fromValue = @(currentAlpha);
    opacity.toValue = @(0);
    opacity.removedOnCompletion = NO;
    opacity.fillMode = kCAFillModeForwards;
    [_titleLbl.layer addAnimation:opacity forKey:@"opacity"];
    [_titleLbl setNeedsLayout];
    [_titleLbl layoutIfNeeded];
    
    
    
}

- (void)viewDidLoad {
    [super viewDidLoad];
    
    
    _titleLbl = [[UILabel alloc] initWithFrame:CGRectMake(100, 100, 40, 40)];
    _titleLbl.text = @"12";
    [self.view addSubview:_titleLbl];
    
    UILabel *titleLbl2 = [[UILabel alloc] initWithFrame:CGRectMake(100, 190, 90, 40)];
    titleLbl2.text = @"34";
    [self.view addSubview:titleLbl2];
      
    return;
     
    // 会崩溃
//    dispatch_queue_t serQue = dispatch_queue_create("com.ser", DISPATCH_QUEUE_SERIAL);

//    dispatch_async(serQue, ^{
//
//        NSLog(@"1111-------%@",[NSThread currentThread]);  // 1111-------<NSThread: 0x6000028cf400>{number = 3, name = (null)}
//        sleep(2);
//        dispatch_sync(serQue, ^{  // 崩溃在这里
//
//            sleep(3);
//            NSLog(@"2222------%@",[NSThread currentThread]);  //
//        });
//
//        NSLog(@"3333----------%@",[NSThread currentThread]);
//    });
    
    // 不会崩溃，只有一个线程
   dispatch_queue_t que = dispatch_queue_create("com.ser", DISPATCH_QUEUE_CONCURRENT);
    
    dispatch_async(que, ^{
       
        NSLog(@"1-------%@",[NSThread currentThread]); // 1-------<NSThread: 0x600003451640>{number = 6, name = (null)}
        sleep(2);
        dispatch_async(que, ^{  // 当前线程
             
            for (int i = 0; i< 10; i++) {
                NSLog(@"2------%@",[NSThread currentThread]);  //  2------<NSThread: 0x600003451640>{number = 6, name = (null)}
            }
             
            dispatch_sync(que, ^{  // 当前线程
                
                sleep(3);
                NSLog(@"3------%@",[NSThread currentThread]);  //  2------<NSThread: 0x600003451640>{number = 6, name = (null)}
                
                dispatch_sync(que, ^{  // 当前线程
                    
                    sleep(3);
                    NSLog(@"4------%@",[NSThread currentThread]);  //  2------<NSThread: 0x600003451640>{number = 6, name = (null)}
                    
                    
                });
            });
             
            NSLog(@"5------%@",[NSThread currentThread]);  //  2------<NSThread: 0x600003451640>{number = 6, name = (null)}
        });
        
        for (int i = 0; i< 10; i++) {
            NSLog(@"6----------%@",[NSThread currentThread]);   //  3----------<NSThread: 0x600003451640>{number = 6, name = (null)}
        }
    });
    
    
    
//    dispatch_async(que, ^{
//
//        NSLog(@"1-------%@",[NSThread currentThread]);
//        sleep(2);
//        dispatch_sync(dispatch_get_main_queue(), ^{  // 主线程
//
//            sleep(2);
//            NSLog(@"2------%@",[NSThread currentThread]);
//        });
//        NSLog(@"3----------%@",[NSThread currentThread]);
//    });
//
    
    
    
    
//    return;
//    __block int a = 0;
//    dispatch_async(dispatch_get_main_queue(), ^{
//        a++;
//        NSLog(@"zhosuhua ------ %d",a);
//    });
    
    
    
//
//    NSLog(@"=====%ld",DISPATCH_QUEUE_ATTR_PRIO2IDX(0));
//
////    NSLog(@"=====%ld",DQA_INDEX_UNSPECIFIED_OVERCOMMIT);
////
////
////    NSLog(@"=====%d",DISPATCH_QUEUE_ATTR_AUTORELEASE_FREQUENCY2IDX(0));
////
////       NSLog(@"=====%ld",DISPATCH_QUEUE_ATTR_CONCURRENT2IDX(false));  // DQA_INDEX_SERIAL
////
//       NSLog(@"=====%ld",DISPATCH_QUEUE_ATTR_INACTIVE2IDX(false));  DQA_INDEX_ACTIVE
//
    
    
//    dispatch_queue_create("test", DISPATCH_QUEUE_CONCURRENT);
//    [self testGroupEnter];
//    [self testsync1];
 
//    [self testSemaphore];
//    [self testSync];
//    [self testBarrier];
    
//    [self testThird];
//      [self test];
    
    
//    [self testGroup];
//    [self testThirdAttr];
//    [self globalQueueTest];
//    [self createMultCons];
//    [self createMultSers];
    
    
    // 利用率-cpu
//    [self threadTest]; // 耗时-堵塞-主线程-用户体验
}

- (void)testGroupEnter {
    
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
}

- (void)testsync1 {

   NSLock *lock = [[NSLock alloc] init];
    dispatch_queue_t queue = dispatch_get_global_queue(0, 0);
    __block int a = 0;
    while (a < 10) {
        dispatch_async(queue, ^{
            a++;
        });
    }
    NSLog(@"%d",a); // 并不是a真正的值
    
    dispatch_async(queue, ^{
       
        NSLog(@"真正%d",a);
    });
    
//    dispatch_queue_t queue = dispatch_queue_create("zhouzhou", DISPATCH_QUEUE_CONCURRENT);
//    NSLog(@"1");
//    dispatch_async(queue, ^{
//        NSLog(@"2");
//
//        dispatch_sync(queue, ^{
//            NSLog(@"3");
//        });
//        NSLog(@"4");
//    });
//    NSLog(@"5");
}

- (void)testSemaphore {
    
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
}

- (void)testApply {
    
    dispatch_queue_t queue = dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0);
    dispatch_apply(10, queue, ^(size_t index) {
        NSLog(@"%zu",index);
    });
    NSLog(@"done");
}

- (void)testSync {
    
    dispatch_queue_t queue = dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0);
    dispatch_sync(queue, ^{

        NSLog(@"test --- sync start");
        sleep(5);
        NSLog(@"test --- sync end");
    });
    NSLog(@"哈哈哈哈哈哈哈");
    
//    dispatch_queue_t queue = dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0);
//    dispatch_async(queue, ^{
//
//        NSLog(@"test --- sync start");
//        sleep(5);
//        NSLog(@"test --- sync end");
//    });
//    NSLog(@"哈哈哈哈哈哈哈");
}

- (void)testBarrier {
    
    dispatch_queue_t queue = dispatch_queue_create("com.zhou.third", DISPATCH_QUEUE_CONCURRENT);
    dispatch_async(queue, ^{
        NSLog(@"blk0_reading");
    });
    dispatch_async(queue, ^{
        NSLog(@"blk1_reading");
    });
    dispatch_async(queue, ^{
        sleep(4);
        NSLog(@"blk2_reading");
    });
    dispatch_barrier_async(queue, ^{
        NSLog(@"blk3_写入处理");
    });
    dispatch_async(queue, ^{
        NSLog(@"blk4_reading");
    });
    dispatch_async(queue, ^{
        NSLog(@"blk5_reading");
    });
    dispatch_async(queue, ^{
        NSLog(@"blk6_reading");
    });
}

- (void)testThird {
    
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
}

- (void)test {
    
    while (1) {
        sleep(1);
        NSLog(@"哈哈哈");
    }
}

- (void)testGroup {
    
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
    
    
}

- (void)testThirdAttr {
    
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
    
    
}

- (void)globalQueueTest {
    
    dispatch_queue_t hightQueu = dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_HIGH, 0);
    
    dispatch_queue_t hightQueu1 = dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_HIGH, 0);
    
    dispatch_queue_t defaultQueu2 = dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0);
    
    
    dispatch_queue_t defaultQueu3 = dispatch_queue_create("hhh", DISPATCH_QUEUE_CONCURRENT);
    dispatch_queue_t defaultQueu4 = dispatch_queue_create("yyyy", DISPATCH_QUEUE_CONCURRENT);

    
    NSLog(@"hightQ=%@",hightQueu);
    NSLog(@"hightQueu1=%@",hightQueu1);
    NSLog(@"defaultQueu2=%@",defaultQueu2);
    NSLog(@"defaultQueu3=%@",defaultQueu3);
    NSLog(@"defaultQueu4=%@",defaultQueu4);
}

- (void)createMultSers {
    
    for (int i = 0; i < 10; i ++) {
        
        NSString *queueName = [NSString stringWithFormat:@"myQueue%d",10+i];
        const char *ptr = [queueName cStringUsingEncoding:NSUTF8StringEncoding];

        
        dispatch_queue_t queue = dispatch_queue_create(ptr, DISPATCH_QUEUE_SERIAL);
        
        dispatch_async(queue, ^{
        
            NSLog(@"执行任务%@ - %@",queueName,[NSThread currentThread]);
            sleep(10);
        });
    }
    
    
     
    
    dispatch_queue_t queue2 = dispatch_queue_create("myQueue2", DISPATCH_QUEUE_SERIAL);
    
    dispatch_async(queue2, ^{
    
        NSLog(@"执行任务2 - %@",[NSThread currentThread]);
        sleep(10);
    });
    
    dispatch_queue_t queue3 = dispatch_queue_create("myQueue3", DISPATCH_QUEUE_SERIAL);
    
    dispatch_async(queue3, ^{
    
        NSLog(@"执行任务3 - %@",[NSThread currentThread]);
        sleep(10);
    });
    
     
    
    dispatch_queue_t queue4 = dispatch_queue_create("myQueue4", DISPATCH_QUEUE_SERIAL);
    
    dispatch_async(queue4, ^{
    
        NSLog(@"执行任务4 - %@",[NSThread currentThread]);
        sleep(10);
    });
    
    
    dispatch_queue_t queue5 = dispatch_queue_create("myQueue5", DISPATCH_QUEUE_SERIAL);
    
    dispatch_async(queue5, ^{
    
        NSLog(@"执行任务5 - %@",[NSThread currentThread]);
        sleep(10);
    });
     
    
    dispatch_queue_t queue6 = dispatch_queue_create("myQueue6", DISPATCH_QUEUE_SERIAL);
    
    dispatch_async(queue6, ^{
    
        NSLog(@"执行任务6 - %@",[NSThread currentThread]);
        sleep(10);
    });
}


- (void)createMultCons {
    
    for (int i = 0; i < 10; i ++) {
           
           NSString *queueName = [NSString stringWithFormat:@"myQueue%d",10+i];
           const char *ptr = [queueName cStringUsingEncoding:NSUTF8StringEncoding];

           
           dispatch_queue_t queue = dispatch_queue_create(ptr, DISPATCH_QUEUE_CONCURRENT);
           
           dispatch_async(queue, ^{
           
               NSLog(@"执行任务%@ - %@",queueName,[NSThread currentThread]);
               sleep(10);
           });
       }
   
    
    dispatch_queue_t queue = dispatch_queue_create("myQueue", DISPATCH_QUEUE_CONCURRENT);
    
    dispatch_async(queue, ^{
    
        NSLog(@"执行任务1 - %@",[NSThread currentThread]);
        sleep(10);
    });
    
     
    
    dispatch_queue_t queue2 = dispatch_queue_create("myQueue2", DISPATCH_QUEUE_CONCURRENT);
    
    dispatch_async(queue2, ^{
    
        NSLog(@"执行任务2 - %@",[NSThread currentThread]);
        sleep(10);
    });
    
    dispatch_queue_t queue3 = dispatch_queue_create("myQueue3", DISPATCH_QUEUE_CONCURRENT);
    
    dispatch_async(queue3, ^{
    
        NSLog(@"执行任务3 - %@",[NSThread currentThread]);
        sleep(10);
    });
    
     
    
    dispatch_queue_t queue4 = dispatch_queue_create("myQueue4", DISPATCH_QUEUE_CONCURRENT);
    
    dispatch_async(queue4, ^{
    
        NSLog(@"执行任务4 - %@",[NSThread currentThread]);
        sleep(10);
    });
    
    
    dispatch_queue_t queue5 = dispatch_queue_create("myQueue5", DISPATCH_QUEUE_CONCURRENT);
    
    dispatch_async(queue5, ^{
    
        NSLog(@"执行任务5 - %@",[NSThread currentThread]);
        sleep(10);
    });
     
    
    dispatch_queue_t queue6 = dispatch_queue_create("myQueue6", DISPATCH_QUEUE_CONCURRENT);
    
    dispatch_async(queue6, ^{
    
        NSLog(@"执行任务6 - %@",[NSThread currentThread]);
        sleep(10);
    });
}

//- (void)touchesBegan:(NSSet<UITouch *> *)touches withEvent:(UIEvent *)event {
//
//    self.t = [[NSThread alloc] initWithTarget:self.p selector:@selector(testThreadStatus) object:@100];
//    // 2:启动线程
//    [self.t start];
//    self.t.name = @"学习线程";
//
//}

/*
 线程状态演练方法
 */
- (void)testThreadStatus {
    
    // running
    for (int i = 0; i < 10; i++) {
        // blocked
        if (i == 2) {
            sleep(1);
        }
        if ( i == 8) {
            
        }
    }
    [self.t cancel];
}

- (void)threadTest {
    
    
}

/**
 线程创建的方式
 */
- (void)createThreadMethod {
    
    NSLog(@"%@",[NSThread currentThread]);
    
    // A: 1:开辟线程
    NSThread *t = [[NSThread alloc] initWithTarget:self.p selector:@selector(study:) object:@100];
    // 2:启动线程
    [t start];
    t.name = @"学习线程";
    
    
    
    dispatch_sync(dispatch_get_global_queue(0, 0), ^{
        // __block 处理能带过来
        // block 的回调也没关系的，你
    });
    
    
}


@end
