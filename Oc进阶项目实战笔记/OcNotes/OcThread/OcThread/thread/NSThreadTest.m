//
//  NSThreadTest.m
//  OcThread
//
//  Created by 周素华 on 1/7/2020.
//  Copyright © 2020 周素华. All rights reserved.
//

#import "NSThreadTest.h"
 
@implementation NSThreadTest

{
    
    NSArray *_numbers;
}

- (instancetype)initWithNumber:(NSArray *)numbers {
    
    self = [super init];
    if (self) {
        _numbers = numbers;
    }
    return self;
}

- (void)main {
    
    NSUInteger min = NSUIntegerMax;
    NSUInteger max = 0;
    
    for (int i = 0; i < _numbers.count; i++) {
        NSUInteger v = [_numbers[i] unsignedIntegerValue];
        min = MIN(min, v);
        max = MAX(max, v);
    }
    
    self.min = min;
    self.max = max;
}

@end

