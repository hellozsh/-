//
//  AppDelegate.swift
//  NSURLSessionDemo
//
//  Created by 周素华 on 2020/7/23.
//  Copyright © 2020 周素华. All rights reserved.
//

import UIKit

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {

    var window: UIWindow?

    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        // Override point for customization after application launch.
        
        self.window = UIWindow.init(frame: UIScreen.main.bounds)
               self.window?.backgroundColor = UIColor.white
               
               self.window?.rootViewController = ViewController()
               self.window?.makeKeyAndVisible()
        return true
    }
    
    

}

