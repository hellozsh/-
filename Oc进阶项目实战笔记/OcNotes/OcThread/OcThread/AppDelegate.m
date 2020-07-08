//
//  AppDelegate.m
//  OcThread
//
//  Created by 周素华 on 2020/6/7.
//  Copyright © 2020 周素华. All rights reserved.
//

#import "AppDelegate.h"
#import "thirdViewController.h"

@interface AppDelegate ()

@end

@implementation AppDelegate


- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions {
     
//    NSArray *arr = @[ @11, @3, @4,@99,@6,@2,@9,@3,@2,@20];  // @1, @3, @4,
//
//
//    NSArray *result = [self guibin:arr];
//    NSLog(@"zhousuhua ====== result=%@",result);
//
    
    self.window = [[UIWindow alloc] initWithFrame:[UIScreen mainScreen].bounds];
    self.window.backgroundColor = [UIColor whiteColor];
    self.window.rootViewController = [[thirdViewController alloc] init];
    [self.window makeKeyAndVisible];
    
    // Override point for customization after application launch.
    return YES;
}
    
- (NSArray *)guibin:(NSArray *)arr {
    
    if (arr.count == 1) {
        return arr;
    }
    NSMutableArray *oriArr = [[NSMutableArray alloc] initWithArray:arr];

    int count = oriArr.count / 2;
    NSArray *arr1 = [oriArr subarrayWithRange:NSMakeRange(0, count)];
    NSArray *arr2 = [oriArr subarrayWithRange:NSMakeRange(count, oriArr.count-count)];
   
    NSArray *paihao1 = [NSArray arrayWithArray:[self guibin:arr1]];
    NSArray *paihao2 = [NSArray arrayWithArray:[self guibin:arr2]];
    NSLog(@"paihao1=%@, paihao1=%@",paihao1, paihao2);
     
    NSMutableArray *result = [NSMutableArray arrayWithCapacity:arr.count];
   
    int indexX=0;
    int indexY = 0;
    
    for (indexY= 0,indexX = 0; indexY < paihao2.count && indexX < paihao1.count;) {
        
         if ([paihao1[indexX] intValue] > [paihao2[indexY] intValue]) {
             
             [result addObject:paihao2[indexY]];
             indexY++;
         } else if([paihao1[indexX] intValue] == [arr2[indexY] intValue]) {
             
             [result addObject:paihao1[indexX]];
             [result addObject:paihao2[indexY]];
             indexX++;
             indexY++;
         } else {
             
             [result addObject:paihao1[indexX]];
             indexX++;
             
         }
    }
     
    for (int i = indexX; i < paihao1.count; i++) {
        [result addObject:paihao1[i]];
    }
    for (int i = indexY; i < paihao2.count; i++) {
         [result addObject:paihao2[i]];
    }
    NSLog(@"result === %@",result);
    return result;
}

#pragma mark - UISceneSession lifecycle


- (UISceneConfiguration *)application:(UIApplication *)application configurationForConnectingSceneSession:(UISceneSession *)connectingSceneSession options:(UISceneConnectionOptions *)options {
    // Called when a new scene session is being created.
    // Use this method to select a configuration to create the new scene with.
    return [[UISceneConfiguration alloc] initWithName:@"Default Configuration" sessionRole:connectingSceneSession.role];
}


- (void)application:(UIApplication *)application didDiscardSceneSessions:(NSSet<UISceneSession *> *)sceneSessions {
    // Called when the user discards a scene session.
    // If any sessions were discarded while the application was not running, this will be called shortly after application:didFinishLaunchingWithOptions.
    // Use this method to release any resources that were specific to the discarded scenes, as they will not return.
}


@end
