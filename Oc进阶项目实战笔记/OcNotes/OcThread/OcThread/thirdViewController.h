//
//  thirdViewController.h
//  OcThread
//
//  Created by 周素华 on 1/7/2020.
//  Copyright © 2020 周素华. All rights reserved.
//

#import <UIKit/UIKit.h>

NS_ASSUME_NONNULL_BEGIN

@interface thirdViewController : UIViewController

/*
 直接使用线程可能会引发的一个问题是，如果你的代码和所基于的框架代码都创建自己的线程时，那么活动的线程数量有可能以指数级增长。这在大型工程中是一个常见问题。例如，在 8 核 CPU 中，你创建了 8 个线程来完全发挥 CPU 性能。然而在这些线程中你的代码所调用的框架代码也做了同样事情（因为它并不知道你已经创建的这些线程），这样会很快产生成成百上千的线程。代码的每个部分自身都没有问题，然而最后却还是导致了问题。使用线程并不是没有代价的，每个线程都会消耗一些内存和内核资源。

 接下来，我们将介绍两个基于队列的并发编程 API ：GCD 和 operation queue 。它们通过集中管理一个被大家协同使用的线程池，来解决上面遇到的问题。
 
 */


@end

NS_ASSUME_NONNULL_END
