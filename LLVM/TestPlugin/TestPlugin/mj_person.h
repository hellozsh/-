//
//  mj_person.h
//  TestPlugin
//
//  Created by MJ Lee on 2018/8/10.
//  Copyright © 2018年 MJ Lee. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface mj_person : NSObject
{
    int no;
}
@property (assign, nonatomic) int age;
@property (strong, nonatomic) NSString *name;
@end



