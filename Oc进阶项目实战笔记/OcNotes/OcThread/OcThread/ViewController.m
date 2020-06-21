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

@property (nonatomic, strong) Person *p;

@property (nonatomic, strong) NSThread *t;

@end

@implementation ViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    [self createMultCons];
//    [self createMultSers];
    
    
    // 利用率-cpu
//    [self threadTest]; // 耗时-堵塞-主线程-用户体验
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

- (void)touchesBegan:(NSSet<UITouch *> *)touches withEvent:(UIEvent *)event {
     
    self.t = [[NSThread alloc] initWithTarget:self.p selector:@selector(testThreadStatus) object:@100];
    // 2:启动线程
    [self.t start];
    self.t.name = @"学习线程";
    
}

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
