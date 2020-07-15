//
//  thirdViewController.m
//  OcThread
//
//  Created by 周素华 on 1/7/2020.
//  Copyright © 2020 周素华. All rights reserved.
//

#import "thirdViewController.h"
#import "PthreadTest.h"
#import "NSThreadTest.h"

@interface thirdViewController ()

@end

@implementation thirdViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    
//    test();
    
//    [self testGCDthreadAsyncSer];
    
//    []
    
    
    // Do any additional setup after loading the view.
}

- (void)testGCDthreadSyncCon {  // 执行，线程变成了主线程
         
    dispatch_queue_t queue = dispatch_queue_create("com.conr",DISPATCH_QUEUE_CONCURRENT);
    
    dispatch_sync(queue, ^{
        
        for (int i = 0; i < 50; i ++) {
            NSLog(@"blk0---------%d,name=%@",i,[NSThread currentThread]);
        }
    });
    dispatch_sync(queue, ^{
       
        for (int i = 0; i < 50; i ++) {
            NSLog(@"blk1---------%d,name=%@",i,[NSThread currentThread]);
        }
    });
    
}

- (void)testGCDthreadAsyncCon {  // 并发执行，2个线程，
         
    dispatch_queue_t queue = dispatch_queue_create("com.conr",DISPATCH_QUEUE_CONCURRENT);
    
    dispatch_async(queue, ^{
        
        for (int i = 0; i < 50; i ++) {
            NSLog(@"blk0---------%d,name=%@",i,[NSThread currentThread]);
        }
    });
    dispatch_async(queue, ^{
       
        for (int i = 0; i < 50; i ++) {
            NSLog(@"blk1---------%d,name=%@",i,[NSThread currentThread]);
        }
    });
    
}


- (void)testGCDthreadSyncSer {  // 串行执行，线程一个，
         
    dispatch_queue_t queue = dispatch_queue_create("com.conr",NULL);
    
    dispatch_sync(queue, ^{
        
        for (int i = 0; i < 50; i ++) {
            NSLog(@"blk0---------%d,name=%@",i,[NSThread currentThread]);
        }
    });
    dispatch_sync(queue, ^{
       
        for (int i = 0; i < 50; i ++) {
            NSLog(@"blk1---------%d,name=%@",i,[NSThread currentThread]);
        }
    });
    
}

- (void)testGCDthreadAsyncSer {  // 串行执行，线程一个，
    
    dispatch_queue_t queue = dispatch_queue_create("com.conr", NULL);
    
    dispatch_async(queue, ^{
        
        for (int i = 0; i < 50; i ++) {
            NSLog(@"blk0---------%d,name=%@",i,[NSThread currentThread]);
        }
    });
    dispatch_async(queue, ^{
       
        for (int i = 0; i < 50; i ++) {
            NSLog(@"blk1---------%d,name=%@",i,[NSThread currentThread]);
        }
    });
    
}

- (void)testNSThread {
    
    size_t const count = 1000000;
    NSMutableArray *inputValues = [NSMutableArray arrayWithCapacity:count];
    
    // 使用随机数字填充 inputValues
    for (size_t i = 0; i < count; i++) {
        [inputValues addObject:@(arc4random())];
    }
    
    NSMutableArray *threads = [NSMutableArray arrayWithCapacity:4];
    NSUInteger numberCount = inputValues.count;
    NSUInteger threadCount = 4;
    for (NSUInteger i = 0; i < threadCount; i++) {
        NSUInteger offset = (count / threadCount) * i;
        NSUInteger count = MIN(numberCount - offset, numberCount / threadCount);
        NSRange range = NSMakeRange(offset, count);
        NSArray *subset = [inputValues subarrayWithRange:range];
        NSThreadTest *thread = [[NSThreadTest alloc] initWithNumber:subset];
        [threads addObject:thread];
        [thread start];
    }
    
    
    dispatch_after(dispatch_time(DISPATCH_TIME_NOW, (int64_t)(4 * NSEC_PER_SEC)), dispatch_get_main_queue(), ^{
        
        // 寻找min和max
           NSUInteger min = UINT32_MAX;
           NSUInteger max = 0;
           for (size_t i = 0; i < threadCount; ++i) {
               NSThreadTest *thread = threads[i];
               min = MIN(min, thread.min);
               max = MAX(max, thread.max);
           }
           
           NSLog(@"min = %lu", (unsigned long)min);
           NSLog(@"max = %lu", (unsigned long)max);
    });
   
    
}

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

@end
