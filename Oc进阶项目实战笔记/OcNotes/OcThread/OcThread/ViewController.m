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

@end

@implementation ViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    // 利用率-cpu
    [self threadTest]; // 耗时-堵塞-主线程-用户体验
}

- (void)touchesBegan:(NSSet<UITouch *> *)touches withEvent:(UIEvent *)event {
    
    NSLog(@"123");
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
