//
//  NSThreadTest.h
//  OcThread
//
//  Created by 周素华 on 1/7/2020.
//  Copyright © 2020 周素华. All rights reserved.
//

#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN

// NSThread 是 Objective-C 对 pthread 的一个封装。
@interface NSThreadTest : NSThread

@property (nonatomic) NSUInteger min;
@property (nonatomic) NSUInteger max;
- (instancetype)initWithNumber:(NSArray *)numbers;

@end

NS_ASSUME_NONNULL_END
